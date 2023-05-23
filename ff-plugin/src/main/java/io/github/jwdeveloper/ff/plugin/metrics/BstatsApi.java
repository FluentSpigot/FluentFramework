package io.github.jwdeveloper.ff.plugin.metrics;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class BstatsApi
{
    public static FluentApiExtension use(int bstatsMetricsId)
    {
        return new MetricsExtension(bstatsMetricsId);
    }
}
