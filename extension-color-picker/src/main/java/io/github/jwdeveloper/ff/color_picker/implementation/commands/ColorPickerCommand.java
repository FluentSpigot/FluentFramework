/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveloper.ff.color_picker.implementation.commands;

import io.github.jwdeveloper.ff.color_picker.implementation.ColorPicker;
import io.github.jwdeveloper.spigot.commands.api.builder.CommandBuilder;

public class ColorPickerCommand {
    public static CommandBuilder getCommand(CommandBuilder builder) {
        builder.withDescription("command used for internal color picker system, just ignore it");
        builder.withHideFromSuggestions(true);
        builder.onPlayerExecute(event ->
        {
            if (!event.sender().isOp()) {
                return;
            }
            event.sender().sendMessage("Command used for internal color picker system, just ignore it");
        });


        builder.addSubCommand("page", subBuilder ->
        {
            subBuilder.addArgument("color");
            subBuilder.onPlayerExecute(event ->
            {
                var color = event.getText("color");
                var picker = event.command().container().find(ColorPicker.class);
                picker.handlePageSelection(event.sender(), color);
            });
        });
        return builder;
    }

}
