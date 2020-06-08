package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import CustomException.MyException;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements IStatement {
    private IExpression expression;
    public CloseReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IFileTable<StringValue, BufferedReader> fileTable= state.getFileTable();
        IValue fileName = this.expression.evaluate(symbolTable, state.getHeap());

        if (!fileName.getType().equals(new StringType()))
            throw new MyException("File name is not a string");

        StringValue stringValueFileName = ((StringValue) fileName);

        if(!fileTable.isDefined(stringValueFileName))
            throw new MyException("File name: " + stringValueFileName.getValue() + " is not open");

        BufferedReader fileDescriptor = fileTable.getValue(stringValueFileName);

        try {
            fileDescriptor.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        fileTable.remove(stringValueFileName);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        if(!this.expression.typeCheck(typeEnv).equals(new StringType()))
            throw new MyException("Close stmt: right hand side and left hand side have different types ");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.expression.toString() + ")";
    }
}
