package io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository;

import io.github.jwdeveloper.ff.extension.files.api.fluent_files.Saveable;

import java.util.List;

public interface SaveableRepository<KEY, CONTENT> extends Repository<CONTENT, KEY>, Saveable<List<CONTENT>> {
}
