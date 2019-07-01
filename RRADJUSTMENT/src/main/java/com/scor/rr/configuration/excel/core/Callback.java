package com.scor.rr.configuration.excel.core;

import java.util.Collection;

@FunctionalInterface
public interface Callback<T> {
    void apply(T t, Collection<CellError> errors);
}
