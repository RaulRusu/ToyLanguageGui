package Controller;

import Model.ProgramState;
import CustomException.MyException;
import Repository.IRepository;

import java.util.List;

public interface IController {
    void allStep() throws MyException ;
    void displayProgramState(ProgramState programState);
    IRepository getRepository();
    void oneStep() throws MyException;
}
