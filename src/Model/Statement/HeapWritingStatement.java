package Model.Statement;

import CustomException.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

import java.sql.Ref;

public class HeapWritingStatement implements IStatement {
    private String variableId;
    private IExpression expression;

    public HeapWritingStatement(String variableId, IExpression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();

        if (!symbolTable.isDefined(this.variableId))
            throw new MyException("Variable id:" + this.variableId + " is not defined");

        IValue value = symbolTable.getValue(this.variableId);
        if (!(value instanceof ReferenceValue))
            throw new MyException("Variable id:" + this.variableId + " is not Reference Type");

        ReferenceValue referenceValue = (ReferenceValue)value;
        int address = referenceValue.getAddress();

        if (!heap.isDefined(address))
            throw new MyException("Address: " + address + " is not a valid");

        IValue newValue = expression.evaluate(symbolTable, heap);

        if(!referenceValue.getType().equals(new ReferenceType(newValue.getType())))
            throw new MyException("Inner type of variable id:" + this.variableId + " is not " + newValue.getType().toString());

        heap.update(address, newValue);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType variableType = typeEnv.getValue(this.variableId);
        IType expressionType = this.expression.typeCheck(typeEnv);
        if(!variableType.equals(new ReferenceType(expressionType)))
            throw new MyException("Heap Writing stmt: right hand side and left hand side have different types ");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(" + this.variableId + "," + this.expression.toString() + ")";
    }
}
