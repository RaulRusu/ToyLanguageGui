package Repository;

import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import CustomException.MyException;

public class Repository implements IRepository {
    private ArrayList<ProgramState> programs;
    private String logFilePath;

    public Repository() {
        this.programs = new ArrayList<ProgramState>();
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    @Override
    public void clearLogFile() throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath)));
            logFile.print("");
            logFile.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getProgramStateById(int id) {
        return this.programs.stream().filter(programStateId -> programStateId.getID() == id).findFirst().get();
    }

    @Override
    public void addProgram(ProgramState programState) {
        this.programs.add(programState);
    }

    @Override
    public void logProgramStateFile(ProgramState programState) throws MyException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        logFile.println(programState.toString());
        logFile.close();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programs;
    }

    @Override
    public void setProgramList(List<ProgramState> programList) {
        this.programs = (ArrayList<ProgramState>) programList;
    }
}
