package io.github.jwdeveloper.ff.core.async.cancelation;

import java.util.HashSet;
import java.util.Set;

public class CancelationTokenSource implements IAsyncCancelation
{
    private final Set<CancelationToken> tokens = new HashSet<>();

    private boolean canceld = false;

    public void attacheToken(CancelationToken cancelationToken)
    {
        tokens.add(cancelationToken);
    }

    public CancelationToken createToken()
    {
        var ctx = new CancelationToken();
        tokens.add(ctx);
        return ctx;
    }

    @Override
    public void cancel()
    {
        canceld = true;
        for(var token : tokens)
        {
            token.cancel();
        }
    }

    @Override
    public boolean isCancel() {
        return canceld;
    }

    @Override
    public boolean isNotCancel() {
        return !isCancel();
    }
}
