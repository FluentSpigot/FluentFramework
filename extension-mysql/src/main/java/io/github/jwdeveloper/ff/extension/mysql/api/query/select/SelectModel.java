package io.github.jwdeveloper.ff.extension.mysql.api.query.select;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;

@Data
public class SelectModel
{
    private Map<String,String> columns = new HashMap<>();

    private SelectQueryType queryType = SelectQueryType.NONE;

    private String fromTable;

    private Class<?> fromTableClass;

    private String type;
}
