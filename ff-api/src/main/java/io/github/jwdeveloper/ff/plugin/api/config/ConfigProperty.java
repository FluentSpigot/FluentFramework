package io.github.jwdeveloper.ff.api.api.config;

public record ConfigProperty<T> (String path, T defaultValue, String ... description) {
}
