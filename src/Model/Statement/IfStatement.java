package Model.Statement;

import Model.ADT.Dictionary;
import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IStack<IStatement> executionStack = state.getExecutionStack();

        IValue condition = expression.evaluate(symbolTable, state.getHeap());

        if (!condition.getType().equals(new BoolType()))
            throw new MyException("Condition expression is not a boolean");

        if (((BoolValue) condition).getValue())
            executionStack.push(this.thenStatement);
        else
            executionStack.push(this.elseStatement);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        if (!this.expression.typeCheck(typeEnv).equals(new BoolType()))
            throw new MyException("The condition of IF has not the type bool");
        IDictionary<String, IType> clone1TypeEnv = new Dictionary<String, IType>();
        IDictionary<String, IType> clone2TypeEnv = new Dictionary<String, IType>();

        typeEnv.getContent().keySet().forEach(key -> {
            try {
                clone1TypeEnv.update(key, (IType) typeEnv.getValue(key).clone());
                clone2TypeEnv.update(key, (IType) typeEnv.getValue(key).clone());
            } catch (MyException | CloneNotSupportedException ignore) {}
        });
        this.thenStatement.typeCheck(clone1TypeEnv);
        this.elseStatement.typeCheck(clone2TypeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "IF(" + this.expression.toString() + ")THEN(" + this.thenStatement.toString() +
                ") ELSE (" + this.elseStatement.toString() + ")";
    }
}
