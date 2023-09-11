package io.github.jwdeveloper.ff.tools.description.documentation.api.builders;


import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;

public class YmlBuilder
{
    private TextBuilder builder;

    public YmlBuilder()
    {
        builder = new TextBuilder();
    }

    public YmlBuilder addSection(String name)
    {
        return addSection(name,0);
    }

    public YmlBuilder addSection(String name, int offset)
    {
        return addProperty(name, StringUtils.EMPTY,offset);
    }

    public YmlBuilder addListProperty(String member, int offset)
    {
        builder.space(offset).text("- ").text(member).newLine();
        return this;
    }

    public YmlBuilder addListSection(String member, int offset)
    {
        builder.space(offset).text("- ").text(member).text(":").newLine();
        return this;
    }

    public YmlBuilder addProperty(String field, Object value)
    {
        return addProperty(field,value,0);
    }

    public YmlBuilder addProperty(String field, Object value, int offset)
    {
        builder.space(offset).text(field).text(":").space().text(value).newLine();
        return this;
    }

    public YmlBuilder addComment(String comment)
    {
        builder.text("#").space().text(comment).newLine();
        return this;
    }

    public YmlBuilder newLine()
    {
        builder.newLine();
        return this;
    }

    public String build()
    {
        return builder.toString();
    }
}
