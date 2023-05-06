package unit;

import io.github.jwdeveloper.ff.extension.mysql.api.DbTypes;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectQueryType;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryModelTranslator;
import org.junit.Before;
import org.junit.Test;


public class QueryFactoryTests {

    private static SqlQueryFactory sqlQueryFactory;

    @Before
    public void before() {
        sqlQueryFactory = new SqlQueryFactory(null, null, new SqlQueryModelTranslator());
    }

    @Test
    public void selectTest() {
        var query = sqlQueryFactory.select(selectOptions ->
                {
                    selectOptions.from("Users");
                    selectOptions.columns("id", "name", "age");
                    selectOptions.queryType(SelectQueryType.DISTINCT);
                })
                .where(whereOptions ->
                {
                    whereOptions.isEqual("age", 2);
                    whereOptions.and();
                    whereOptions.isLike("name", "%mike");
                    whereOptions.and();
                    whereOptions.isGreaterThen("id", 23);
                })
                .groupByHaving("name", whereOptions ->
                {
                    whereOptions.isLike("name", "%adam");
                })
                .orderBy("age")
                .limit(2)
                .toRawQuery();
        System.out.println(query);
    }


    @Test
    public void insertTest() {
        var query = sqlQueryFactory.insert(selectOptions ->
                {
                    selectOptions.into("Users");
                    selectOptions.columns("id", "name", "age");
                    selectOptions.values(2, "Mark", 23);
                })
                .toRawQuery();
        System.out.println(query);
    }


    @Test
    public void deleteTest() {
        var query = sqlQueryFactory.delete(selectOptions ->
                {
                    selectOptions.from("Users");
                })
                .where(whereOptions ->
                {
                    whereOptions.isEqual("id", 2);
                })
                .toRawQuery();
        System.out.println(query);

    }

    @Test
    public void updateTest() {
        var query = sqlQueryFactory.update(selectOptions ->
                {
                    selectOptions.table("Users");
                    selectOptions.set("id", "mark");
                    selectOptions.set("age", 32);
                })
                .where(whereOptions ->
                {
                    whereOptions.isEqual("id", 2);
                })
                .toRawQuery();
        System.out.println(query);
    }

    @Test
    public void createTableTest() {
        var query = sqlQueryFactory.createTable(selectOptions ->
                {
                    selectOptions.withTableName("Users");
                    selectOptions.withColumn(columnOptions ->
                    {
                        columnOptions.withPrimaryKey();
                        columnOptions.withAutoIncrement();
                        columnOptions.withColumnName("id");
                        columnOptions.withType(DbTypes.INT);
                    });
                    selectOptions.withColumn(columnOptions ->
                    {
                        columnOptions.withColumnName("name");
                        columnOptions.withType(DbTypes.VARCHAR,30);
                    });
                    selectOptions.withColumn(columnOptions ->
                    {
                        columnOptions.withColumnName("age");
                        columnOptions.withType(DbTypes.INT);
                    });
                    selectOptions.withColumn(columnOptions ->
                    {
                        columnOptions.withColumnName("groupId");
                        columnOptions.withType(DbTypes.INT);
                        columnOptions.withForeignKey("Groups","groupId");
                    });
                })
                .toRawQuery();
        System.out.println(query);
    }

}
