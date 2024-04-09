package io.github.jw.spigot.ff.example.drill;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.particles.FluentParticle;
import io.github.jwdeveloper.ff.core.spigot.particles.api.ParticleDisplayMode;
import io.github.jwdeveloper.ff.extension.bai.BlockAndItemsApi;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.DisplayFactory;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MiningCart {
    public void register(FluentApiSpigot fluentAPI) {
        var api = fluentAPI.container().findInjection(BlockAndItemsApi.class);
        var item = api.items()
                .addItem()
                .withEvents(events ->
                {
                    events.onRightClick(this::handlePlace);
                })
                .withCrafting(builder ->
                {
                    builder.setSlot(1, Material.DIAMOND);
                })
                .withSchema(schema ->
                {
                    schema.withName("mining-drill-cart");
                    schema.withCustomModelId(517);
                    schema.withMaterial(Material.WOODEN_PICKAXE);
                    schema.withDisplayName("Mining CART");
                    schema.withStackable(false);
                }).buildAndRegister();


        api.craftings()
                .addCrafting()
                .setSlot(0, item)
                .setSlot(1, Material.REDSTONE_BLOCK)
                .withOutput((fluentCrafting, matrix) ->
                {
                    var itemStack = matrix[0].clone();
                    var meta = itemStack.getItemMeta();
                    meta.setDisplayName("dupa");
                    itemStack.setItemMeta(meta);
                    return itemStack;
                })
                .buildAndRegister();
    }

    private void handlePlace(FluentItemUseEvent event) {

        FluentLogger.LOGGER.info("Siema@");
        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
            return;
        }
        var block = spigotEvent.getClickedBlock();
        var face = spigotEvent.getBlockFace();
        if (block.getType().isAir()) {
            return;
        }
        var loc = block.getLocation();
        loc = loc.add(face.getDirection().toBlockVector());

        var world = loc.getWorld();
        var cart = (Minecart) world.spawnEntity(loc, EntityType.MINECART);

    }

    public static void createMiningCart(Minecart cart) {


        var item = FluentApi.container().findInjection(FluentItemApi.class).findFluentItem("mining-drill-cart").get();
        var factory = FluentApi.container().findInjection(DisplayFactory.class);

        var display = factory.createDisplay(cart.getLocation(), item.toItemStack());
        cart.addPassenger(display);
        FluentApi.events().onEvent(VehicleDestroyEvent.class, event ->
        {
            if (!event.getVehicle().equals(cart)) {
                return;
            }
            display.remove();
        });
        FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {
            display.setRotation(cart.getLocation().getYaw(), cart.getLocation().getPitch());
        }).start();
        FluentParticle.create(FluentApi.plugin())
                .triggerEveryTicks(3)
                .nextStep()
                .withParticleCount(4)
                .withEntityTracking(cart)
                .withDisplayMode(ParticleDisplayMode.SINGLE_AT_ONCE)
                .withParticle(Particle.CAMPFIRE_SIGNAL_SMOKE)
                .nextStep()
                .onParticle((particle, particleInvoker) ->
                {
                    var loc = particle.getLocation();
                    var world = loc.getWorld();
                    world.spawnParticle(particle.getParticle(), loc.getX(), loc.getY() + 1, loc.getZ(), 1, 0, 1, 0, 0.1);
                })
                .nextStep()
                .buildAndStart();
        FluentApi.tasks().taskTimer(20, (iteration, task) ->
        {
            var direction = rotateVectorBy270Degrees(cart.getLocation().getDirection());
            var directionLocation = cart.getLocation().add(direction);
            var directionBlock = directionLocation.getBlock();
            var directionBlockHigher = directionLocation.add(0, 1, 0).getBlock();


            if (directionBlock.getType().isAir()) {
                directionBlock.setType(Material.RAIL);
                cart.setVelocity(cart.getLocation().getDirection().multiply(1.1f));
            }
            if (!cart.getLocation().getBlock().getType().equals(Material.RAIL)) {
                FluentLogger.LOGGER.info(cart.getLocation().getBlock().getType());
                cart.getLocation().getBlock().setType(Material.RAIL);
                cart.setVelocity(cart.getLocation().getDirection().multiply(1.1f));
            }


            if (directionBlockHigher.getType().isAir()) {
                return;
            }
            var blockFace = directionBlockHigher.getFace(cart.getLocation().add(0, 1, 0).getBlock());
            MiningDrill.doDrill(directionBlockHigher, blockFace, cart);
        }).start();
    }


    public static Vector rotateVectorBy90Degrees(Vector direction) {
        // Get the current X, Y, Z components of the vector
        double x = direction.getX();
        double z = direction.getZ();

        // Rotate the vector by 90 degrees around the Y-axis
        double rotatedX = z;
        double rotatedZ = -x;

        // Return the new vector
        return new Vector(rotatedX, direction.getY(), rotatedZ);
    }

    public static Vector rotateVectorBy270Degrees(Vector direction) {
        // Get the current X, Y, Z components of the vector
        double x = direction.getX();
        double z = direction.getZ();

        // Rotate the vector by 270 degrees around the Y-axis (or 90 degrees to the left)
        double rotatedX = -z;
        double rotatedZ = x;

        // Return the new vector
        return new Vector(rotatedX, direction.getY(), rotatedZ);
    }
}
