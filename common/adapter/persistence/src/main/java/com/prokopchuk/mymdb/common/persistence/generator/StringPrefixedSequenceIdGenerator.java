package com.prokopchuk.mymdb.common.persistence.generator;

import java.util.Optional;
import java.util.Properties;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;

import jakarta.persistence.Table;
import lombok.SneakyThrows;

public class StringPrefixedSequenceIdGenerator extends SequenceStyleGenerator {

    @Override
    @SneakyThrows
    protected QualifiedName determineSequenceName(Properties params, Dialect dialect, JdbcEnvironment jdbcEnv,
                                                  ServiceRegistry serviceRegistry) {
        String sequencePerEntitySuffix = ConfigurationHelper.getString(
          CONFIG_SEQUENCE_PER_ENTITY_SUFFIX, params, DEF_SEQUENCE_SUFFIX);

        String sequence = entityTable(params).map(Table::name)
          .map(name -> name.concat(sequencePerEntitySuffix))
          .orElse(params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix);

        if (sequence.contains(".")) {
            return QualifiedNameParser.INSTANCE.parse(sequence);
        } else {
            IdentifierHelper identifierHelper = jdbcEnv.getIdentifierHelper();
            Identifier catalog = identifierHelper.toIdentifier(ConfigurationHelper.getString(CATALOG, params));
            Identifier schema = entityTable(params).map(Table::schema)
              .map(identifierHelper::toIdentifier)
              .orElse(identifierHelper.toIdentifier(ConfigurationHelper.getString(SCHEMA, params)));
            return new QualifiedNameParser.NameParts(catalog, schema, identifierHelper.toIdentifier(sequence));
        }
    }


    private static Optional<Table> entityTable(Properties params) throws ClassNotFoundException {
        return Optional.of(Class.forName(params.getProperty("entity_name")))
          .map(clazz -> clazz.getAnnotation(Table.class));
    }
}
