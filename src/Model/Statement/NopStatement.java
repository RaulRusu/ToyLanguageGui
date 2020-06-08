package Model.Statement;

import Model.ADT.IDictionary;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;

public class NopStatement implements IStatement {

    public NopStatement() { }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
