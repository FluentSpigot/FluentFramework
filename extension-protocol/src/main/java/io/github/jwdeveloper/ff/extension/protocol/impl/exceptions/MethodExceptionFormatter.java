package io.github.jwdeveloper.ff.extension.protocol.impl.exceptions;


import io.github.jwdeveloper.reflect.api.exceptions.MethodValidationException;
import io.github.jwdeveloper.reflect.api.validators.ValidationResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;

public class MethodExceptionFormatter {
    private final MethodValidationException exception;

    public record MappedError(String message, Method method) {
    }

    ;

    public MethodExceptionFormatter(MethodValidationException exception) {
        this.exception = exception;
    }


    public void show() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + exception.getMessage());


        var reas = new MappedError[exception.getValidationResult().getLogs().size()];
        var i = 0;
        for (var o : exception.getValidationResult().getLogs()) {
            var cast = (ValidationResult<?>) o;
            var method = (Method) cast.getValue();
            reas[i] = new MappedError(cast.getMessage(), method);
            i++;
        }
        Arrays.sort(reas, new UserNameComparator());

        for (var result : reas) {
            if (Modifier.isNative(result.method().getModifiers())) {
                continue;
            }

            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + result.message() + " " + ChatColor.YELLOW + formatMethod(result.method()));
        }
    }


    private String formatMethod(Method method) {


        var sj = new StringBuilder();
        sj.append(ChatColor.BLUE + " ").append(Modifier.toString(method.getModifiers()));
        sj.append(ChatColor.AQUA + " ").append(method.getReturnType().getName());
        sj.append(ChatColor.YELLOW + " ").append(method.getName());
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0) {
            sj.append(ChatColor.WHITE + "()");
            return sj.toString();
        }

        sj.append(ChatColor.WHITE + "(");

        StringJoiner argJoiner = new StringJoiner(ChatColor.WHITE + ", ");
        for (Class<?> parameter : method.getParameterTypes()) {
            argJoiner.add(ChatColor.DARK_AQUA + parameter.getName());
        }

        sj.append(argJoiner.toString());
        sj.append(ChatColor.WHITE + ")");
        return sj.toString();
    }

    class UserNameComparator implements Comparator<MappedError> {
        public int compare(MappedError a, MappedError b) {
            return a.method().getName().compareTo(b.method().getName());
        }
    }

}