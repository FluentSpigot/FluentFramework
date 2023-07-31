package io.github.jwdeveloper.ff.core.async.cancelation;

public class CancelationToken
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
        throw new CancelationException("Token requested cancelation");
    }

    public boolean isNotCancel()
    {
        return !isCancel();
    }

    public static CancelationToken create()
    {
        return new CancelationToken();
    }

}
