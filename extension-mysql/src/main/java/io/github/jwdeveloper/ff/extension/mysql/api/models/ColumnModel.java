package io.github.jwdeveloper.ff.extension.mysql.api.models;

import io.github.jwdeveloper.ff.extension.mysql.api.enums.ReferenceOption;
import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ColumnModel
{
    private String name ="";

    private boolean isRequired;

    private String type;

    private int size;

    private boolean isKey;

    private boolean isPrimaryKey;

    private boolean isAutoIncrement;

    private boolean isForeignKey;

    private String foreignKeyReference;

    private String foreignKeyName;

    private String foreignKeyTableName;
    private ReferenceOption onDelete;

    private ReferenceOption onUpdate;

    public String getOnDeleteString()
    {
        return onDelete.name().replace("_"," ");
    }
    private Field field;

    public void setField(Field field) {
        field.setAccessible(true);
        this.field = field;
    }
}
