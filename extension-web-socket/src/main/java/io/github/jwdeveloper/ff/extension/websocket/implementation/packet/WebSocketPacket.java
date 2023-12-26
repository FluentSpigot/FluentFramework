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

package io.github.jwdeveloper.ff.extension.websocket.implementation.packet;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocketPacket;
import io.github.jwdeveloper.ff.extension.websocket.api.TypeResolver;
import io.github.jwdeveloper.ff.extension.websocket.api.annotations.PacketProperty;
import io.github.jwdeveloper.ff.extension.websocket.implementation.resolver.*;
import org.java_websocket.WebSocket;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public abstract class WebSocketPacket implements FluentWebsocketPacket {
    private int packetSize = 0;
    private final int packetIdSize = 4;
    private final List<PacketFieldWrapper> packetFields;
    private final Queue<Consumer<WebSocket>> tasks = new LinkedBlockingQueue<>();
    private  SimpleTaskTimer taskTimer;
    private final Gson gson;

    public abstract void onPacketTriggered(WebSocket webSocket);

    public abstract int getPacketId();

    public WebSocketPacket(FluentTaskFactory manager) {
        gson = JsonUtility.getGson();
        packetFields = loadPacketFields();
        packetSize = getPacketSize();

        if(manager != null)
        {
            taskTimer = manager.taskTimer(1, (iteration, taskTimer) ->
            {
                for (final var task : tasks) {
                    task.accept(null);
                }
                tasks.clear();
            });
            taskTimer.start();
        }

    }

    protected void addSpigotTask(Consumer<WebSocket> consumer) {
        tasks.add(consumer);
    }

    private List<PacketFieldWrapper> loadPacketFields() {
        var fields = this.getClass().getDeclaredFields();
        final List<PacketFieldWrapper> packetFields = new ArrayList(fields.length);
        for (Field field : fields) {
            if (field.getAnnotation(PacketProperty.class) == null)
                continue;
            var observer = new Observer(this, field);
            TypeResolver typeResolver = null;
            var type = observer.getType();
            if (type.equals(int.class) || type.equals(Integer.class))
            {
                typeResolver = new IntResolver();
            }
            if (type.equals(byte.class) || type.equals(Byte.class)) {
                typeResolver = new ByteResolver();
            }
            if (type.equals(long.class) || type.equals(Long.class)) {
                typeResolver = new LongResolver();
            }
            if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                typeResolver = new BoolResolver();
            }
            if (type.equals(UUID.class)) {
                typeResolver = new UuidResolver();
            }
            if (type.equals(String.class)) {
                typeResolver = new StringResolver();
            }
            packetFields.add(new PacketFieldWrapper(observer, typeResolver));
        }
        return packetFields;
    }

    public boolean resolvePacket(ByteBuffer buffer) {
        if (packetSize != buffer.limit()) {
            FluentLogger.LOGGER.warning(getClass().getSimpleName(), "Invalid incoming packet size", buffer.limit(), "expected size", packetSize);
            return false;
        }
        int currentIndex = packetIdSize;
        Object result;
        try {
            for (var packetField : packetFields) {
                result = packetField.getResolver().resolve(currentIndex, buffer);
                currentIndex += packetField.getResolver().typeSize();
                packetField.getObserver().set(result);
            }
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Packet resolver error " + this.getClass().getSimpleName(), e);
            return false;
        }
        return true;
    }

    public boolean resolveJson(JsonObject content)
    {

        try {

            for (var packetField : packetFields)
            {
                var fieldName = packetField.getObserver().getFieldName();
                var type = packetField.getObserver().getField().getType();
                if(!content.has(fieldName))
                {
                    continue;
                }

                Object value = null;
                var jsonField = content.get(fieldName);
                try {
                    if (type.equals(int.class) || type.equals(Integer.class))
                    {
                        value = jsonField.getAsInt();
                    }
                    if (type.equals(byte.class) || type.equals(Byte.class)) {
                        value = jsonField.getAsByte();
                    }
                    if (type.equals(long.class) || type.equals(Long.class)) {
                        value = jsonField.getAsLong();
                    }
                    if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        value = jsonField.getAsBoolean();
                    }
                    if (type.equals(String.class)) {
                        value = jsonField.getAsString();
                    }
                }
                catch (Exception e)
                {
                    continue;
                }
                packetField.getObserver().set(value);
            }
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Packet resolver error " + this.getClass().getSimpleName(), e);
            return false;
        }
        return true;
    }





    private int getPacketSize() {
        int size = 0;
        for (var packetField : packetFields) {
            size += packetField.getResolver().typeSize();
        }
        return packetIdSize + size;
    }

    protected void sendJson(WebSocket webSocket, Object obj) {
        webSocket.send(gson.toJson(obj));
    }


}
