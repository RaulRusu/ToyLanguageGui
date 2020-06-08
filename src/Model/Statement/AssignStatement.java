package Model.Statement;

import Model.ADT.IDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStatement implements IStatement{
    private String variableId;
    private IExpression expression;

    public AssignStatement(String id, IExpression expression) {
        this.variableId = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (!symbolTable.isDefined(variableId))
            throw new MyException("Variable id:" + this.variableId + " is not defined");

        IType variableType = symbolTable.getValue(this.variableId).getType();
        IValue newValue = this.expression.evaluate(symbolTable, state.getHeap());


        if(!variableType.equals(newValue.getType()))
            throw new MyException("declared type of variable "+ this.variableId +" and type of the assigned expression do not match");

        symbolTable.update(this.variableId, newValue);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType variableType = typeEnv.getValue(this.variableId);
        IType expressionType = this.expression.typeCheck(typeEnv);
        if (!variableType.equals(expressionType))
            throw new MyException("Assignment: right hand side and left hand side have different types");
        return typeEnv;
    }

    @Override
    public String toString() {
        return this.variableId + "=" + this.expression.toString();
    }
}
