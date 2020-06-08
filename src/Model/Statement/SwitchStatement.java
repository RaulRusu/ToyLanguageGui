package Model.Statement;

import CustomException.MyException;
import Model.ADT.Dictionary;
import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Expression.ArithmeticExpression;
import Model.Expression.IExpression;
import Model.Expression.RelationalExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.IValue;

import java.util.ArrayList;

public class SwitchStatement implements IStatement {
    private IExpression expression;
    private IExpression expression1;
    private IExpression expression2;
    private IStatement statement1;
    private IStatement statement2;
    private IStatement statementDefault;

    public SwitchStatement(IExpression expression, IExpression expression1, IExpression expression2,
                           IStatement statement1, IStatement statement2, IStatement statementDefault) {
        this.expression = expression;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statementDefault = statementDefault;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IStack<IStatement> executionStack = state.getExecutionStack();

        IStatement newStatement = new IfStatement(new RelationalExpression("==",this.expression, this.expression1), this.statement1,
                new IfStatement(new RelationalExpression("==",this.expression, this.expression2), this.statement2, this.statementDefault));

        executionStack.push(newStatement);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType expressionType = this.expression.typeCheck(typeEnv);
        IType expression1Type = this.expression1.typeCheck(typeEnv);
        IType expression2Type = this.expression2.typeCheck(typeEnv);

        if (!expressionType.equals(expression1Type))
            throw new MyException("The type of Switch and cases1 don't match");

        if (!expressionType.equals(expression2Type))
            throw new MyException("The type of Switch and cases1 don't match");

        IDictionary<String, IType> clone1TypeEnv = new Dictionary<String, IType>();
        IDictionary<String, IType> clone2TypeEnv = new Dictionary<String, IType>();
        IDictionary<String, IType> clone3TypeEnv = new Dictionary<String, IType>();

        typeEnv.getContent().keySet().forEach(key -> {
            try {
                clone1TypeEnv.update(key, (IType) typeEnv.getValue(key).clone());
                clone2TypeEnv.update(key, (IType) typeEnv.getValue(key).clone());
                clone3TypeEnv.update(key, (IType) typeEnv.getValue(key).clone());
            } catch (MyException | CloneNotSupportedException ignore) {}
        });
        this.statement1.typeCheck(clone1TypeEnv);
        this.statement2.typeCheck(clone2TypeEnv);
        this.statementDefault.typeCheck(clone2TypeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch(" + this.expression + ")" + "(case " + this.expression1 + ": " + statement1 + ")"
                + "(case " + this.expression2 + ": " + statement2 + ")"
                + "(default: " + statementDefault + ")";
    }
}
