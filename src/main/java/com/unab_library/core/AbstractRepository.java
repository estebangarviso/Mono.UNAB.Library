package com.unab_library.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> {
    private ArrayList<T> items;

    protected AbstractRepository() {
        items = new ArrayList<>();
    }

    //#region Create
    /**
     * Save a new item in the repository
     * @param item
     */
    public void save(T item) {
        items.add(item);
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
     * Get an item by its index
     * @param index
     * @return
     */
    public T get(int index) {
        return items.get(index);
    }

    /**
     * Get the number of items in the repository
     * @return
     */
    public int count() {
        return items.size();
    }

    /**
     * Remove an item by its index
     * @param index
     */
    public void remove(int index) {
        items.remove(index);
    }

    /**
     * Remove all items in the repository
     */
    public void removeAll() {
        items.clear();
    }
    //#endregion
    //#region Update
    /**
     * Update an item by its index
     * @param index
     * @param item
     */
    public void update(int index, T item) {
        items.set(index, item);
    }
    //#endregion
}