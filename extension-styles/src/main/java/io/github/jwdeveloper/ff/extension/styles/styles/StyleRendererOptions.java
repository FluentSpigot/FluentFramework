package io.github.jwdeveloper.ff.extension.styles.styles;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


public class StyleRendererOptions {

    @Setter
    @Getter
    private String cacheId;

    @Getter
    @Setter
    private boolean useCache;

    private final Map<String, Function<StyleRenderEvent, String>> parameters;

    public StyleRendererOptions() {
        parameters = new LinkedHashMap<>();
    }

    public void addParameter(String parameter, Function<StyleRenderEvent, String> value) {
        parameters.put(parameter, value);
    }

    public void reset()
    {
        parameters.clear();;
    }

    public void addParameter(String parameter, String value) {
        addParameter(parameter, (e) -> value);
    }

    public boolean hasCacheID() {
        return !StringUtils.isNullOrEmpty(cacheId);
    }

    public boolean hasAnyParameter() {
        return !parameters.isEmpty();
    }

    public boolean hasParameter(String parameterName) {
        return parameters.containsKey(parameterName);
    }

    public Optional<Function<StyleRenderEvent, String>> getParameter(String parameterName) {
        if (!hasParameter(parameterName)) {
            return Optional.empty();
        }
        return Optional.of(parameters.get(parameterName));
    }

    public Map<String, Function<StyleRenderEvent, String>> getParametersByContains(String query) {

        var result = new LinkedHashMap<String, Function<StyleRenderEvent, String>>();
        for (var entry : parameters.entrySet()) {
            if (!entry.getKey().contains(query)) {
                continue;
            }
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
