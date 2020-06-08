package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import CustomException.MyException;

public class LogicalExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private String operator;

    public LogicalExpression(String operator, IExpression expression1, IExpression expression2) {
        this.operator = operator;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException {
        IValue value1 = expression1.evaluate(symbolTable, heap);
        if (!value1.getType().equals(new BoolType()))
            throw new MyException("first operand is not a boolean");

        IValue value2 = expression2.evaluate(symbolTable, heap);
        if(!value2.getType().equals(new BoolType()))
            throw new MyException("second operand is not a boolean");

        boolean boolValue1 = ((BoolValue)value1).getValue();
        boolean boolValue2 = ((BoolValue)value2).getValue();

        if (operator.equals("and")) return new BoolValue(boolValue1 && boolValue2);
        if (operator.equals("or")) return new BoolValue(boolValue1 || boolValue2);

        throw new MyException("Operator: " + this.operator + " is not recognized as a valid logical operator");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = this.expression1.typeCheck(typeEnv);
        type2 = this.expression2.typeCheck(typeEnv);
        if (!type1.equals(new BoolType()))
            throw new MyException("first operand is not an boolean");
        if (!type2.equals(new BoolType()))
            throw new MyException("second operand is not an boolean");
        return new BoolType();
    }

    @Override
    public String toString() {
        return this.expression1.toString() + this.operator + this.expression2.toString();
    }
}
