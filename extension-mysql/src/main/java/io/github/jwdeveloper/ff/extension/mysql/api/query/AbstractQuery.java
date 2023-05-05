package io.github.jwdeveloper.ff.extension.mysql.api.query;

import java.util.List;
import java.util.Optional;

public interface AbstractQuery<T>
{
     List<T> toList();

     Optional<T> toFirst();

     String toRawQuery();
}
