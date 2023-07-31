package io.github.jwdeveloper.ff.extension.files.api.repository;

import io.github.jwdeveloper.ff.extension.files.api.Saveable;

public interface SaveableRepository<KEY, CONTENT> extends Repository<CONTENT, KEY>, Saveable<CONTENT> {
}
