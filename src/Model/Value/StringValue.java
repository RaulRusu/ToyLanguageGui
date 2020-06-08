package Model.Value;

import Model.Type.IType;
import Model.Type.StringType;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StringValue))
            return false;

        StringValue stringValue = (StringValue)obj;
        return this.value.equals(stringValue.getValue());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e) {
            clone = new StringValue(this.value);
        }

        return clone;
    }
}
