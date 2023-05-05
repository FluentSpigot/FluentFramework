package io.github.jwdeveloper.ff.extension.mysql.api.query.limit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
public class LimitModel {
    private int offset;
    private int count;
}
