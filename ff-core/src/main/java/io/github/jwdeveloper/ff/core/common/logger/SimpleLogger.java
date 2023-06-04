package io.github.jwdeveloper.ff.core.common.logger;

import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger implements PluginLogger {
    private final String errorBar;

    @Getter
    @Setter
    private boolean active = true;

    @Getter
    @Setter
    private String prefix = "";

    @Getter
    private EventGroup<String> logEvents;

    public SimpleLogger(String prefix) {
        this();
        this.prefix = prefix;
    }

    public SimpleLogger() {
        errorBar = getBuilder().newLine().text(ChatColor.BOLD).text(ChatColor.DARK_RED).bar("-", 100).text(ChatColor.RESET).newLine().toString();
        logEvents = new EventGroup<>();
    }

    private void send(String message) {
        if (!isActive()) {
            return;
        }

        if (Bukkit.getServer() != null) {
            Bukkit.getConsoleSender().sendMessage(message);
        }
        else
        {
            System.out.println(message);
        }
        logEvents.invoke(message);
    }

    public void info(Object... messages) {
        if (!isActive()) {
            return;
        }

        var message = getBuilder()
                .text(getPrefix("Info", ChatColor.AQUA))
                .text(messages)
                .toString();
        send(message);
    }

    public void success(Object... messages) {
        if (!isActive()) {
            return;
        }

        var message = getBuilder()
                .text(getPrefix("Success", ChatColor.GREEN))
                .text(messages)
                .toString();
        send(message);
    }

    public void warning(Object... messages) {
        if (!isActive()) {
            return;
        }

        var message = getBuilder()
                .text(getPrefix("Warning", ChatColor.YELLOW))
                .text(messages)
                .toString();
        send(message);
    }

    public void error(String message) {
        if (!isActive()) {
            return;
        }

        var msg = getBuilder()
                .text(getPrefix("Error", ChatColor.RED))
                .text(message)
                .toString();
        send(msg);
    }

    public void error(String message, Throwable throwable) {
        if (!isActive()) {
            return;
        }

        var description = getErrorDescription(message, throwable);
        var stackTrace = getStackTrace(throwable);
        var errorMessage = getBuilder()
                .text(description, errorBar, stackTrace, errorBar)
                .toString();
        send(errorMessage);
    }




    private TextBuilder getErrorDescription(String message, Throwable exception) {
        var stackTrace = new TextBuilder();

        stackTrace.newLine()
                .text(getPrefix("Critical Error",ChatColor.DARK_RED)).space()
                .text(ChatColor.RED).text(message)
                .text(ChatColor.RESET);
        if (exception == null) {
            return stackTrace;
        }
        var cause = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        stackTrace.newLine().text(ChatColor.DARK_RED)
                .text(getPrefix("Reason",ChatColor.DARK_RED))
                .text(ChatColor.YELLOW)
                .space()
                .text(cause)
                .text(ChatColor.RESET)
                .newLine()
                .text(getPrefix("Exception type",ChatColor.DARK_RED))
                .text(ChatColor.YELLOW).space().text(exception.getClass().getSimpleName())
                .text(ChatColor.RESET);
        return stackTrace;
    }

    private TextBuilder getStackTrace(Throwable exception) {
        var builder = new TextBuilder();
        if (exception == null) {
            return builder;
        }
        var offset = 6;
        builder.text(ChatColor.RESET);
        for (var trace : exception.getStackTrace()) {
            offset = 4;
            offset = offset - (trace.getLineNumber() + "").length();
            builder
                    .newLine()
                    .text(ChatColor.GRAY,"At line")
                    .space(1)
                    .text(ChatColor.AQUA,trace.getLineNumber())
                    .space(offset)
                    .text(ChatColor.GRAY,"in")
                    .space()
                    .text(ChatColor.WHITE)
                    .text(trace.getClassName())
                    .text(ChatColor.AQUA)
                    .text( "." + trace.getMethodName() + "()")
                    .space()
                    .text(ChatColor.RESET);
        }
        return builder;
    }

    private String getPrefix(String name, ChatColor color) {

        var builder =getBuilder();
        if(StringUtils.isNotNullOrEmpty(prefix))
        {
            builder.text(ChatColor.GRAY).text("["+prefix+"]");
        }

        return builder.text(color, "["+ name+"]", ChatColor.RESET).toString();
    }

    private TextBuilder getBuilder() {
        return new TextBuilder();
    }

}
