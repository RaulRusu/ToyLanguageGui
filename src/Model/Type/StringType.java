package Model.Type;

import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType {
    public StringType() { }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new StringType();
    }
}
