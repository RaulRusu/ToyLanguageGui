package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Type.IType;
import Model.Value.IValue;
import CustomException.MyException;

public interface IExpression
{
    IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<IValue> heap) throws MyException;
    IType typeCheck(IDictionary<String, IType> typeEnv) throws MyException;
}
