package io.github.jwdeveloper.ff.core.spigot.player;

import io.github.jwdeveloper.ff.core.common.MathHelper;
import lombok.Getter;
import org.bukkit.Location;

public class BodyRotation {

    float bodyYaw = 0;

    public float tickMovement(Location from, Location to)
    {
        // Our yaw is actually the internal yaw of the player, rather than the wrapped yaw
        float yaw = true ? to.getYaw() : bodyYaw;
        double i = to.getX() - from.getX();
        double d = to.getZ() - from.getZ();
        float f = (float) (i * i + d * d);
        float g = this.bodyYaw;
        if (f > 0.0025000002F) {
            // Using internal Mojang math utils here
            float l = (float) MathHelper.atan2(d, i) * 57.295776F - 90.0F;
            float m = MathHelper.abs(MathHelper.wrapDegrees(yaw) - l);
            if (95.0F < m && m < 265.0F) {
                g = l - 180.0F;
            } else {
                g = l;
            }
        }

        this.turnBody(g, yaw);
        return bodyYaw;
    }

    public void turnBody(float bodyRotation, float yaw) {
        float f = MathHelper.wrapDegrees(bodyRotation - bodyYaw);
        bodyYaw += f * 0.3F;
        float g = MathHelper.wrapDegrees(yaw - bodyYaw);
        if (g < -75.0F) {
            g = -75.0F;
        }

        if (g >= 75.0F) {
            g = 75.0F;
        }

        this.bodyYaw = yaw - g;
        if (g * g > 2500.0F) {
            this.bodyYaw += g * 0.2F;
        }
    }



}
