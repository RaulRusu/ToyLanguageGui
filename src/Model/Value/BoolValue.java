package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoolValue))
            return false;
        BoolValue boolValue = (BoolValue)obj;
        return this.value == boolValue.getValue();
    }

    @Override
    public String toString() {
        return Boolean.toString(this.value);
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e) {
            clone = new BoolValue(this.value);
        }

        return clone;
    }

}
