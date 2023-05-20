package integration;


import assets.ExampleGroupTable;
import assets.ExampleUserTable;
import io.github.jwdeveloper.ff.extension.mysql.api.DbTable;
import io.github.jwdeveloper.ff.extension.mysql.implementation.DbContext;
import lombok.Getter;

public class ExampleContext extends DbContext
{
    @Getter
    private DbTable<ExampleGroupTable> groups;

    @Getter
    private DbTable<ExampleUserTable> users;
}