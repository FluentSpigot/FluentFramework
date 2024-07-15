package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.models.api.data.Bone;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class DisplayModelData
{
    private String name;

    private Map<String, Bone> bones;
}
