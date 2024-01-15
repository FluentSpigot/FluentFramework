package io.github.jwdeveloper.ff.extension.protocol.impl;

import io.github.jwdeveloper.ff.extension.protocol.impl.exceptions.FieldExceptionFormatter;
import io.github.jwdeveloper.ff.extension.protocol.impl.exceptions.MethodExceptionFormatter;
import io.github.jwdeveloper.ff.extension.protocol.impl.packets.PacketOperator;
import io.github.jwdeveloper.ff.extension.protocol.impl.packets.PacketSenderManager;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.reflect.api.exceptions.FieldValidationException;
import io.github.jwdeveloper.reflect.api.exceptions.MethodValidationException;
import io.github.jwdeveloper.reflect.api.exceptions.ValidationException;
import io.github.jwdeveloper.reflect.implementation.FluentReflect;
import org.bukkit.Bukkit;

public class FluentProtocolExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {


        try {
            var version = builder.plugin().getServer().getVersion();
            var reflect = new FluentReflect(version);
            var factory = new FluentProtocolNms(reflect, version);

            var manager = new PacketSenderManager(factory);
            var operator = new PacketOperator(manager, factory, FluentProtocolExtension.class.getSimpleName());


            builder.container().registerSigleton(FluentProtocolImpl.class, container ->
            {
                return new FluentProtocolImpl(factory, reflect);
            });

        } catch (ValidationException e) {
            if (e instanceof MethodValidationException ex) {
                new MethodExceptionFormatter(ex).show();
            }
            if (e instanceof FieldValidationException ex) {
                new FieldExceptionFormatter(ex).show();
            }

            throw new RuntimeException(e);
        } catch (Exception e) {
            throw e;
        }


    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {


    }

    public String getServerVersion() {
        var ver2 = Bukkit.getServer().getBukkitVersion();
        ver2 = ver2.replace("-SNAPSHOT", "");
        System.out.println(" Ver2 " + ver2);
        return "v1_17_R1";
    }
}
