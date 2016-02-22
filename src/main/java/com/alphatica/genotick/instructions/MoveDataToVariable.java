package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

import java.io.Serializable;

public class MoveDataToVariable extends DataVarInstruction  implements Serializable {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3017704625520415010L;

    private MoveDataToVariable(MoveDataToVariable i) {
        this.setDataTableIndex(i.getDataColumnIndex());
        this.setDataOffsetIndex(i.getDataOffsetIndex());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public MoveDataToVariable() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public MoveDataToVariable copy() {
        return new MoveDataToVariable(this);
    }

}
