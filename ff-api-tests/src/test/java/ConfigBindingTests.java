import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import resources.config.AnotherOptions;
import resources.config.ConfigListContent;
import resources.config.TestConfig;
import resources.ExampleExtension;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class ConfigBindingTests extends FluentApiTest
{
    @Override
    public void onBuild(FluentApiSpigotBuilder builder)
    {
        var config = builder.config();
        config.getOrCreate("config.player-name", "Mark");
        config.getOrCreate("config.player-age", 10);
        config.getOrCreate("config.player-speed", 12.3f);
        config.getOrCreate("config.player-money", 16.3d);
        config.getOrCreate("config.player-op", true);

        //Nested
        config.getOrCreate("config.session.online-time", 100);
        config.getOrCreate("config.session.last-session", "20-02-2023");

        //Double nested
        config.getOrCreate("config.session.double-nested.desc", "Hello world");


        //List
        config.getOrCreate("config.info.value-1.name", "Adam");
        config.getOrCreate("config.info.value-1.last-name", "Stone");

        config.getOrCreate("config.info.value-2.name", "Mark");
        config.getOrCreate("config.info.value-2.last-name", "Wood");

        config.getOrCreate("custom.path.say-my-name", "Heisenberg");

        builder.bindToConfig(TestConfig.class);
        builder.bindToConfig(AnotherOptions.class,"custom.path");

        builder.useExtension(new ExampleExtension());


    }


    @Test
    public void ShouldHandleCustomBindingPath()
    {
        var options = getFluentApiMock().container().findInjection(AnotherOptions.class);
        Assertions.assertEquals(options.getSayMyName(),"Heisenberg");
    }

    @Test
    public void shouldMapConfigToObject()
    {
        var options = getFluentApiMock().container().findInjection(TestConfig.class);

        Assertions.assertEquals(options.getName(),"Mark");
        Assertions.assertEquals(options.getAge(),10);
        Assertions.assertEquals(options.getSpeed(),12.3f);
        Assertions.assertEquals(options.getMoney(),16.3d);
        Assertions.assertTrue(options.isPlayerOp());

        var nestedConfig = options.getSessionInfo();
        Assertions.assertNotNull(nestedConfig);
        Assertions.assertEquals(nestedConfig.getOnlineTime(),100);
        Assertions.assertEquals(nestedConfig.getLastSession(),"20-02-2023");

        var doubleNestedConfig = nestedConfig.getConfigDoubleNestedOptions();
        Assertions.assertNotNull(doubleNestedConfig);
        Assertions.assertEquals(doubleNestedConfig.getDescription(),"Hello world");

        var listConfig = options.getListContents();
        Assertions.assertNotNull(listConfig);
        Assertions.assertEquals(listConfig.size(),2);

        var firstValue = listConfig.get(0);
        Assertions.assertEquals(firstValue.getName(),"Adam");
        Assertions.assertEquals(firstValue.getLastName(),"Stone");

        var secondValue = listConfig.get(1);
        Assertions.assertEquals(secondValue.getName(),"Mark");
        Assertions.assertEquals(secondValue.getLastName(),"Wood");
    }

    @Test
    public void shouldSaveObjectToConfig()
    {
        var options = getFluentApiMock().container().findInjection(TestConfig.class);
        options.setName("John");
        options.setAge(200);
        options.setSpeed(15f);
        options.setMoney(20d);
        options.setPlayerOp(false);

        options.getSessionInfo().setOnlineTime(200);
        options.getSessionInfo().setLastSession("20-02-2024");

        options.getSessionInfo().getConfigDoubleNestedOptions().setDescription("Hello everyone");



        var memeber = new ConfigListContent();
        memeber.setName("MemberName");
        memeber.setLastName("MemberLastName");
        options.getListContents().add(memeber);

        var config =getFluentApiMock().config();
        config.save(options);

        Assertions.assertEquals(config.getRequired("config.player-name"),"John");
        Assertions.assertEquals(config.getRequired("config.player-age"),200);
        Assertions.assertEquals(config.getRequired("config.player-speed"),15f);
        Assertions.assertEquals(config.getRequired("config.player-money"),20d);
        Assertions.assertEquals(config.getRequired("config.player-op"),false);


        Assertions.assertEquals(config.getRequired("config.session.online-time"),200f);
        Assertions.assertEquals(config.getRequired("config.session.last-session"),"20-02-2024");

        Assertions.assertEquals(config.getRequired("config.session.double-nested.desc"),"Hello everyone");


        Assertions.assertEquals(config.get("config.info.value-3.name"), "MemberName");
        Assertions.assertEquals(config.get("config.info.value-3.last-name"), "MemberLastName");

    }

}
