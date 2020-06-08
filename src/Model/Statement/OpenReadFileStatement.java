package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.Expression.IExpression;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements IStatement{
    private IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
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

        if(fileTable.isDefined(stringValueFileName))
            throw new MyException("File name: " + stringValueFileName.getValue() + " is already open");

        BufferedReader fileDescriptor = null;
        try{
            fileDescriptor = new BufferedReader(new FileReader(stringValueFileName.getValue()));
        } catch (FileNotFoundException e) {
            throw new MyException(e.getMessage());
        }

        fileTable.add(stringValueFileName, fileDescriptor);

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
        return "openRFile(" + this.expression.toString() + ")";
    }
}
