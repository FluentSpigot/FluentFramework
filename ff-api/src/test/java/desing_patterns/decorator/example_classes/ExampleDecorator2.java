package desing_patterns.decorator.example_classes;


import io.github.jwdeveloper.ff.core.injector.decorator.api.annoatations.Decorator;

@Decorator
public class ExampleDecorator2 implements ExampleInterface {

    private final ExampleInterface exampleInterface;

    public ExampleDecorator2(ExampleInterface exampleInterface) {
        this.exampleInterface = exampleInterface;
    }


    @Override
    public String doSomething() {
        return getClass().getSimpleName()+" "+exampleInterface.doSomething();
    }
}
