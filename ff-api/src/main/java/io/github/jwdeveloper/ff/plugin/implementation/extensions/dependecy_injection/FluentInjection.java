package io.github.jwdeveloper.ff.plugin.implementation.extensions.dependecy_injection;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface FluentInjection
{
    <T> T tryFindInjection(Class<T> injectionType);
    <T> T findInjection(Class<T> injectionType);

    <T> Collection<T> findAllByInterface(Class<T> _interface);

    <T> Collection<T> findAllBySuperClass(Class<T> superClass);

    Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation);
}
