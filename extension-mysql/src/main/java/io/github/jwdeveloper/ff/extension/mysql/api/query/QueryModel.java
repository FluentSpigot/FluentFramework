package io.github.jwdeveloper.ff.extension.mysql.api.query;

import io.github.jwdeveloper.ff.extension.mysql.api.query.group.GroupModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.insert.InsertModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.limit.LimitModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.order.OrderModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.select.SelectModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.update.UpdateModel;
import io.github.jwdeveloper.ff.extension.mysql.api.query.where.WhereModel;
import lombok.Data;

@Data
public class QueryModel
{
    private SelectModel selectModel;

    private InsertModel insertModel;

    private UpdateModel updateModel;

    private WhereModel whereModel;

    private GroupModel groupModel;

    private OrderModel orderModel;

    private LimitModel limitModel;
}
