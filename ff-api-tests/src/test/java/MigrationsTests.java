import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.config.ConfigProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import resources.ExampleExtension;

public class MigrationsTests extends FluentApiTest {


    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {

        fluentApiBuilder.jarScanner().attacheAllClassesFromPackage(MigrationsTests.class);
        fluentApiBuilder.config().getOrCreate(new ConfigProperty<String>("versions.ExampleExtension", "1.0.0"));

        fluentApiBuilder.useExtension(new ExampleExtension());

    }

    @Test
    public void ShouldInvokeMigrations() {
        var nullValue = getFluentApiMock().config().get("example");
        var result = getFluentApiMock().config().get("new.example");
        Assertions.assertNull(nullValue);
        Assertions.assertNotNull(result);
    }


}
