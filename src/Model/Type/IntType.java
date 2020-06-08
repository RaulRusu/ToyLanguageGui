package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType {
    public IntType() { }

    @Override
    public boolean equals(Object obj){
        return obj instanceof IntType;
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new IntType();
    }
}
