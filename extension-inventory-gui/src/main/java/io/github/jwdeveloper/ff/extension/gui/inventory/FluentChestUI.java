package io.github.jwdeveloper.ff.extension.gui.inventory;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.renderer.ButtonStyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.renderer.CatchButtonStyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.FluentButtonStyle;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;

@Injection
public class FluentChestUI
{
    private final FluentTranslator translator;
    private final FluentButtonStyle style;
    private final CatchButtonStyleRenderer renderer;

    @Inject
    public FluentChestUI(FluentTranslator translator)
    {
        this.translator = translator;
        style = new FluentButtonStyle(translator);
        renderer = new CatchButtonStyleRenderer(translator, style.getColorSet(), new SimpleMessage());
    }

    public FluentTranslator lang()
    {
        return translator;
    }


    public ButtonStyleRenderer renderer()
    {
        return renderer;
    }

    public FluentButtonUIBuilder buttonBuilder()
    {
        return null;
      //  return new FluentButtonUIBuilder(translator,renderer);
    }

    public FluentButtonUIFactory buttonFactory()
    {
        return new FluentButtonUIFactory(translator, style, buttonBuilder());
    }
}
