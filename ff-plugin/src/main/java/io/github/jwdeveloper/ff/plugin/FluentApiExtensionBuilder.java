package io.github.jwdeveloper.ff.plugin;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class FluentApiExtensionBuilder
{
    private String version;

    private String name;
    private ExtentionPriority priority;

    private Consumer<FluentApiSpigotBuilder> onConfiguration;

    private Consumer<FluentApiSpigot> onEnable;

    private Consumer<FluentApiSpigot> onDisable;

    private final Plugin plugin;


    public FluentApiExtensionBuilder(Plugin plugin)
    {
        name = StringUtils.EMPTY;
        version = StringUtils.EMPTY;
        priority = ExtentionPriority.MEDIUM;
        this.plugin = plugin;
        onConfiguration = (e)->{};
        onEnable= (e)->{};
        onDisable= (e)->{};
    }


    public FluentApiExtensionBuilder onConfiguration(Consumer<FluentApiSpigotBuilder> event) {
        onConfiguration = event;
        return this;
    }

    public FluentApiExtensionBuilder onFluentApiEnable(Consumer<FluentApiSpigot> event) {
        onEnable = event;
        return this;
    }

    public FluentApiExtensionBuilder onFluentApiDisabled(Consumer<FluentApiSpigot> event) {
        onDisable = event;
        return this;
    }

    public FluentApiExtensionBuilder withPriority(ExtentionPriority priority) {
        this.priority = priority;
        return this;
    }

    public FluentApiExtensionBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public FluentApiExtensionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FluentApiExtension build()
    {
     return new FluentApiExtension() {
         @Override
         public void onConfiguration(FluentApiSpigotBuilder builder) {
             onConfiguration.accept(builder);
         }

         @Override
         public String getVersion() {
             return version.equals(StringUtils.EMPTY) ? plugin.getDescription().getVersion() : version;
         }

         @Override
         public String getName() {
             return name.equals(StringUtils.EMPTY) ? plugin.getDescription().getName() : name;
         }

         @Override
         public ExtentionPriority getPriority() {
             return priority;
         }

         @Override
         public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
             onEnable.accept(fluentAPI);
         }

         @Override
         public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
             onDisable.accept(fluentAPI);
         }
     };
    }
}
