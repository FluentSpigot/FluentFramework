package io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation;

import java.util.HashSet;
import java.util.Set;

public class CancelationTokenSource implements IAsyncCancelation
{
    private final Set<CancellationToken> tokens = new HashSet<>();

    private boolean canceld = false;

    public void attacheToken(CancellationToken cancelationToken)
    {
        tokens.add(cancelationToken);
    }

    public CancellationToken createToken()
    {
        var ctx = new CancellationToken();
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
