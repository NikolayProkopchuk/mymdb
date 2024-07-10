package com.prokopchuk.mymdb.media.domain;

import com.prokopchuk.mymdb.common.domain.entity.BaseEntity;
import com.prokopchuk.mymdb.common.domain.value.FilmId;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Film extends BaseEntity<FilmId> {
    private String name;
    private String description;
    private LocalDate productionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Film(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setDescription(builder.description);
        setProductionDate(builder.productionDate);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Film.Builder builder() {
        return new Film.Builder();
    }


    public static final class Builder {
        private FilmId id;
        private String name;
        private String description;
        private LocalDate productionDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public Builder id(FilmId val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder productionDate(LocalDate val) {
            productionDate = val;
            return this;
        }

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public Film build() {
            return new Film(this);
        }
    }
}
