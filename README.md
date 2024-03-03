
https://jitpack.io/#jwdeveloper/FluentFramework

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
	    <artifactId>extension-web-socket</artifactId>
	    <version>0.0.5-Release</version>
	</dependency>

	<dependency>
	    <groupId>com.github.jwdeveloper.FluentFramework</groupId>
	    <artifactId>extension-translator</artifactId>
	    <version>0.0.5-Release</version>
	</dependency>

	<dependency>
	    <groupId>com.github.jwdeveloper.FluentFramework</groupId>
	    <artifactId>ff-api</artifactId>
	    <version>0.0.5-Release</version>
	</dependency>

 	<dependency>
	    <groupId>com.github.jwdeveloper.FluentFramework</groupId>
	    <artifactId>ff-plugin</artifactId>
	    <version>0.0.5-Release</version>
	</dependency>

	 <dependency>
	    <groupId>com.github.jwdeveloper.FluentFramework</groupId>
	    <artifactId>ff-core</artifactId>
	    <version>0.0.5-Release</version>
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
import io.github.jwdeveloper.ff.extension.items.FluentItemFramework;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMain extends JavaPlugin {

    @Override
    public void onEnable() 
    {
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


