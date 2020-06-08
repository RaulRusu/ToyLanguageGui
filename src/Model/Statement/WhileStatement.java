package Model.Statement;

import CustomException.MyException;
import Model.ADT.Dictionary;
import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

import java.time.chrono.IsoChronology;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue value = expression.evaluate(state.getSymbolTable(), state.getHeap());

        if (!value.getType().equals(new BoolType()))
            throw new MyException("Condition expression is not a boolean");

        BoolValue boolValue = (BoolValue)value;

        IStack<IStatement> stack = state.getExecutionStack();

        if (boolValue.getValue()) {
            stack.push(new WhileStatement(this.expression, this.statement));
            stack.push(statement);
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        if(!this.expression.typeCheck(typeEnv).equals(new BoolType()))
            throw new MyException("The condition of while has not the type bool");

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
        return "(while(" + this.expression.toString() + ")" + this.statement.toString() + ")";
    }
}
