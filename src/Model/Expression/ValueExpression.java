package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.IType;
import Model.Value.IValue;
import CustomException.MyException;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException {
        return this.value;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return this.value.getType();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
