package Repository;

import Model.ProgramState;
import CustomException.MyException;

import java.util.List;

public interface IRepository {
    void addProgram(ProgramState programState);
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programList);
    void clearLogFile() throws MyException;
    void logProgramStateFile(ProgramState programState) throws MyException;
    void setLogFilePath(String logFilePath);
    ProgramState getProgramStateById(int id);
}
