package desing_patterns.decorator;

import desing_patterns.decorator.example_classes.ExampleClass;
import desing_patterns.decorator.example_classes.ExampleDecorator;
import desing_patterns.decorator.example_classes.ExampleDecorator2;
import desing_patterns.decorator.example_classes.ExampleInterface;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator.FluentDecorator;
import org.junit.Assert;
import org.junit.Test;

public class DecoratorTests
{
    @Test
    public void Should_DecoratorWork() throws Exception {
        //Arrange
        var containerBuilder = new BuilderTemp();
        containerBuilder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);

        var decoratorBuilder = FluentDecorator.CreateDecorator();
        decoratorBuilder.decorate(ExampleInterface.class, ExampleDecorator.class);
        decoratorBuilder.decorate(ExampleInterface.class, ExampleDecorator2.class);

        var decorator = decoratorBuilder.build();
        containerBuilder.configure(c -> c.getEvents().add(decorator));

        var container = containerBuilder.build();
        var exampleInterface = (ExampleInterface)container.find(ExampleInterface.class);

        //Act
        var result = exampleInterface.doSomething();

        //Assert
        Assert.assertEquals(exampleInterface.getClass(), ExampleDecorator2.class);
        Assert.assertEquals("ExampleDecorator2 ExampleDecorator ExampleClass ",result);
    }

    public class BuilderTemp extends ContainerBuilderImpl<BuilderTemp>
    {

    }

}
