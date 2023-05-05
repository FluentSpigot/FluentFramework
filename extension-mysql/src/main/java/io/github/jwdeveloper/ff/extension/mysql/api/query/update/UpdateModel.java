package io.github.jwdeveloper.ff.extension.mysql.api.query.update;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UpdateModel
{
    private String table;

    private Map<String, Object> values = new HashMap<>();
}
