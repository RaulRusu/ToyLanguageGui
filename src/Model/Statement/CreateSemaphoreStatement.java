package Model.Statement;

import CustomException.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class CreateSemaphoreStatement implements IStatement {
    private String variableID;
    private IExpression expression;

    public CreateSemaphoreStatement(String variableID, IExpression expression) {
        this.variableID = variableID;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();

        IValue number = this.expression.evaluate(symbolTable, heap);
        if(!number.getType().equals(new IntType()))
            throw new MyException("error");

        int nextFree = state.getSemaphoreTable().add(((IntValue)number).getValue());

        if(!symbolTable.isDefined(this.variableID))
            throw new MyException("error");

        symbolTable.update(this.variableID, new IntValue(nextFree));

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {

        return typeEnv;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + this.variableID + "," + this.expression + ")";
    }
}
