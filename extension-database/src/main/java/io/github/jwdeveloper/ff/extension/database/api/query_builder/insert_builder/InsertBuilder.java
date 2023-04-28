package io.github.jwdeveloper.ff.extension.database.api.query_builder.insert_builder;

public interface InsertBuilder {

    InsertBuilderColumn table(Class<?> tableClass);

    InsertBuilderColumn table(String table);
}
