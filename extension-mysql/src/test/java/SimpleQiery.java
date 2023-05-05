import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryContext;
import io.github.jwdeveloper.ff.extension.mysql.api.query.QueryModel;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryModelTranslator;
import org.junit.jupiter.api.Test;

public class SimpleQiery {
    @Test
    public void test() {


        var factory = new SqlQueryFactory();
        var query = factory.insert(newContext(), insertOptions ->
        {
            insertOptions.into("siema");
            insertOptions.columns("a", "b", "c");
            insertOptions.values(1, "2", "siema");
        }).toRawQuery();


        var query2 = factory.update(newContext(), insertOptions ->
        {
            insertOptions.table("siema");
            insertOptions.set("Mike",2);
            insertOptions.set("Adam","simea");
        }).toRawQuery();

        var i = 0;
    }


    private QueryContext newContext() {
        return new QueryContext(new QueryModel(), null, null, new SqlQueryModelTranslator());
    }
}
