package resources.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

import java.util.List;


@Data
@YamlSection(path = "config")
public class TestConfig
{
    @YamlSection(name = "info")
    private List<ConfigListContent> listContents;

    @YamlSection(name = "session")
    private ConfigSubOptions sessionInfo;

    @YamlSection(name = "player-name")
    private String name;

    @YamlSection(name = "player-age")
    private int age;

    @YamlSection(name = "player-speed")
    private float speed;

    @YamlSection(name = "player-money")
    private double money;

    @YamlSection(name = "player-op")
    private boolean isPlayerOp;
}
