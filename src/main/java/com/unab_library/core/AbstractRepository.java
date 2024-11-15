package com.unab_library.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;


public abstract class AbstractRepository<T> {
    protected ArrayList<T> items;
    private static final String DATA_DIR = "data";
    private static final String TRANSACTION_SUFFIX = "_transactions.log";
    private final Gson gson;
    private final String fileName;
    private final Type typeToken;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    protected AbstractRepository(String fileName, TypeToken<ArrayList<T>> token) {
        this.fileName = DATA_DIR + "/" + fileName;
        this.typeToken = token.getType();
        this.gson = new Gson().newBuilder()
            .registerTypeAdapter(Transaction.class, new TransactionAdapter<>(token))
            .create();
        new File(DATA_DIR).mkdirs();
        this.items = loadItemsWithTransactions();
    }

    private ArrayList<T> loadItemsWithTransactions() {
        lock.readLock().lock();
        try {
            // Load base file
            ArrayList<T> loadedItems = loadBaseItems();
            
            // Apply transactions
            Path transactionFile = Paths.get(fileName + TRANSACTION_SUFFIX);
            if (Files.exists(transactionFile)) {
                try (BufferedReader reader = Files.newBufferedReader(transactionFile)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Transaction<T> transaction = gson.fromJson(line, new TypeToken<Transaction<T>>(){}.getType());
                        applyTransaction(loadedItems, transaction);
                    }
                }
            }
            return loadedItems;
        } catch (IOException e) {
            throw new RuntimeException("Error loading data", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    private ArrayList<T> loadBaseItems() {
        File file = new File(fileName);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, typeToken);
        } catch (IOException e) {
            throw new RuntimeException("Error loading data from " + fileName, e);
        }
    }

    private void applyTransaction(ArrayList<T> items, Transaction<T> transaction) {
        switch (transaction.type) {
            case ADD:
                items.add(transaction.item);
                break;
            case DELETE:
                items.remove(transaction.index);
                break;
            case UPDATE:
                items.set(transaction.index, transaction.item);
                break;
        }
    }

    private void appendTransaction(Transaction<T> transaction) {
        lock.writeLock().lock();
        try {
            Files.writeString(
                Paths.get(fileName + TRANSACTION_SUFFIX),
                gson.toJson(transaction) + System.lineSeparator(),
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND
            );
            
        } catch (IOException e) {
            throw new RuntimeException("Error writing transaction", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    //#region Create
    /**
     * Save a new item in the repository
     * @param item
     */
    protected Result<T> save(T item) {
        Transaction<T> transaction = new Transaction<>(TransactionType.ADD, items.size(), item);
        items.add(item);
        appendTransaction(transaction);
        return Result.<T>builder().setSuccess(true).setValue(item).build();
    }
    //#endregion
    //#region Read
    /**
     * Get all items in the repository
     * @return
     */
    public List<T> getAll() {
        return items;
    }

    /**
     * Get a range of items in the repository
     * @param start
     * @param end
     * @return
     */
    public List<T> getRange(int start, int end) {
        return new ArrayList<>(items.subList(start, end));
    }

    /**
     * Get a page of items in the repository
     * @param page the page number
     * @param size the number of items per page
     * @return the items in the specified page
     */
    public List<T> getPage(int page, int size) {
        // calculate the start index
        int start = (page - 1) * size;
        // calculate the end index
        int end = Math.min(start + size, this.count());
        // return the books in the specified page
        return this.getRange(start, end);
    }

    /**
     * Get an item by its index
     * @param index
     * @return
     */
    protected T get(int index) {
        return items.get(index);
    }

    /**
     * Get the number of items in the repository
     * @return
     */
    protected int count() {
        return items.size();
    }

    protected <R> Result<T> getByProperty(Function<T, R> propertyGetter, R value) {
        try {
            lock.readLock().lock();
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                if (propertyGetter.apply(item).equals(value)) {
                    return new Result.Builder<T>()
                        .setIndex(i)
                        .setSuccess(true)
                        .setValue(item)
                        .build();
                }
            }
            return new Result.Builder<T>()
                .setSuccess(false)
                .setError(new Exception("Item not found"))
                .build();
        } catch (Exception e) {
            return new Result.Builder<T>()
                .setSuccess(false)
                .setError(e)
                .build();
        } finally {
            lock.readLock().unlock();
        }
    }
    //#endregion
    //#region Delete
    /**
     * Remove an item by its index
     * @param index
     */
    protected void delete(int index) {
        Transaction<T> transaction = new Transaction<>(TransactionType.DELETE, index, null);
        items.remove(index);
        appendTransaction(transaction);
    }

    /**
     * Remove all items in the repository
     */
    protected void clear() {
        items.clear();
    }
    //#endregion
    //#region Update
    /**
     * Update an item by its index
     * @param index
     * @param item
     */
    protected void update(int index, T item) {
        Transaction<T> transaction = new Transaction<>(TransactionType.UPDATE, index, item);
        items.set(index, item);
        appendTransaction(transaction);
    }
    //#endregion

    private enum TransactionType {
        ADD, DELETE, UPDATE
    }

    private static class Transaction<T> {
        TransactionType type;
        int index;
        JsonElement itemJson;  // Changed from T to JsonElement
        transient T item;      // Transient field for deserialized item

        Transaction(TransactionType type, int index, T item) {
            this.type = type;
            this.index = index;
            this.item = item;
            this.itemJson = item != null ? new Gson().toJsonTree(item) : null;
        }
    }

    private class TransactionAdapter<T> extends TypeAdapter<Transaction<T>> {
        private final TypeToken<ArrayList<T>> typeToken;

        TransactionAdapter(TypeToken<ArrayList<T>> typeToken) {
            this.typeToken = typeToken;
        }

        @Override
        public void write(JsonWriter out, Transaction<T> transaction) throws IOException {
            out.beginObject();
            out.name("type").value(transaction.type.name());
            out.name("index").value(transaction.index);
            out.name("itemJson");
            if (transaction.itemJson != null) {
                gson.toJson(transaction.itemJson, out);
            } else {
                out.nullValue();
            }
            out.endObject();
        }

        @Override
        public Transaction<T> read(JsonReader in) throws IOException {
            in.beginObject();
            TransactionType type = null;
            int index = -1;
            JsonElement itemJson = null;

            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "type":
                        type = TransactionType.valueOf(in.nextString());
                        break;
                    case "index":
                        index = in.nextInt();
                        break;
                    case "itemJson":
                        itemJson = JsonParser.parseReader(in);
                        break;
                    default:
                        in.skipValue();
                }
            }
            in.endObject();

            Transaction<T> transaction = new Transaction<>(type, index, null);
            transaction.itemJson = itemJson;
            if (itemJson != null) {
                Type itemType = ((ParameterizedType) typeToken.getType()).getActualTypeArguments()[0];
                transaction.item = gson.fromJson(itemJson, itemType);
            }
            return transaction;
        }
    }

    //#region Result
    public static class Result<T> {
        private boolean success;
        private T value;
        private int index;
        private Exception error;

        private Result() {}

        public boolean isSuccess() {
            return success;
        }

        public T getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }

        public Exception getError() {
            return error;
        }

        public static <T> Builder<T> builder() {
            return new Builder<>();
        }

        public static class Builder<T> {
            private Result<T> result = new Result<>();

            public Builder<T> setIndex(int index) {
                result.index = index;
                return this;
            }

            public Builder<T> setSuccess(boolean success) {
                result.success = success;
                return this;
            }

            public Builder<T> setValue(T value) {
                result.value = value;
                return this;
            }

            public Builder<T> setError(Exception error) {
                result.error = error;
                return this;
            }

            public Result<T> build() {
                return result;
            }
        }
    }
    //#endregion
}