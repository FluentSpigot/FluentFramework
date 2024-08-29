package immersive;

import immersive.api.ImmersiveBlockHandler;
import immersive.api.ImmersiveBlockApi;
import immersive.handlers.AnvilBlock;
import immersive.handlers.CraftingBlock;
import immersive.handlers.EnchantBlock;
import immersive.common.ImpersiveBlockApiImpl;
import immersive.handlers.FurnaceBlock;
import immersive.listeners.BlocksListener;
import immersive.listeners.PlayerListener;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class ImmersiveBlocksExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();
        container.registerSingleton(BlocksListener.class);
//        container.registerSingleton(PlayerListener.class);
        container.registerSingleton(BlocksHandler.class);
        container.registerSingleton(ImmersiveBlockApi.class, ImpersiveBlockApiImpl.class);

        container.registerSingleton(ImmersiveBlockHandler.class, CraftingBlock.class);
        container.registerSingleton(ImmersiveBlockHandler.class, FurnaceBlock.class);
        container.registerSingleton(ImmersiveBlockHandler.class, AnvilBlock.class);
        container.registerSingleton(ImmersiveBlockHandler.class, EnchantBlock.class);

        container.registerSingletonList(ImmersiveBlockHandler.class);
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var cls = fluentAPI.container().findAllByInterface(ImmersiveBlockHandler.class);
        cls.forEach(immersiveBlock ->
        {
            FluentLogger.LOGGER.info("Handler!", immersiveBlock.getClass().getName());
        });

        fluentAPI.container().findInjection(BlocksListener.class);
     //   fluentAPI.container().findInjection(PlayerListener.class);
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

        fluentAPI.container().findInjection(PluginCache.class).close();
    }
}
