package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.IType;
import Model.Value.IValue;
import CustomException.MyException;

public class VariableExpression implements IExpression {
    private String variableId;

    public VariableExpression(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException {
        if (!symbolTable.isDefined(this.variableId))
            throw new MyException("Variable id:" + this.variableId + " is not defined");
        return symbolTable.getValue(this.variableId);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv.getValue(this.variableId);
    }

    @Override
    public String toString() {
        return this.variableId;
    }
}
