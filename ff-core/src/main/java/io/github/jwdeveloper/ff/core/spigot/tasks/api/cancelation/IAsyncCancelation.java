package io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation;

public interface IAsyncCancelation
{
     void cancel();

     boolean isCancel();

     boolean isNotCancel();
}
