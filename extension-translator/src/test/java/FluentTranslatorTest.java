import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.extension.translator.FluentTranslatorAPI;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FluentTranslatorTest extends FluentApiTest {

    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {
        fluentApiBuilder.useExtension(FluentTranslatorAPI.use());
    }

    @Test
    public void shouldCreateLanaguageFiles() {

    }

    @Test
    public void ShouldLoadLanaguages() {
        var translator = getFluentApiMock().container().findInjection(FluentTranslator.class);
        Assertions.assertEquals(translator.getLanguagesName().size(), 6);
    }
}
