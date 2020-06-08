package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import CustomException.MyException;
import Model.Value.IntValue;

import java.lang.reflect.Type;

public class RelationalExpression implements IExpression {
    IExpression expression1;
    IExpression expression2;
    String operator;

    public RelationalExpression(String operator, IExpression expression1, IExpression expression2) {
        this.operator = operator;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    private BoolValue compareIntValues(String operator, IntValue intValue1, IntValue intValue2) throws MyException{
        int integer1 = intValue1.getValue();
        int integer2 = intValue2.getValue();

        switch (operator) {
            case "<":
                return new BoolValue(integer1 < integer2);
            case "<=":
                return new BoolValue(integer1 <= integer2);
            case "==":
                return new BoolValue(integer1 == integer2);
            case "!=":
                return new BoolValue(integer1 != integer2);
            case ">":
                return new BoolValue(integer1 > integer2);
            case ">=":
                return new BoolValue(integer1 >= integer2);
            default:
                break;
        }

        throw new MyException("Operator: " + this.operator + " is not recognized as a valid comparison operator");
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException {
        IValue value1 = expression1.evaluate(symbolTable, heap);
        if (!value1.getType().equals(new IntType()))
            throw new MyException("first operand is not an int");

        IValue value2 = expression2.evaluate(symbolTable, heap);
        if (!value2.getType().equals(new IntType()))
            throw new MyException("second operand is not an int");

        return this.compareIntValues(this.operator, (IntValue)value1, (IntValue)value2);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = this.expression1.typeCheck(typeEnv);
        type2 = this.expression2.typeCheck(typeEnv);
        if (!type1.equals(new BoolType()))
            throw new MyException("first operand is not an integer");
        if (!type2.equals(new BoolType()))
            throw new MyException("second operand is not an integer");
        return new BoolType();
    }

    @Override
    public String toString() {
        return this.expression1.toString() + this.operator + this.expression2.toString();
    }
}
