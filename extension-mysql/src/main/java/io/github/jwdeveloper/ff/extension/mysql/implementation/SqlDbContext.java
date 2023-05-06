package io.github.jwdeveloper.ff.extension.mysql.implementation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlDbContext {
    final List<SqlTable<?>> tables = new ArrayList<>();

    void addTable(SqlTable<?> table) {
        tables.add(table);
    }


    public void saveChanges() throws Exception {
        for (var table : tables) {
            table.saveChanges();
        }
    }


}
