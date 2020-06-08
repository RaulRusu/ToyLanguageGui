package Model.Statement;
import CustomException.MyException;
import Model.ADT.IDictionary;
import Model.ProgramState;
import Model.Type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IDictionary<String, IType> typeCheck(IDictionary<String,IType> typeEnv) throws MyException;
}
