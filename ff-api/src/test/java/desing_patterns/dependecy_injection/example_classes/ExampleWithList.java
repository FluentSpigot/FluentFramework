package desing_patterns.dependecy_injection.example_classes;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import lombok.Getter;

import java.util.List;

@Injection
public class ExampleWithList
{
    @Getter
    private final List<ExampleInterface> exampleInterfaces;

    @Inject
    public ExampleWithList(List<ExampleInterface> exampleInterfaces) {
        this.exampleInterfaces = exampleInterfaces;
    }
}
