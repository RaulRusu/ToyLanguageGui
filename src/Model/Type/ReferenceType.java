package Model.Type;

import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class ReferenceType implements IType {
    private IType innerType;

    public ReferenceType(IType innerType) {
        this.innerType = innerType;
    }

    public IType getInnerType() {
        return innerType;
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, this.innerType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceType)
            return this.innerType.equals(((ReferenceType) obj).getInnerType());
        return false;
    }

    @Override
    public String toString() {
        return "Ref(" + this.innerType.toString() + ")";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ReferenceType((IType) this.innerType.clone());
    }
}
