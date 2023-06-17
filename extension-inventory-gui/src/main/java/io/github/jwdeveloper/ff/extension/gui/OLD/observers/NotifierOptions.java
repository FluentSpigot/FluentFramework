package io.github.jwdeveloper.ff.extension.gui.OLD.observers;

import lombok.Getter;

import java.util.UUID;

public class NotifierOptions {
    @Getter
    private String id = UUID.randomUUID().toString();
}
