package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.Expression.IExpression;
import Model.ProgramState;
import CustomException.MyException;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String variableId;

    public ReadFileStatement(IExpression expression, String variableId) {
        this.expression = expression;
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IFileTable<StringValue, BufferedReader> fileTable= state.getFileTable();

        if (!symbolTable.isDefined(this.variableId))
            throw new MyException("Variable id:" + this.variableId + " is not defined");

        IType variableType = symbolTable.getValue(this.variableId).getType();
        if (!variableType.equals(new IntType()))
            throw new MyException("Variable id:" + this.variableId + " is not int Type");

        IValue fileName = this.expression.evaluate(symbolTable, state.getHeap());
        if (!fileName.getType().equals(new StringType()))
            throw new MyException("File name is not a string");

        StringValue stringValueFileName = (StringValue)fileName;
        if(!fileTable.isDefined(stringValueFileName))
            throw new MyException("File name: " + stringValueFileName.getValue() + " is not open");

        BufferedReader fileDescriptor = fileTable.getValue(stringValueFileName);

        String stringRead;
        try {
            stringRead = fileDescriptor.readLine();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        if (stringRead == null)
            stringRead = "";
        int intRead;
        try {
            System.out.println(stringRead);
            if (stringRead.equals(""))
                intRead = 0;
            else
                intRead = Integer.parseInt(stringRead);
        } catch (NumberFormatException e){
            throw new MyException(e.getMessage());
        }

        symbolTable.update(this.variableId, new IntValue(intRead));

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType expressionType, variableType;
        expressionType = this.expression.typeCheck(typeEnv);
        variableType = typeEnv.getValue(this.variableId);
        if(!variableType.equals(variableType))
            throw new MyException("Read stmt: right hand side and left hand side have different types ");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + "," + this.variableId + ")";
    }
}
