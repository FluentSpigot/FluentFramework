
https://jitpack.io/#jwdeveloper/FluentFramework
https://www.gamergeeks.net/apps/minecraft/give-command-generator
```code
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```


```code

 <dependencies>
 	<dependency>
	    <groupId>com.github.jwdeveloper.FluentFramework</groupId>
	    <artifactId>ff-plugin</artifactId>
	    <version>0.0.11-Release</version>
	</dependency>
 </dependeices>
```

```java
package io.github.jw.spigot.backpack;

import io.github.jw.spigot.backpack.commands.DevCommands;
import io.github.jw.spigot.backpack.commands.PluginCommands;
import io.github.jw.spigot.backpack.config.PluginConsts;
import io.github.jw.spigot.backpack.config.PluginSettings;
import io.github.jwdeveloper.ff.extension.gui.FluentInventoryApi;
import io.github.jwdeveloper.ff.extension.bai.items.FluentItemFramework;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMain extends JavaPlugin {

    @Override
    public void onEnable() {
        FluentPlugin.initialize(this)
                .withBstatsMetrics(PluginConsts.METRICS_ID)
                .withExtension(new BackpackPluginExtension())
                .withExtension(FluentItemFramework.use())
                .withExtension(FluentInventoryApi.use())
                .withTranslator()
                .withFiles(fluentFilesOptions ->
                {
                    fluentFilesOptions.addJsonFile(PluginSettings.class);
                })
                .withCommand(options ->
                {
                    options.setDefaultCommand(PluginCommands.class);
                    options.addCommand(DevCommands.class);
                }).create();
    }

}
```


