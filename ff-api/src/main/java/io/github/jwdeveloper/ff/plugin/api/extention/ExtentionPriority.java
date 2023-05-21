package io.github.jwdeveloper.ff.plugin.api.extention;

public enum ExtentionPriority
{
    LOW(1), MEDIUM(2), HIGH(3);

    private final int level;

    private ExtentionPriority(int level)
    {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
