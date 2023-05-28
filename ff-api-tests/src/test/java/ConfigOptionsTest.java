import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import resources.ExampleManager;
import resources.config.TestConfig;

public class ConfigOptionsTest extends FluentApiTest {
    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {
        fluentApiBuilder.bindToConfig(TestConfig.class);
        fluentApiBuilder.container().registerSigleton(ExampleManager.class);
    }

    @Test
    public void shouldReturnConfigOptions()
    {
        var manager = getFluentApiMock().container().findInjection(ExampleManager.class);
        Assertions.assertNotNull(manager.getOptions().get());
    }
}
