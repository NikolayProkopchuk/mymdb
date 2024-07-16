package com.prokopchuk.mymdb.common.domain.value;

import java.util.Objects;

public class BaseId<T> {
    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseId<?> baseId = (BaseId<?>) obj;
        return Objects.equals(value, baseId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
