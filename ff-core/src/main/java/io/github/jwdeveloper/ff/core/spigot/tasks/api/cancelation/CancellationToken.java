package io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation;

public class CancellationToken
{
    private boolean isCanceled =false;

    public void cancel()
    {
         isCanceled =true;
    }

    public boolean isCancel()
    {

        return isCanceled;
    }

    public void throwIfCancel()
    {
        if(!isCanceled)
        {
            return;
        }
        throw new CancelationException("Token requested cancellation");
    }

    public boolean isNotCancel()
    {
        return !isCancel();
    }

    public static CancellationToken create()
    {
        return new CancellationToken();
    }
}
