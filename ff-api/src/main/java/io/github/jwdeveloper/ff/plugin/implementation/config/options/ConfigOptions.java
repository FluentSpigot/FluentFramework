package io.github.jwdeveloper.ff.plugin.implementation.config.options;

public interface ConfigOptions<T>
{
    void save();

    T get();

    static Class<?> getClassFor(Class<?> configMember)
    {
        try {
            var member = configMember.newInstance();
            return new ConfigOptionsImpl(null, member,null).getClass();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to intialize class for Config Options");
        }

    }
}
