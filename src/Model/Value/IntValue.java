package Model.Value;

import Model.Type.IntType;
import Model.Type.IType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntValue))
            return false;
        IntValue intValue = (IntValue)obj;
        return this.value == intValue.getValue();
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e) {
            clone = new IntValue(this.value);
        }

        return clone;
    }
}
