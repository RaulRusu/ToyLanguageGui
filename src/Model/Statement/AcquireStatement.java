package Model.Statement;

import CustomException.MyException;
import Model.ADT.*;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.List;
import java.util.ListIterator;

public class AcquireStatement implements IStatement {
    private String variableID;

    public AcquireStatement(String variableID) {
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

        int NL = pair.getSecond().size();

        if(pair.getFirst() > NL) {
            if (pair.getSecond().contains(state.getID()))
                ;
            else
                pair.getSecond().add(state.getID());
        }
        else
            state.getExecutionStack().push(new AcquireStatement(this.variableID));
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "acquire(" + this.variableID + ")";
    }
}
