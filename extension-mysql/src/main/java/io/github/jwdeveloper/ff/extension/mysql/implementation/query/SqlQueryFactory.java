package io.github.jwdeveloper.ff.extension.mysql.implementation.query;

import io.github.jwdeveloper.ff.extension.mysql.api.models.TableModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.delete.SqlDeleteBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.insert.SqlInsertBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.select.SqlSelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.update.SqlUpdateBridge;

import java.util.function.Consumer;

public class SqlQueryFactory {

    public <T> SelectBridge<T> select(QueryContext queryContext, Consumer<SelectOptions> options) {
        return new SqlSelectBridge<T>(queryContext).select(options);
    }

    public <T> InsertBridge<T> insert(QueryContext queryContext, Consumer<InsertOptions> options) {
        return new SqlInsertBridge<T>(queryContext).insert(options);
    }

    public <T> UpdateBridge<T> update(QueryContext queryContext, Consumer<UpdateOptions> options) {
        return new SqlUpdateBridge<T>(queryContext).update(options);
    }

    public <T> DeleteBridge<T> delete(QueryContext queryContext, Consumer<SelectOptions> options) {
        return new SqlDeleteBridge<T>(queryContext).delete(options);
    }


    public String createTable(TableModel tableModel) {
        return null;
    }

    public String dropTable(TableModel tableModel) {
        return null;
    }
}
