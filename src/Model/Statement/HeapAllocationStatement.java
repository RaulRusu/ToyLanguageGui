package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class HeapAllocationStatement implements IStatement {
    private String variableId;
    private IExpression expression;

    public HeapAllocationStatement(String variableId, IExpression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();

        if (!symbolTable.isDefined(this.variableId))
            throw new MyException("Variable id:" + this.variableId + " is not defined");

        IValue referenceValue = symbolTable.getValue(this.variableId);
        if (!(referenceValue instanceof ReferenceValue))
            throw new MyException("Variable id:" + this.variableId + " is not Reference Type");

        IValue newValue = this.expression.evaluate(symbolTable, heap);
        if(!referenceValue.getType().equals(new ReferenceType(newValue.getType())))
            throw new MyException("Inner type of variable id:" + this.variableId + " is not " + newValue.getType().toString());

        int nextFreeAddress = heap.getNextFree();

        heap.add(newValue);
        symbolTable.update(this.variableId, new ReferenceValue(nextFreeAddress, newValue.getType()));

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType variableType = typeEnv.getValue(this.variableId);
        IType expressionType = this.expression.typeCheck(typeEnv);
        if(!variableType.equals(new ReferenceType(expressionType)))
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "new(" + this.variableId + "," + this.expression.toString() + ")";
    }
}
