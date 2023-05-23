package integration;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import io.github.jwdeveloper.ff.extension.mysql.FluentSqlApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IntegrationTest {

    private ServerMock serverMock;


    @Before
    public void before() {
        serverMock = MockBukkit.mock();
    }

    @After
    public void after() {
        sendMessages(serverMock);
        MockBukkit.unmock();
    }

    @Test
    public void run() throws Exception {
        var plugin = MockBukkit.createMockPlugin();
        var extension = FluentSqlApi.useMySql(ExampleContext.class, model ->
        {
            model.setUser("root");
            model.setPassword("password123");
            model.setDatabase("example");
            model.setServer("localhost:3307");
        });
        var builder = FluentApiBuilder.create(plugin, extension);
        var api = builder.build();
        api.enable();
        api.disable();
    }


    public void sendMessages(ServerMock serverMock) {
        var mock = (ConsoleCommandSenderMock) serverMock.getConsoleSender();
        String msg = "";
        while (msg != null) {
            msg = mock.nextMessage();
            if (msg == null) {
                break;
            }
            System.out.println(msg);
        }
    }
}
