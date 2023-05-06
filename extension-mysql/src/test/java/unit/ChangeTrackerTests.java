package unit;

import assets.ExampleUserTable;
import io.github.jwdeveloper.ff.extension.mysql.api.enums.EntryState;
import io.github.jwdeveloper.ff.extension.mysql.implementation.factories.TableModelFactory;
import io.github.jwdeveloper.ff.extension.mysql.implementation.tracker.SqlChangeTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChangeTrackerTests
{
    private static SqlChangeTracker<ExampleUserTable> sqlQueryFactory;

    @Before
    public void before() {

        var factory = new TableModelFactory();
        var model  = factory.createTableModel(ExampleUserTable.class);

        sqlQueryFactory = new SqlChangeTracker<ExampleUserTable>(model);
    }

    @Test
    public void shouldInsert()
    {
        var exampleUser = new ExampleUserTable();

        sqlQueryFactory.insert(exampleUser);

        var entries = sqlQueryFactory.getTrackedEntries();
        var first = entries.stream().findFirst();
        var entry = first.get();


        Assert.assertEquals(exampleUser, entry.getEntity());
        Assert.assertEquals(EntryState.INSERT, entry.getEntryState());
        Assert.assertEquals("id", entry.getKeyColumnName());
    }

    @Test
    public void shouldDelete()
    {
        var exampleUser = new ExampleUserTable();

        sqlQueryFactory.delete(exampleUser);

        var entries = sqlQueryFactory.getTrackedEntries();
        var first = entries.stream().findFirst();
        var entry = first.get();


        Assert.assertEquals(exampleUser, entry.getEntity());
        Assert.assertEquals(EntryState.DELETE, entry.getEntryState());
        Assert.assertEquals("id", entry.getKeyColumnName());
    }

    @Test
    public void shouldUpdate()
    {
        var exampleUser = new ExampleUserTable();

        sqlQueryFactory.update(exampleUser);

        var entries = sqlQueryFactory.getTrackedEntries();
        var first = entries.stream().findFirst();
        var entry = first.get();


        Assert.assertEquals(exampleUser, entry.getEntity());
        Assert.assertEquals(EntryState.UPDATE, entry.getEntryState());
        Assert.assertEquals("id", entry.getKeyColumnName());
    }

    @Test
    public void shouldClear()
    {
        var exampleUser1 = new ExampleUserTable();
        var exampleUser2 = new ExampleUserTable();
        var exampleUser3 = new ExampleUserTable();


        sqlQueryFactory.insert(exampleUser1);
        sqlQueryFactory.update(exampleUser2);
        sqlQueryFactory.delete(exampleUser3);

        sqlQueryFactory.clear();
        var entries = sqlQueryFactory.getTrackedEntries();
        Assert.assertEquals(2,entries.size());
        for(var entry : entries)
        {
            Assert.assertEquals(EntryState.NONE,entry.getEntryState());
        }
    }




}
