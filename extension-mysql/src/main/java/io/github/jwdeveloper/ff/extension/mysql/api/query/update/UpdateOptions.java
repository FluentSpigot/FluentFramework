package io.github.jwdeveloper.ff.extension.mysql.api.query.update;

import java.util.Map;

public interface UpdateOptions {
    UpdateOptions table(String table);

    UpdateOptions set(Map<String, Object> values);

    UpdateOptions set(String column, Object value);
}
