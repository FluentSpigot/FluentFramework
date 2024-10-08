package io.github.jw.spigot.ff.example;

import immersive.ImmersiveBlocksExtension;
import io.github.jw.spigot.ff.example.commands.AnimationCmd;
import io.github.jw.spigot.ff.example.commands.DisplayEntity;
import io.github.jw.spigot.ff.example.commands.EntityTest;
import io.github.jw.spigot.ff.example.commands.RandomStuff;
import io.github.jw.spigot.ff.example.drill.MiningCartInventory;
import io.github.jw.spigot.ff.example.menu.ItemMenuExtension;
import io.github.jw.spigot.ff.example.resource.ResourcepackWatcher;
import io.github.jwdeveloper.ff.DisplayModelFramework;
import io.github.jwdeveloper.ff.extension.bai.BlockAndItemsApi;
import io.github.jwdeveloper.ff.extension.bai.BlocksAndItemsFramework;
import io.github.jwdeveloper.ff.extension.gui.FluentInventoryApi;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleFFPlugin extends JavaPlugin implements FluentApiExtension {


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.useExtension(BlocksAndItemsFramework.use());
        builder.useExtension(FluentInventoryApi.use());
        builder.useExtension(DisplayModelFramework.use());

   //     builder.useExtension(new ItemMenuExtension());
        builder.useExtension(new ImmersiveBlocksExtension());
      //  builder.container().registerSingleton(MiningCartInventory.class);
     //   builder.useExtension(new Extension());
    }


    @Override
    public void onEnable() {
        FluentPlugin.initialize(this)
                .withExtension(this)
                .withFiles(fluentFilesOptions ->
                {
                    var outputPath = "C:\\Users\\ja\\curseforge\\minecraft\\Instances\\ServerTester\\resourcepacks\\resourcepack";
                   // var outputPath = "C:\\Users\\ja\\AppData\\Roaming\\.minecraft\\resourcepacks\\testresourcepack";
                    var inputPath = "D:\\Git\\fluent-framework\\example-ff-plugin\\resourcepack";
                    fluentFilesOptions.addFolderWatcher(new ResourcepackWatcher(inputPath, outputPath), inputPath);
                })
                .withCommand(options ->
                {
                    options.create(EntityTest.class);
                    options.create(RandomStuff.class);
                    options.create(AnimationCmd.class);
                    options.create(DisplayEntity.class);
                })
                .withTranslator()
                .create();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
