package io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluentBlockStates {
    private List<FluentBlockState> states = new ArrayList<>();

    @Setter
    @Getter
    private FluentBlockState defaultState;




    public void addState(FluentBlockState state) {
        states.add(state);
    }

    public Optional<FluentBlockState> findByName(String name) {
        return states.stream().filter(e -> e.getName().equals(name)).findFirst();
    }

    public Optional<FluentBlockState> findByIndex(int index) {
        return states.stream().filter(e -> e.getIndex() == index).findFirst();
    }


}
