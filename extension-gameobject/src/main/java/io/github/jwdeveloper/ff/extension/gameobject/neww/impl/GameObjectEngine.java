package io.github.jwdeveloper.ff.extension.gameobject.neww.impl;

import io.github.jwdeveloper.ff.core.workers.background.BackgroundWorkerBase;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancelationException;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.core.common.StopWatch;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception.GameObjectEngineException;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.exception.GameObjectException;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameObjectImpl;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.events.GameObjectAttatcheEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class GameObjectEngine extends BackgroundWorkerBase {

    private final PluginLogger logger;
    private final Set<GameObjectImpl> gameObjects;
    private final Queue<GameObjectImpl> renderQueue;
    private final Queue<GameObjectImpl> initializeQueue;
    private final Queue<GameObjectImpl> destroyQueue;
    private final StopWatch stopWatch;
    private double gameTime;
    private long tickTimeout;


    public GameObjectEngine(FluentTaskFactory factory, FluentEventManager events) {
        super(factory);
        events.onEvent(GameObjectAttatcheEvent.class, this::onGameObjectAttachEvent);
        events.onEvent(GameObjectAttatcheEvent.class, this::onGameObjectDestroyEvent);

        tickTimeout = 20;
        gameTime = 0;

        logger = new SimpleLogger("GameObjectEngine");
        stopWatch = new StopWatch();
        gameObjects = new HashSet<>();
        initializeQueue = new ConcurrentLinkedQueue<>();
        destroyQueue = new ConcurrentLinkedQueue<>();
        renderQueue = new ConcurrentLinkedQueue<>();

        logger.disable();
    }


    @Override
    public void onWork(CancellationToken cancelationToken) {
        gameTime = 0;
        while (cancelationToken.isNotCancel()) {
            try {
                if (isPaused()) {
                    Thread.sleep(tickTimeout);
                    continue;
                }


                logger.info("Next loop cycle for delta: ", gameTime, "gameobjects", gameObjects.size());
                stopWatch.start();
                tryLoopCycle(cancelationToken);
                stopWatch.stop();
                logger.info("Main Loop cycle: ", stopWatch.toString());
                logger.info(" ");
                logger.info(" ");

            } catch (CancelationException ex) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
                throw new GameObjectEngineException("Error while running main loop ", e);

            }
        }
    }

    public void tryLoopCycle(CancellationToken cancelationToken) throws InterruptedException {
        runEventAsync(gameObject -> gameObject.events.phisicAsyncEvent.invoke(null), "PhisicAsync");
        runEventAsync(gameObject -> gameObject.events.updateAsyncEvent.invoke(gameTime), "UpdateAsync");
        runEventAsync(gameObject -> gameObject.events.renderAsyncEvent.invoke(null), "PreRenderAsync");
        waitForBukkitThread(() ->
        {
            doDestroy();
            doRender();
            doInitialization();
            return true;
        }, cancelationToken);
        gameTime += 0.01d;
        Thread.sleep(tickTimeout);

    }

    public void runEventAsync(Consumer<GameObjectImpl> onEvent, String eventName) {
        stopWatch.start();
        for (var go : gameObjects) {
            cancelationToken.throwIfCancel();
            onEvent.accept(go);
        }
        stopWatch.stop();
        logger.info("Event ", eventName, ": ", stopWatch.toString());
    }


    public void doInitialization() {
        for (var go : initializeQueue)
        {
            if (gameObjects.contains(go)) {
                continue;
            }

            try {
                logger.success("Initializing object of: " + go.meta.getName());
                cancelationToken.throwIfCancel();
                go.events.initializationEvent.invoke(FluentApi.getFluentApiSpigot());
                cancelationToken.throwIfCancel();
                go.events.enableEvent.invoke(null);
                gameObjects.add(go);

            } catch (GameObjectException e) {
                logger.error("Unable to initialize gameobject", e);
            } catch (Exception e) {
                logger.error("Unable to initialize gameobject", e);
            }

        }
        initializeQueue.clear();
    }

    public void doRender() {
        for (var go : gameObjects) {
            cancelationToken.throwIfCancel();
            go.events.renderEvent.invoke(null);

        }
        renderQueue.clear();
    }

    public void doDestroy() {
        for (var component : destroyQueue) {
            try {
                component.events.destroyEvent.invoke(null);
                gameObjects.remove(component);
            } catch (Exception ignored) {

            }
        }
        destroyQueue.clear();
    }


    @Override
    public void onClose() {
        destroyQueue.addAll(gameObjects);
        doDestroy();
    }

    public void attache(GameObject gameObject) {
        if (gameObject instanceof GameObjectImpl impl) {
            initializeQueue.add(impl);
        } else {
            throw new GameObjectEngineException("GameObject implementation must be assignable from GameObjectImpl");
        }
    }

    public void destroy(GameObject gameObject) {
        if (gameObject instanceof GameObjectImpl impl) {
            destroyQueue.add(impl);
        } else {
            throw new GameObjectEngineException("GameObject implementation must be assignable from GameObjectImpl");
        }
    }

    public void onGameObjectAttachEvent(GameObjectAttatcheEvent event) {
        attache(event.getGameObject());
    }


    public void onGameObjectDestroyEvent(GameObjectAttatcheEvent event) {
        destroy(event.getGameObject());
    }
}
