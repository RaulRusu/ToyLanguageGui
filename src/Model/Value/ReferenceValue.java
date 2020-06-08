package Model.Value;

import Model.Type.IType;
import Model.Type.ReferenceType;

public class ReferenceValue implements IValue {
    private int address;
    private IType locationType;

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReferenceValue;
    }

    @Override
    public String toString() {
        return "(" + this.address + "," + this.locationType.toString() + ")";
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e) {
            clone = new ReferenceValue(this.address, this.locationType);
        }
        return clone;
    }
}
