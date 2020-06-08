package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;

public class CompoundStatement implements IStatement {
    private IStatement firstStatement;
    private IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(this.secondStatement);
        executionStack.push(this.firstStatement);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + this.firstStatement.toString() + ";" + this.secondStatement.toString() + ")";
    }
}
