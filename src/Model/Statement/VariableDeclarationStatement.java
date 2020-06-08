package Model.Statement;

import Model.ADT.IDictionary;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableDeclarationStatement implements IStatement {
    private String variableId;
    private IType variableType;

    public VariableDeclarationStatement(String id, IType type) {
        this.variableId = id;
        this.variableType = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(variableId))
            throw new MyException("Variable id:" + this.variableId + " is already declared");

        symbolTable.update(this.variableId, this.variableType.defaultValue());

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.update(this.variableId, this.variableType);
        return typeEnv;
    }

    @Override
    public String toString() {
        return this.variableType.toString() + " " + this.variableId;
    }
}
