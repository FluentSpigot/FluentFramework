package io.github.jwdeveloper.ff.extension.files.api;

import java.util.List;

public interface Saveable<T>
{
     List<T> getContent();

     String getFileName();
}
