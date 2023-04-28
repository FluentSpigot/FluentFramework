package io.github.jwdeveloper.ff.extension.database.mysql.query_builder;

import io.github.jwdeveloper.ff.extension.database.api.query_builder.delete_builder.DeleteBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.insert_builder.InsertBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.select_builder.SelectBuilder;
import io.github.jwdeveloper.ff.extension.database.api.query_builder.update_builder.UpdateBuilder;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.delete_builder.DeleteBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.insert_builder.InsertBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.select_builder.SelectBuilderImpl;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.table_builder.TableBuilder;
import io.github.jwdeveloper.ff.extension.database.mysql.query_builder.update_builder.UpdateBuilderImpl;

public final class SqlQueryBuilder
{
    public static SelectBuilder select()
    {
        return new SelectBuilderImpl();
    }

    public static InsertBuilder insert()
    {
        return new InsertBuilderImpl();
    }

    public static DeleteBuilder delete()
    {
        return new DeleteBuilderImpl();
    }

    public static UpdateBuilder update()
    {
        return new UpdateBuilderImpl();
    }

    public static TableBuilder table() {return new TableBuilder(); }
}
