package io.github.jwdeveloper.ff.core.async.cancelation;

public interface IAsyncCancelation
{
     void cancel();

     boolean isCancel();

     boolean isNotCancel();
}
