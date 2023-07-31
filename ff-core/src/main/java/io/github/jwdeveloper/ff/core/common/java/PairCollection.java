package io.github.jwdeveloper.ff.core.common.java;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PairCollection<FIRST, SECOND>
{
    @Getter
    private List<FIRST> firsts = new ArrayList<>();

    @Getter
    private List<SECOND> seconds = new ArrayList<>();

    public void addFirst(FIRST first)
    {
        firsts.add(first);
    }

    public void addSecond(SECOND second)
    {
        seconds.add(second);
    }


}
