package com.prokopchuk.mymdb.common.persistence.entity;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.lang.Nullable;

import com.prokopchuk.mymdb.common.persistence.generator.StringPrefixedSequenceIdGenerator;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CustomAbstractEntity<PKT extends Serializable> extends CustomAbstractPersistable<PKT> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "pooled-lo")
    @GenericGenerator(
      name = "pooled-lo",
      type = StringPrefixedSequenceIdGenerator.class,
      parameters = {
        @Parameter(name = "optimizer", value = "pooled-lo"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "10"),
        @Parameter(name = SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX, value = "_id_seq")})
    private PKT id;

    @Nullable
    public PKT getId() {
        return id;
    }

    public void setId(@Nullable PKT id) {
        this.id = id;
    }
}
