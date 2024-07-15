package io.github.jwdeveloper.ff.models.api.data;

import lombok.Data;
import org.bukkit.util.Vector;

@Data
public class BoneCube {
    private Vector transform = new Vector(0, 0, 0);
    private Vector scale = new Vector(1, 1, 1);

    private Vector rotation = new Vector(0, 0, 0);
}
