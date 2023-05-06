package io.github.jwdeveloper.ff.extension.mysql.implementation.query;

import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.models.TableModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.delete.DeleteBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.TableBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.table.create.CreateTableOptions;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateBridge;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateOptions;
import io.github.jwdeveloper.ff.extension.mysql.implementation.executor.SqlQueryExecutor;
import io.github.jwdeveloper.ff.extension.mysql.implementation.mapper.SqlQueryMapper;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.delete.SqlDeleteBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.insert.SqlInsertBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.select.SqlSelectBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.table.SqlTableBridge;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.update.SqlUpdateBridge;

import java.util.function.Consumer;

public class SqlQueryFactory {
    private final SqlQueryExecutor queryExecutor;
    private final SqlQueryMapper queryMapper;
    private final SqlQueryModelTranslator queryModelTranslator;

    public SqlQueryFactory(SqlQueryExecutor queryExecutor,
                           SqlQueryMapper queryMapper,
                           SqlQueryModelTranslator queryModelTranslator) {
        this.queryExecutor = queryExecutor;
        this.queryMapper = queryMapper;
        this.queryModelTranslator = queryModelTranslator;
    }

    public <T> SelectBridge<T> select(Consumer<SelectOptions> options) {
        return new SqlSelectBridge<T>(createQueryContext()).select(options);
    }

    public <T> InsertBridge<T> insert(Consumer<InsertOptions> options) {
        return new SqlInsertBridge<T>(createQueryContext()).insert(options);
    }

    public <T> UpdateBridge<T> update(Consumer<UpdateOptions> options) {
        return new SqlUpdateBridge<T>(createQueryContext()).update(options);
    }

    public <T> DeleteBridge<T> delete(Consumer<DeleteOptions> options) {
        return new SqlDeleteBridge<T>(createQueryContext()).delete(options);
    }

    public <T> TableBridge<T> createTable(Consumer<CreateTableOptions> options)
    {
        return new SqlTableBridge<T>(createQueryContext()).createTable(options);
    }


    private QueryContext createQueryContext() {
        return new QueryContext(new QueryModel(), queryExecutor, queryMapper, queryModelTranslator);
    }


    public String dropTable(TableModel tableModel) {
        return null;
    }
}
