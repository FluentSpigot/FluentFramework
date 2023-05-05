package io.github.jwdeveloper.ff.extension.mysql.api.query.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderModel {
    private String[] columns;
    private boolean isAsc;
}
