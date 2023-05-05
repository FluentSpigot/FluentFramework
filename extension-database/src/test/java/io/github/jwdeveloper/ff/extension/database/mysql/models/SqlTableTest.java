package io.github.jwdeveloper.ff.extension.database.mysql.models;


import org.junit.jupiter.api.Test;

class SqlTableTest {

    @Test
    void select() {



        var table = new SqlTable<>(ExampleClass.class);

        var whiere = table.select();
        var www = whiere.where().isEqual("asd", 2);
        var group = www.groupBy();
        var col = group.column("siema");

        var aa = col.having().isEqual("adasa", 2);
        var bb = aa.orderBy().asc("asdaad");
        var query = bb.getQuery();
        var i = 0;
    }
}