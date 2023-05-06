package unit;


import assets.ExampleUserTable;
import io.github.jwdeveloper.ff.extension.mysql.api.enums.EntryState;
import io.github.jwdeveloper.ff.extension.mysql.implementation.SqlEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SqlEntryTest {

    private static SqlEntry<ExampleUserTable> entry;

    @Before
    public void before() {
        entry = new SqlEntry<ExampleUserTable>(new ExampleUserTable(), null, "id", EntryState.NONE);
    }

    @Test
    public void hasFieldValueChanged() {
        entry.setField("id", 2);
        var resultInt = entry.hasFieldValueChanged("id", 2);
        var resultInt1 = entry.hasFieldValueChanged("id", 3);
        var resultInt2 = entry.hasFieldValueChanged("id", null);
        var resultInt3 = entry.hasFieldValueChanged("id", "mark");

        Assert.assertFalse(resultInt);
        Assert.assertTrue(resultInt1);
        Assert.assertTrue(resultInt2);
        Assert.assertTrue(resultInt3);
    }


}