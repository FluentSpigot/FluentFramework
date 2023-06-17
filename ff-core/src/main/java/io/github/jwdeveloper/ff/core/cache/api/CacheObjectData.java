package io.github.jwdeveloper.ff.core.cache.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CacheObjectData {
    private Object object;
    private boolean isForever;
    private LocalDateTime createdAt;
    private Duration duration;
}
