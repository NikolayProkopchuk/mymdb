package com.prokopchuk.mymdb.common.domain.entity;

import java.util.Objects;

public abstract class BaseEntity<IDT> {

    private IDT id;

    public IDT getId() {
        return id;
    }

    public void setId(IDT id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
