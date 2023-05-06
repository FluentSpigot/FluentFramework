package integration;


import assets.ExampleGroupTable;
import assets.ExampleUserTable;
import io.github.jwdeveloper.ff.extension.mysql.api.DbTable;
import io.github.jwdeveloper.ff.extension.mysql.implementation.SqlDbContext;
import lombok.Getter;

public class ExampleContext extends SqlDbContext
{
    @Getter
    private DbTable<ExampleGroupTable> groups;

    @Getter
    private DbTable<ExampleUserTable> users;
}