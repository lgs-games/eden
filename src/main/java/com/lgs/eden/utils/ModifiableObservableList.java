package com.lgs.eden.utils;

import javafx.collections.ModifiableObservableListBase;

import java.util.List;
import java.util.Vector;

public class ModifiableObservableList<E> extends ModifiableObservableListBase<E> {
    /**
     * Internal List
     */
    private List<E> delegate = null;

    /**
     * Default constructor.
     * Builds an empty list
     */
    public ModifiableObservableList()
    {
        delegate = new Vector<E>();
    }

    @Override
    public E get(int index)
    {
        return delegate.get(index);
    }

    @Override
    public int size()
    {
        return delegate.size();
    }

    @Override
    protected void doAdd(int index, E element)
    {
        delegate.add(index, element);
    }

    @Override
    protected E doSet(int index, E element)
    {
        return delegate.set(index, element);
    }

    @Override
    protected E doRemove(int index)
    {
        return delegate.remove(index);
    }
}
