package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IntValue;
import Model.Value.IValue;
import CustomException.MyException;


public class ArithmeticExpression implements IExpression {
    private IExpression expression1, expression2;
    private char operator;

    public ArithmeticExpression(char operator, IExpression expression1, IExpression expression2) {
        this.operator = operator;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException {
        IValue value1 = this.expression1.evaluate(symbolTable, heap);
        if (!value1.getType().equals(new IntType()))
            throw new MyException("first operand is not an integer");
        IValue value2 = this.expression2.evaluate(symbolTable, heap);
        if(!value2.getType().equals(new IntType()))
            throw new MyException("second operand is not an integer");

        int integerValue1 = ((IntValue)value1).getValue();
        int integerValue2 = ((IntValue)value2).getValue();

        if(this.operator == '+') return new IntValue(integerValue1 + integerValue2);
        if(this.operator == '-') return new IntValue(integerValue1 - integerValue2);
        if(this.operator == '*') return new IntValue(integerValue1 * integerValue2);
        if(this.operator == '/') {
            if (integerValue2 == 0)
                throw new MyException("division by zero");
            return new IntValue(integerValue1 / integerValue2);
        }

        throw new MyException("Operator: " + this.operator + " is not recognized as a valid arithmetic operator");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = this.expression1.typeCheck(typeEnv);
        type2 = this.expression2.typeCheck(typeEnv);
        if (!type1.equals(new IntType()))
            throw new MyException("first operand is not an integer");
        if (!type2.equals(new IntType()))
            throw new MyException("second operand is not an integer");
        return new IntType();
    }

    @Override
    public String toString() {
        return this.expression1.toString() + this.operator + this.expression2.toString();
    }
}
