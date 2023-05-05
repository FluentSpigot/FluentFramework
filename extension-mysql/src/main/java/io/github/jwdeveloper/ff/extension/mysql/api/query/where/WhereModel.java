package io.github.jwdeveloper.ff.extension.mysql.api.query.where;

import lombok.Data;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Data
public class WhereModel
{
    private Queue<WhereInstruction> instructions = new LinkedBlockingQueue<>();

    public void addInstruction(WhereInstruction instruction)
    {
        instructions.add(instruction);
    }
}
