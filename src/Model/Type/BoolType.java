package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType {
    public BoolType() { }

    @Override
    public boolean equals(Object obj){
        return obj instanceof BoolType;
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new BoolType();
    }
}
