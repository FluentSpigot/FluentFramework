import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.Test;

public class ExampleTest extends FluentApiTest
{


    @Test
    public void Test()
    {
        var event = new PlayerJoinEvent(getPlayer(), "hello world");
        invokeEvent(event);



    }


    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {

    }
}
