package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.drawing;

import io.github.jwdeveloper.ff.core.common.VectorUtility;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.util.Vector;
import org.joml.Vector2i;



import java.util.ArrayList;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ParticleOutlineComponent extends GameComponent {


    private List<Location> renderLocations;
    public final Settings settings;


    public ParticleOutlineComponent() {
        settings = new Settings();

    }

    @Override
    public void onInitialization(FluentApiSpigot api) {
        transform().scale().setX(2).setZ(2).setY(2);
    }



    @Override
    public void onUpdateAsync(double deltaTime) {
        var sin = 20 * Math.sin(deltaTime * Math.PI);
        transform().scale(sin,sin,sin);
        renderLocations = GetRenderLocations();
    }




    @Override
    public void onRender()
    {
        for (var location : renderLocations)
        {
            transform().world().spawnParticle(settings.particle, location, settings.particleCount, settings.dustOptions);
        }
    }

    private List<Location> GetRenderLocations() {
        var vertex = getVertex();
        var linesIndexes = getLinesIndexes();
        var locations = new ArrayList<Location>();

        for (Vector2i indexes : linesIndexes) {
            var a = vertex[indexes.x];
            var b = vertex[indexes.y];
            for (double time = 0; time <= 1; time += settings.density) {
                locations.add(VectorUtility.lerp3(a, b, time).toLocation(transform().world()));
            }
        }
        return locations;
    }


    private Vector[] getVertex() {
        var result = new Vector[8];

        result[0] = new Vector(0, 0, 0);
        result[1] = new Vector(1, 0, 0);
        result[2] = new Vector(1, 0, 1);
        result[3] = new Vector(0, 0, 1);


        result[4] = new Vector(0, 1, 0);
        result[5] = new Vector(1, 1, 0);
        result[6] = new Vector(1, 1, 1);
        result[7] = new Vector(0, 1, 1);


        for (var i = 0; i < result.length; i++) {
            var temp = result[i];


            temp = VectorUtility.rotate(temp, transform().rotation());
            temp = temp.multiply(transform().scale());
            result[i] = temp.add(transform().position());
        }
        return result;
    }


    private static Vector2i[] _lineIndexes;

    private static Vector2i[] getLinesIndexes() {

        if (_lineIndexes != null) {
            return _lineIndexes;
        }

        var result = new Vector2i[12];

        //Bottom Face
        result[0] = new Vector2i(0, 1);
        result[1] = new Vector2i(1, 2);
        result[2] = new Vector2i(3, 2);
        result[3] = new Vector2i(0, 3);


        //Top Face
        result[4] = new Vector2i(4, 5);
        result[5] = new Vector2i(5, 6);
        result[6] = new Vector2i(7, 6);
        result[7] = new Vector2i(4, 7);


        //Pillars
        result[8] = new Vector2i(0, 4);
        result[9] = new Vector2i(1, 5);
        result[10] = new Vector2i(2, 6);
        result[11] = new Vector2i(3, 7);

        _lineIndexes = result;
        return _lineIndexes;
    }


    public class Settings {
        @Setter
        private Vector2i[] pointsIndexes = new Vector2i[0];

        @Setter
        private Vector[] points = new Vector[0];

        @Setter
        private double density =0.1d;

        @Setter
        private Particle particle = Particle.REDSTONE;

        @Setter
        private int particleCount = 1;

        @Setter
        private Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GREEN,0.5f);
    }

}
