package io.github.jwdeveloper.ff.extension.mysql.api.query;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface AbstractQuery<T>
{
     List<T> toList();

     void toListAsync(Consumer<List<T>> onDone);

     Optional<T> toFirst();

     String toRawQuery();
}
