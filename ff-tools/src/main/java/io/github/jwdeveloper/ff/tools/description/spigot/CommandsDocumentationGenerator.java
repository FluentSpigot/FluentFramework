package io.github.jwdeveloper.ff.tools.description.spigot;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.tools.description.documentation.api.builders.YmlBuilder;
import io.github.jwdeveloper.spigot.commands.api.Command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandsDocumentationGenerator {


    private List<String> allCommandsNames = new ArrayList<>();

    public String generate(Collection<Command> commands) {

        var commandInfoBuilder = createYmlBuilder();
        commandInfoBuilder.newLine();
        commandInfoBuilder.newLine();
        commandInfoBuilder.addSection("commands");
        for (var command : commands) {
            renderCommandInfo(commandInfoBuilder, command);
        }

        var commandInfoResult = commandInfoBuilder.build();


        var output = createYmlBuilder();
        output.addComment("Commands");
        for (var cmd : allCommandsNames) {
            output.addComment(cmd);
        }
        output.addSection(commandInfoResult);
        return output.build();
    }

    protected YmlBuilder createYmlBuilder() {
        return new YmlBuilder();
    }


    private void renderCommandInfo(YmlBuilder builder, Command command) {
        var defaultOffset = 2;
        var propertyOffset = 4;
        var listOffset = 6;
        var model = command.properties();
        var title = StringUtils.isNullOrEmpty(model.usageMessage()) ? model.name() : model.usageMessage();
        builder.addComment(title);
        allCommandsNames.add(title);
        builder.addSection(model.name(), defaultOffset);
        if (!command.children().isEmpty()) {
            builder.addSection("children", propertyOffset);
            for (var subCommand : command.children()) {
                builder.addListProperty(subCommand.name(), listOffset);
            }
        }

        if (!model.permission().isEmpty()) {
            builder.addSection("permissions", propertyOffset);
            builder.addListProperty(model.permission(), listOffset);
        }


        if (!model.excludedSenders().isEmpty()) {
            builder.addSection("can-not-use", propertyOffset);
            for (var access : model.excludedSenders()) {
                builder.addListProperty(access.name().toLowerCase(), listOffset);
            }
        }


        if (!command.arguments().isEmpty()) {
            builder.addSection("arguments", propertyOffset);
            for (var argument : command.arguments()) {
                builder.addListSection(argument.name(), listOffset);
                builder.addProperty("type", argument.type(), listOffset + 4);
                if (!StringUtils.isNullOrEmpty(argument.name())) {
                    builder.addProperty("description", argument.description(), listOffset + 4);
                }
            }
        }

        if (!StringUtils.isNullOrEmpty(model.shortDescription())) {
            builder.addProperty("short-description", model.shortDescription(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.description())) {
            builder.addProperty("description", model.description(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.label())) {
            builder.addProperty("label", model.label(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.usageMessage())) {
            builder.addProperty("usage", model.usageMessage(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.permission())) {
            builder.addProperty("permission-message", model.permission(), propertyOffset);
        }

        for (var subCommand : command.children()) {
            renderCommandInfo(builder, subCommand);
        }
        builder.newLine();
    }


    private void renderCommandTreeMember(YmlBuilder builder, Command command, int offset) {
        builder.addSection(command.name(), offset);
        offset = offset + 2;
        for (var subCommand : command.children()) {
            renderCommandTreeMember(builder, subCommand, offset);
        }
    }


}

