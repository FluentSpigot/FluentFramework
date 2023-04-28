package io.github.jwdeveloper.ff.core.spigot.commands.implementation.validators;

import io.github.jwdeveloper.ff.core.spigot.commands.api.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
