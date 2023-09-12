import io.github.jwdeveloper.ff.extension.resourcepack.tools.ResourcepackTools;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.tools.FluentTaskAction;
import org.junit.jupiter.api.Test;

public class ShouldGenerateModelClass extends FluentTaskAction {
    @Test
    public void run() {
        var input= getTestResourcePath("exampleModel.json");
        var output = getTestResourcePath("generated");
        ResourcepackTools.generateResourcepackModelsAsClass(input, output);
    }

    @Override
    public void onFluentPluginBuild(FluentPluginBuilder builder) {

    }
}
