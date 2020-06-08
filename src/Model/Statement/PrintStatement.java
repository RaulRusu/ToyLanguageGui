package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IList;
import Model.Expression.IExpression;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;
import Model.Value.IValue;

public class PrintStatement implements IStatement {
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IList<IValue> outList = state.getOutList();
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();

        outList.pushBack(this.expression.evaluate(symbolTable, state.getHeap()));

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
