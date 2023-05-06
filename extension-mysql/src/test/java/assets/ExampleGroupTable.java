package assets;

import io.github.jwdeveloper.ff.extension.mysql.api.DbTypes;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.Column;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.Key;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.Table;
import lombok.Data;

@Data
@Table(name = "Groups")
public class ExampleGroupTable
{
    @Key
    @Column(name = "id",type = DbTypes.INT)
    private int id;

    @Column(name = "name")
    private String groupName;
}
