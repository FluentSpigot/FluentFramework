package io.github.jwdeveloper.ff.extension.gameobject.neww.api.core;

public interface GameObjectMetadata
{
    boolean hasTag(String tag);

    void setTag(String tag);

    String getTag();

    boolean isActive();

    boolean isVisible();

    void setActive(boolean active);

    void setName(String name);

    String  getName();
}
