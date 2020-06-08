package Controller;

import Model.ADT.IStack;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Repository.IRepository;
import CustomException.MyException;
import com.sun.jdi.Value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller implements IController {
    private boolean displayFlag;
    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = true;
        this.repository.setLogFilePath("log.txt");
        try {
            this.repository.clearLogFile();
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private Map<Integer, IValue> garbageCollector(List<Integer> symbolTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(value -> symbolTableAddr.contains(value.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddr(Collection<IValue> symbolTableValues, Collection<IValue> heap) {
        return Stream.concat(
                symbolTableValues.stream()
                        .filter(value -> value instanceof ReferenceValue)
                        .map(value -> ((ReferenceValue) value).getAddress())
                ,
                heap.stream()
                        .filter(value -> value instanceof ReferenceValue)
                        .map(value -> ((ReferenceValue) value).getAddress())
        ).collect(Collectors.toList());
    }

    private Collection<IValue> getValuesFormSymbolTables(List<ProgramState> programStateList) {
        return programStateList.stream()
                .map(programState -> programState.getSymbolTable().getContent().values())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }



    private List<ProgramState> removeCompletedPrograms(List<ProgramState> programStatesList) {
        return programStatesList.stream()
                .filter(programState -> !programState.isCompleted())
                .collect(Collectors.toList());
    }

    private void printAllProgramStatesToFile(List<ProgramState> programStatesList) {
        programStatesList.forEach(programState -> {
            try {
                this.repository.logProgramStateFile(programState);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private List<Callable<ProgramState>> createCallable(List<ProgramState> programStatesList) {
        return  programStatesList.stream()
                .map(programState -> (Callable<ProgramState>) (() -> {
                    return programState.oneStep();
                }))
                .collect(Collectors.toList());
    }

    private List<ProgramState> executeProgramsAsync(List<Callable<ProgramState>> callableList) throws InterruptedException {
        return this.executor.invokeAll(callableList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(programState -> programState != null)
                .collect(Collectors.toList());
    }

    void oneStepForAllPrograms(List<ProgramState> programStatesList) {
        this.printAllProgramStatesToFile(programStatesList);
        this.displayProgramState(programStatesList.get(0));
        List<Callable<ProgramState>> callableList = this.createCallable(programStatesList);

        try {
            List<ProgramState> newProgramStateList = this.executeProgramsAsync(callableList);
            programStatesList.addAll(newProgramStateList);
            this.printAllProgramStatesToFile(programStatesList);
            this.displayProgramState(programStatesList.get(0));
            this.repository.setProgramList(programStatesList);
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void allStep() throws MyException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Log file location: ");
        //String fileLocation = scanner.nextLine();
        this.repository.setLogFilePath("log.txt");
        this.repository.clearLogFile();

        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStateList = this.removeCompletedPrograms(this.repository.getProgramList());

        while (programStateList.size() > 0) {
            Map<Integer, IValue> newHeap = this.garbageCollector(this.getAddr(this.getValuesFormSymbolTables(programStateList), programStateList.get(0).getHeap().getContent().values()), programStateList.get(0).getHeap().getContent());
            programStateList.forEach(programState -> programState.getHeap().setContent(newHeap));
            this.oneStepForAllPrograms(programStateList);
            programStateList = removeCompletedPrograms(this.repository.getProgramList());
        }

        this.executor.shutdownNow();
        this.repository.setProgramList(programStateList);
    }

    @Override
    public void oneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = this.removeCompletedPrograms(this.repository.getProgramList());
        if (programStateList.size() > 0) {
            Map<Integer, IValue> newHeap = this.garbageCollector(this.getAddr(this.getValuesFormSymbolTables(programStateList), programStateList.get(0).getHeap().getContent().values()), programStateList.get(0).getHeap().getContent());
            programStateList.forEach(programState -> programState.getHeap().setContent(newHeap));
            this.oneStepForAllPrograms(programStateList);
        }
        executor.shutdownNow();
        this.repository.setProgramList(programStateList);
    }

    @Override
    public void displayProgramState(ProgramState programState) {
        //System.out.println(programState);
        //System.out.println("---------------------------------------------------------------------------------------");
    }

    @Override
    public IRepository getRepository() {
        return this.repository;
    }


}
