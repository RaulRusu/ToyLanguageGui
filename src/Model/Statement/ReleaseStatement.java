package Model.Statement;

import CustomException.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.ISemaphoreTable;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.List;

public class ReleaseStatement implements IStatement {
    private String variableID;

    public ReleaseStatement(String variableID) {
        this.variableID = variableID;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();
        ISemaphoreTable semaphoreTable = state.getSemaphoreTable();

        if(!symbolTable.isDefined(variableID))
            throw new MyException("error");

        IValue index = symbolTable.getValue(this.variableID);

        if(!index.getType().equals(new IntType()))
            throw new MyException("error");

        int indexValue = ((IntValue) index).getValue();

        if(!semaphoreTable.isDefined(indexValue))
            throw new MyException("error");

        Pair<Integer, List<Integer>> pair = semaphoreTable.getValue(indexValue);

        if(pair.getSecond().contains(state.getID()))
            pair.getSecond().remove(Integer.valueOf(state.getID()));

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "release(" + this.variableID + ")";
    }
}
