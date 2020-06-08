package Model.Statement;

import CustomException.MyException;
import Model.ADT.*;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;


public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ProgramState newProgramState;
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IDictionary<String, IValue> newSymbolTable = new Dictionary<String, IValue>();

        symbolTable.getContent().keySet().forEach(key -> {
            try {
                newSymbolTable.update(key, (IValue) symbolTable.getValue(key).clone());
            } catch (MyException ignore) {}
        });

        System.err.println(this.statement);

        newProgramState = new ProgramState(new MyStack<IStatement>(), newSymbolTable, state.getOutList(),
                state.getFileTable(), state.getHeap(), state.getSemaphoreTable(), this.statement);

        return newProgramState;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IDictionary<String, IType> cloneTypeEnv = new Dictionary<String, IType>();

        typeEnv.getContent().keySet().forEach(key -> {
            try {
                cloneTypeEnv.update(key, (IType) typeEnv.getValue(key).clone());
            } catch (MyException | CloneNotSupportedException ignore) {}
        });
        this.statement.typeCheck(cloneTypeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
