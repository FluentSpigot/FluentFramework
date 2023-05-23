import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.Test;

public class ExampleTest extends FluentApiTest
{
    @Override
    public FluentApiExtension useExtension() {
        return (e)->
        {
          e.logger().info("Siema");
        };
    }


    @Test
    public void Test()
    {
        var event = new PlayerJoinEvent(getPlayer(), "hello world");
        invokeEvent(event);



    }


}
