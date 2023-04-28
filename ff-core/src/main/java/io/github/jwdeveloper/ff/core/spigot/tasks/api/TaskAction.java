package io.github.jwdeveloper.ff.core.spigot.tasks.api;


import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;

public interface TaskAction
{
    void execute(int iteration, SimpleTaskTimer task);
}
