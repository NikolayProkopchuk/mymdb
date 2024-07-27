package com.prokopchuk.mymdb.common.persistence.entity;

import java.io.Serial;
import java.io.Serializable;

import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CustomAbstractPersistable<PKT extends Serializable> implements Persistable<PKT>, Serializable {

    @Serial
    private static final long serialVersionUID = -5554308939380869754L;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s",
          this.getClass()
            .getName(),
          getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }
        CustomAbstractPersistable<?> that = (CustomAbstractPersistable<?>) obj;
        return null != this.getId() && this.getId()
          .equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
