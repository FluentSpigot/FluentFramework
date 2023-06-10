package io.github.jwdeveloper.ff.tools.files.code;

import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MethodCodeGenerator {

    private String methodType = StringUtils.EMPTY;
    private String body = StringUtils.EMPTY;
    private String name = StringUtils.EMPTY;
    private String modifiers = StringUtils.EMPTY;
    private final List<String> parameters;
    private final List<String> comments;
    private final List<String> annotations;

    public MethodCodeGenerator() {
        parameters = new ArrayList<>();
        comments = new ArrayList<>();
        annotations = new ArrayList<>();
    }

    public MethodCodeGenerator addAnnotation(String annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public MethodCodeGenerator addComment(String comment) {
        this.comments.add(comment);
        return this;
    }

    public MethodCodeGenerator setType(String methodType) {
        this.methodType = methodType;
        return this;
    }

    public MethodCodeGenerator setType(Class methodType) {
        this.methodType = methodType.getSimpleName();
        return this;
    }

    public MethodCodeGenerator setName(String name) {
        this.name = name;
        return this;
    }

    public MethodCodeGenerator setModifiers(String modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public MethodCodeGenerator setBody(String body) {
        this.body = body;
        return this;
    }

    public MethodCodeGenerator addBodyLine(String line) {

        if(StringUtils.isNullOrEmpty(body))
        {
            this.body = line;
            return this;
        }

        this.body = body + StringUtils.separator() + line;
        return this;
    }

    public MethodCodeGenerator addBodyLine(Consumer<TextBuilder> consumer) {
        var builder = new TextBuilder();
        consumer.accept(builder);
        return addBodyLine(builder.toString());
    }


    public MethodCodeGenerator setBody(Consumer<TextBuilder> consumer) {
        var builder = new TextBuilder();
        consumer.accept(builder);
        this.body = builder.toString();
        return this;
    }

    public MethodCodeGenerator addParameter(String parameter) {
        this.parameters.add(parameter);
        return this;
    }

    public MethodCodeGenerator addParameter(Class clazz, String parameter) {
        this.parameters.add(clazz.getSimpleName() + " " + parameter);
        return this;
    }

    public MethodCodeGenerator addParameter(String modifier, Class clazz, String parameter) {
        return addParameter(modifier+" "+clazz.getSimpleName()+" "+parameter);
    }

    public String build() {
        var builder = new TextBuilder();

        for (var comment : comments) {
            builder.text("//").textNewLine(comment);
        }
        for (var annotation : annotations) {
            builder.text("@").textNewLine(annotation);
        }

        if(StringUtils.isNotNullOrEmpty(modifiers))
        {
           builder.text(modifiers).space();
        }
        if(StringUtils.isNotNullOrEmpty(methodType))
        {
            builder.text(methodType).space();
        }

        builder.text(name).text("(");
        for (var i = 0; i < parameters.size(); i++) {
            var param = parameters.get(i);
            builder.text(param);
            if (i != parameters.size() - 1) {
                builder.text(",").space();
            }
        }
        builder.textNewLine(")");
        builder.textNewLine("{");
        for (var line : body.lines().toList())
        {
            builder.space(4).textNewLine(line);
        }

        builder.textNewLine("}");

        return builder.toString();
    }

}
