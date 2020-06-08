package Model.Type;

import Model.Value.IValue;

public interface IType {
    IValue defaultValue();
    Object clone() throws CloneNotSupportedException;
}
