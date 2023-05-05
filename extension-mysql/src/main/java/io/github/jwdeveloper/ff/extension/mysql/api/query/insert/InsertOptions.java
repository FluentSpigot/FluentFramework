package io.github.jwdeveloper.ff.extension.mysql.api.query.insert;

public interface InsertOptions {
    InsertOptions into(String table);

    InsertOptions columns(String... columns);

    InsertOptions values(Object... values);
}
