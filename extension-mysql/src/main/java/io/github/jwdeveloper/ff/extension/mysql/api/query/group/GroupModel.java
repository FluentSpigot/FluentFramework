package io.github.jwdeveloper.ff.extension.mysql.api.query.group;

import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereModel;
import lombok.Data;

@Data
public class GroupModel
{
    private String[] columns;
    private WhereModel having;


    public GroupModel(String ... columns)
    {
        this.columns = columns;
    }

    public GroupModel(WhereModel having, String ... columns)
    {
       this.having = having;
       this.columns = columns;
    }
}
