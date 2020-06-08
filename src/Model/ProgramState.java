package Model;
import CustomException.MyException;
import Model.ADT.*;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, IValue> symbolTable;
    private IList<IValue> outList;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IStatement originalProgram;
    private IHeap<IValue> heap;
    private ISemaphoreTable semaphoreTable;
    private int id;
    private static int currentID = -1;

    private static synchronized int nextAvailableID() {
        currentID++;
        return currentID;
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolTable, IList<IValue> outList,
                        IFileTable<StringValue, BufferedReader> fileTable, IHeap<IValue> heap, ISemaphoreTable semaphoreTable, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outList = outList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphoreTable = semaphoreTable;
        this.originalProgram = originalProgram;
        this.executionStack.push(originalProgram);
        this.id = nextAvailableID();
    }

    public IStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public IDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable;
    }

    public IList<IValue> getOutList() {
        return this.outList;
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IHeap<IValue> getHeap() {
        return this.heap;
    }

    public ISemaphoreTable getSemaphoreTable() { return this.semaphoreTable; }

    public boolean isCompleted() {
        return this.executionStack.isEmpty();
    }

    public int getID() {
        return this.id;
    }

    public ProgramState oneStep() throws MyException {
        IStack<IStatement> executionStack = this.getExecutionStack();
        if (executionStack.isEmpty())
            throw new MyException("Execution stack is empty");
        IStatement statement = executionStack.pop();
        return statement.execute(this);
    }

    @Override
    public String toString() {
        return  "ID=" + this.id + "\n" +
                "Execution Stack:" + "\n" + this.executionStack.toString() + "\n" +
                "Symbol Table:" + "\n" + this.symbolTable.toString() + "\n" +
                "Out List:" + "\n" + this.outList.toString() + "\n" +
                "File Table: " + "\n" + this.fileTable.toString() + "\n" +
                "Heap:" + "\n" + this.heap.toString() + "\n" +
                "SemaphoreTable:" + "\n" + this.semaphoreTable.toString();
    }
}
