package io.github.jwdeveloper.ff.tools.description.spigot;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.tools.description.documentation.api.builders.YmlBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandsDocumentationGenerator {



    private List<String> allCommandsNames = new ArrayList<>();

    public String generate(Collection<SimpleCommand> commands) {

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
        for(var cmd : allCommandsNames)
        {
            output.addComment(cmd);
        }
        output.addSection(commandInfoResult);
        return output.build();
    }

    protected YmlBuilder createYmlBuilder() {
        return new YmlBuilder();
    }


    private void renderCommandInfo(YmlBuilder builder, SimpleCommand command) {
        if (command.getCommandModel().isHideFromDocumentation()) {
            return;
        }

        var defaultOffset = 2;
        var propertyOffset = 4;
        var listOffset = 6;
        var model = command.getCommandModel();
        var title = StringUtils.isNullOrEmpty(model.getUsageMessage()) ? model.getName() : model.getUsageMessage();
        builder.addComment(title);
        allCommandsNames.add(title);
        builder.addSection(model.getName(), defaultOffset);
        if (!command.getSubCommands().isEmpty()) {
            builder.addSection("children", propertyOffset);
            for (var subCommand : command.getSubCommands()) {
                builder.addListProperty(subCommand.getName(), listOffset);
            }
        }

        if (!model.getPermissions().isEmpty()) {
            builder.addSection("permissions", propertyOffset);
            for (var permission : model.getPermissions()) {
                builder.addListProperty(permission, listOffset);
            }
        }


        if (!model.getCommandAccesses().isEmpty()) {
            builder.addSection("can-use", propertyOffset);
            for (var access : model.getCommandAccesses()) {
                builder.addListProperty(access.name().toLowerCase(), listOffset);
            }
        }

        if (!model.isAllParametersRequired()) {
            builder.addProperty("all-arguments-required", model.isAllParametersRequired(), propertyOffset);
        }

        if (model.getArguments().size() > 0) {
            builder.addSection("arguments", propertyOffset);
            for (var argument : model.getArguments()) {
                builder.addListSection(argument.getName(), listOffset);
                builder.addProperty("type", argument.getType().name().toLowerCase(), listOffset + 4);
                if (!StringUtils.isNullOrEmpty(argument.getDescription())) {
                    builder.addProperty("description", argument.getDescription(), listOffset + 4);
                }
                var tabCompliter = argument.getTabCompleter().get();
                if (!tabCompliter.isEmpty()) {
                    builder.addSection("options", listOffset + 4);
                    for (var tab : tabCompliter) {
                        builder.addListProperty(tab, listOffset + 8);
                    }
                }
            }
        }

        if (!StringUtils.isNullOrEmpty(model.getShortDescription())) {
            builder.addProperty("short-description", model.getShortDescription(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.getDescription())) {
            builder.addProperty("description", model.getDescription(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.getLabel())) {
            builder.addProperty("label", model.getLabel(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.getUsageMessage())) {
            builder.addProperty("usage", model.getUsageMessage(), propertyOffset);
        }

        if (!StringUtils.isNullOrEmpty(model.getPermissionMessage())) {
            builder.addProperty("permission-message", model.getPermissionMessage(), propertyOffset);
        }

        for (var subCommand : command.getSubCommands()) {
            renderCommandInfo(builder, subCommand);
        }
        builder.newLine();
    }


    private void renderCommandTreeMember(YmlBuilder builder, SimpleCommand command, int offset) {
        builder.addSection(command.getName(), offset);
        offset = offset + 2;
        for (var subCommand : command.getSubCommands()) {
            renderCommandTreeMember(builder, subCommand, offset);
        }
    }


}

