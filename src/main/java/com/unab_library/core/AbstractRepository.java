package com.unab_library.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractRepository<T> {
    protected ArrayList<T> items;

    protected AbstractRepository() {
        items = new ArrayList<>();
    }

    //#region Create
    /**
     * Save a new item in the repository
     * @param item
     */
    protected void save(T item) {
        items.add(item);
    }
    //#endregion
    //#region Read
    /**
     * Get all items in the repository
     * @return
     */
    protected List<T> getAll() {
        return items;
    }

    /**
     * Get a range of items in the repository
     * @param start
     * @param end
     * @return
     */
    protected List<T> getRange(int start, int end) {
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
        List<T> items = getAll();
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (propertyGetter.apply(item).equals(value)) {
                return Result.<T>builder()
                    .setIndex(i)
                    .setValue(item)
                    .setSuccess(true)
                    .build();
            }
        }
        return Result.<T>builder()
                .setIndex(-1)
                .setValue(null)
                .setSuccess(false)
                .build();
    }
    //#endregion
    //#region Delete
    /**
     * Remove an item by its index
     * @param index
     */
    protected void delete(int index) {
        items.remove(index);
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
        items.set(index, item);
    }
    //#endregion

    //#region Result class to handle array index and boolean result
    public static class Result<T> {
        private int index;
        private boolean success;
        private T value;

        private Result() {
            index = -1;
            success = false;
            value = null;
        }

        public static <T> ResultBuilder<T> builder() {
            return new ResultBuilder<>(new Result<>());
        }

        public int getIndex() {
            return index;
        }

        public boolean isSuccess() {
            return success;
        }

        public T getValue() {
            return value;
        }
    }
    protected static class ResultBuilder<T> {
        private Result<T> instance;

        private ResultBuilder(Result<T> instance) {
            this.instance = instance;
        }

        public ResultBuilder<T> setIndex(int index) {
            instance.index = index;
            return this;
        }

        public ResultBuilder<T> setSuccess(boolean success) {
            instance.success = success;
            return this;
        }

        public ResultBuilder<T> setValue(T found) {
            instance.value = found;
            return this;
        }

        public Result<T> build() {
            return instance;
        }
    }
    //#endregion
}