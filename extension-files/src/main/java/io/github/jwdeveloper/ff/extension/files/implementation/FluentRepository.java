package io.github.jwdeveloper.ff.extension.files.implementation;

import io.github.jwdeveloper.ff.core.common.java.ObjectUtility;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.DataModel;
import io.github.jwdeveloper.ff.extension.files.api.repository.SaveableRepository;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.stream.Stream;

public class FluentRepository<T extends DataModel> implements SaveableRepository<UUID, T> {
    private final List<T> content;

    @Getter
    private String fileName;

    @Getter
    private final Class<T> entityClass;

    public FluentRepository(Class<T> entityClass)
    {
        this.content = new ArrayList<>();
        this.fileName = entityClass.getSimpleName();
        this.entityClass = entityClass;
    }

    public FluentRepository(Class<T> entityClass, String filename) {
        this(entityClass);
        this.fileName = filename;
    }

    @Override
    public List<T> getContent()
    {
        return content;
    }

    public Stream<T> query() {
        return content.stream();
    }

    @Override
    public Optional<T> findById(UUID id) {
        return content
                .stream()
                .filter(p -> p.getUuid().equals(id))
                .findFirst();
    }

    public Optional<T> getOne(UUID id) {
        return content
                .stream()
                .filter(p -> p.getUuid().equals(id))
                .findFirst();
    }

    public Optional<T> getOneByName(String name) {
        return content
                .stream()
                .filter(p -> ChatColor.stripColor(p.getName())
                        .equalsIgnoreCase(name))
                .findFirst();
    }

    public List<T> findAll() {
        return content;
    }

    @Override
    public boolean insert(T data) {
        return insertOne(data);
    }



    public boolean insertOne(T data) {
        if (data == null) {
            return false;
        }
        if (content.contains(data)) {
            return false;
        }
        if (data.getUuid() == null)
            data.setUuid(UUID.randomUUID());

        content.add(data);

        return true;

    }

    @Override
    public boolean update(UUID id, T data) {
        return updateOne(id, data);
    }

    public boolean updateOne(UUID id, T data) {
        var optional = getOne(id);
        if (optional.isEmpty())
            return false;

        try {
            ObjectUtility.copyToObjectDeep(data, optional.get());
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to update element in " + getClass().getSimpleName() + " repository", e);
            return false;
        }
    }

    public boolean insertMany(List<T> data) {
        data.forEach(this::insertOne);
        return true;
    }


    public boolean updateMany(HashMap<UUID, T> data) {
        data.forEach(this::updateOne);
        return true;
    }

    @Override
    public boolean deleteOne(T data) {
        if (content.contains(data)) {
            content.remove(data);
            return true;
        }
        return false;
    }

    public boolean deleteOneById(UUID uuid) {
        var data = getOne(uuid);
        if (data.isEmpty())
            return false;

        content.remove(data.get());
        return true;
    }

    @Override
    public boolean deleteMany(List<T> data) {
        data.forEach(a -> this.deleteOneById(a.getUuid()));
        return true;
    }

    public boolean deleteAll() {
        content.clear();
        return true;
    }

    public int getSize() {
        return content.size();
    }

    public boolean contains(T data) {
        return content.contains(data);
    }


  /*  public boolean load() {
        try {
            content = JsonUtility.loadList(path, fileName, entityClass);
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Repository load error " + fileName + " " + entityClass.getName(), e);
            return false;
        }
    }


    public boolean save() {
        try {
            JsonUtility.save(content, path, fileName);
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Repository save error " + fileName + " " + entityClass.getName(), e);
            return false;
        }
    }

    public static String getDefaultPath(JavaPlugin plugin) {
        var path = FileUtility.pluginPath(plugin) + FileUtility.separator() + "data";
        FileUtility.ensurePath(path);
        return path;
    }*/
}
