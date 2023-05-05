package io.github.jwdeveloper.ff.extension.mysql.api.query.where;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WhereInstruction
{
    private String name;

    private Object[] params;


    public static WhereInstruction create(String name, Object... params)
    {
        return new WhereInstruction(name, params);
    }
}
