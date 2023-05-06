package unit.factories;


import assets.ExampleUserTable;
import io.github.jwdeveloper.ff.extension.mysql.implementation.factories.TableModelFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableModelFactoryTest {

    private static TableModelFactory factory;

    @Before
    public void before() {
        factory = new TableModelFactory();
    }


    @Test
    public void shouldCreateModel()
    {
        var tableModel = factory.createTableModel(ExampleUserTable.class);

        Assert.assertEquals("Users",tableModel.getName());
        Assert.assertEquals(ExampleUserTable.class,tableModel.getClazz());
        Assert.assertEquals(3,tableModel.getColumnsCount());
        Assert.assertEquals(1,tableModel.getForeignKeysCount());
        Assert.assertEquals("id",tableModel.getPrimaryKeyColumn().getName());
    }


}