package io.github.jwdeveloper.ff.plugin.metrics;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

public class MetricsExtension implements FluentApiExtension {
    private final int metricsId;
    public MetricsExtension(int metricsId) {
        this.metricsId = metricsId;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        if (metricsId == 0) {
            return;
        }

        var metrics = new MetricsLite(builder.plugin(), metricsId);
        builder.container().registerSigleton(MetricsLite.class, metrics);
    }

}
