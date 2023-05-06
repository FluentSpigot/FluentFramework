package assets;

import io.github.jwdeveloper.ff.extension.mysql.api.DbTypes;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.Column;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.ForeignKey;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.Key;
import io.github.jwdeveloper.ff.extension.mysql.api.annotations.Table;
import lombok.Data;

@Table(name = "Users")
@Data
public class ExampleUserTable
{
    @Key
    @Column(name = "id",type = DbTypes.INT)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age",type = DbTypes.INT)
    private int age;



}