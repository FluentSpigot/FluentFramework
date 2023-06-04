import io.github.jwdeveloper.ff.extension.resourcepack.tools.ResourcepackTools;
import io.github.jwdeveloper.ff.tools.TaskBase;
import org.junit.jupiter.api.Test;

public class ShouldGenerateModelClass extends TaskBase {
    @Test
    public void run() {
        var input= getTestResourcePath("exampleModel.json");
        var output = getTestResourcePath("generated");
        ResourcepackTools.generateResourcepackModelsAsClass(input, output);
    }
}
