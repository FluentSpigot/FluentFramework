package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;

import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.common.TextBuilder;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.GameObject;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.Transportation;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import lombok.Setter;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Vector;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class TransformationInfoComponent extends GameComponent {

    private TextDisplay textDisplay;

    @Setter
    private GameObject tracking;
    @Setter
    private boolean visible;


    @Setter
    private boolean showAsLocal = true;

    @Setter
    private boolean showPosition;
    @Setter
    private boolean showTransation = true;

    @Setter
    private boolean showRotation;
    @Setter
    private boolean showScale;

    @Override
    public void onRender() {

        if (visible && tracking != null) {
            Transportation transportation = null;
            if (showAsLocal) {
                transportation = tracking.transform();
            } else {
                transportation = tracking.worldTransform();
            }

            if (textDisplay == null) {
                textDisplay = (TextDisplay) transform().world().spawnEntity(transform().toLocation(), EntityType.TEXT_DISPLAY);

                textDisplay.setDisplayHeight(10);
                textDisplay.setBillboard(Display.Billboard.CENTER);
                textDisplay.setDisplayWidth(10);
                textDisplay.setAlignment(TextDisplay.TextAlignment.LEFT);
                textDisplay.setSeeThrough(true);
                return;
            }


            var builder = new MessageBuilder();
            if (showPosition)
                displayVector(builder, "Position", transportation.position());

            if (showTransation)
                displayVector(builder, "Translation", transportation.translation());

            if (showScale)
                displayVector(builder, "Scale", transportation.scale());


            if (showRotation)
                displayVector(builder, "Rotation", transportation.rotation());


            var trans = transform().clone();
            trans.rotation(0, 0, 0);

            textDisplay.setText(builder.toString());
            textDisplay.teleport(tracking.worldTransform().toLocation());
            textDisplay.setTransformation(trans.toBukkitTransformation());

        }
        if (textDisplay != null) {
            textDisplay.setDisplayHeight(0);
            textDisplay.setDisplayWidth(0);
        }
    }


    public void displayVector(TextBuilder builder, String name, Vector vector) {
        builder.text(name, ": ").newLine(true);
        builder.text("X: ").floor(vector.getX()).newLine(true);
        builder.text("Y: ").floor(vector.getY()).newLine(true);
        builder.text("Z: ").floor(vector.getZ()).newLine(true);
    }

    @Override
    public void onDestroy() {
        if (textDisplay != null) {
            textDisplay.remove();
        }
    }
}
