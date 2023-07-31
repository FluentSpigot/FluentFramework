package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.npc;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class NpcNameComponent extends GameComponent
{

}
