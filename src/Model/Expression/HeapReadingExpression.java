package Model.Expression;

import CustomException.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class HeapReadingExpression implements IExpression{
    private IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException {
        IValue value = this.expression.evaluate(symbolTable, heap);

        if (!(value instanceof ReferenceValue))
            throw new MyException("Expression is not Reference Type");

        ReferenceValue referenceValue = (ReferenceValue)value;
        int address = referenceValue.getAddress();

        if(!heap.isDefined(address))
            throw new MyException("Address: " + address + " is not a valid");

        return heap.getValue(address);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type = this.expression.typeCheck(typeEnv);
        if (!(type instanceof ReferenceType))
            throw new MyException("the heap reading argument is not ref type");
        return ((ReferenceType)type).getInnerType();
    }

    @Override
    public String toString() {
        return "rH(" + this.expression.toString() + ")";
    }
}
