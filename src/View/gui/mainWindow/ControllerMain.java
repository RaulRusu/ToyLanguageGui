package View.gui.mainWindow;

import Controller.Controller;
import Controller.IController;
import CustomException.MyException;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.gui.program.ProgramWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerMain implements Initializable {
    private List<IStatement> statementList;

    @FXML
    private Button executeButton;
    @FXML
    private ListView<String> statementListView;

    private IController createNewController(IStatement statement){
        IStack<IStatement> executionStack = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable = new Dictionary<String, IValue>();
        IList<IValue> outList = new MyList<IValue>();
        IFileTable<StringValue, BufferedReader> fileTable = new FileTable<StringValue, BufferedReader>();
        IHeap<IValue> heap = new Heap<IValue>();
        ISemaphoreTable semaphoreTable = new SemaphoreTable();
        IRepository repository = new Repository();
        ProgramState programState = new ProgramState(executionStack, symbolTable, outList, fileTable, heap, semaphoreTable, statement);
        repository.addProgram(programState);
        return new Controller(repository);
    }

    private void createStatements() {
        IStatement statement1 =  new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableExpression("v"))));

        IStatement statement2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement statement3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        IStatement statement4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))))))))));

        IStatement statement5 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(
                                        new RelationalExpression("==", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                        new AssignStatement("v",new ValueExpression(new
                                                IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        IStatement statement6 =  new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(3))),
                        new CompoundStatement(new VariableDeclarationStatement("n",new ReferenceType(new ReferenceType(new IntType()))),
                                new HeapAllocationStatement("n", new VariableExpression("v")))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement statement7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement statement8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement statement9 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement statement10 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement statement11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">",
                                new VariableExpression("v"), new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new AssignStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        //int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStatement statement12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));
        IStatement statement13 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new BoolValue(true))));
        IStatement statement14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new BoolValue(true))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));

        IStatement statement15 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                    new CompoundStatement(
                                                        new SwitchStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                                                                new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                                                new ValueExpression(new IntValue(10)),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")),new PrintStatement(new VariableExpression("b"))),
                                                                new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                                ),
                                                            new PrintStatement(new ValueExpression(new IntValue(300)))
                                                    )))))));

        IStatement statement16 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1))),
                                new CompoundStatement(new CreateSemaphoreStatement("cnt", new HeapReadingExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"),
                                                    new CompoundStatement(new HeapWritingStatement("v1", new ArithmeticExpression('*', new HeapReadingExpression(new VariableExpression("v1")),  new ValueExpression(new IntValue(10))))
                                                            , new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))), new ReleaseStatement("cnt"))))),
                                                new CompoundStatement(
                                                        new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"),
                                                                new CompoundStatement(new HeapWritingStatement("v1", new ArithmeticExpression('*', new HeapReadingExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10))))
                                                                        , new CompoundStatement(new HeapWritingStatement("v1", new ArithmeticExpression('*', new HeapReadingExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(2)))) ,new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))), new ReleaseStatement("cnt"))))))
                                                        ,
                                                        new CompoundStatement(new AcquireStatement("cnt"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new ArithmeticExpression('-',new HeapReadingExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1))))
                                                                        ,
                                                                        new ReleaseStatement("cnt")
                                                                        ))

                                                )
                                                )))));



        this.statementList = new ArrayList<IStatement>();
        this.statementList.add(statement1);
        this.statementList.add(statement2);
        this.statementList.add(statement3);
        this.statementList.add(statement4);
        this.statementList.add(statement5);
        this.statementList.add(statement6);
        this.statementList.add(statement7);
        this.statementList.add(statement8);
        this.statementList.add(statement9);
        this.statementList.add(statement10);
        this.statementList.add(statement11);
        this.statementList.add(statement12);
        this.statementList.add(statement13);
        this.statementList.add(statement14);
        this.statementList.add(statement15);
        this.statementList.add(statement16);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createStatements();

        List<String> statementStringList = this.statementList.stream().map(statement -> statement.toString()).collect(Collectors.toList());
        ObservableList<String> observablesList = FXCollections.observableList(statementStringList);
        this.statementListView.setItems(observablesList);

        executeButton.setOnAction(actionEvent -> {
            int index = this.statementListView.getSelectionModel().getSelectedIndex();
            try {
                IDictionary<String, IType> typeEnv = new Dictionary<String, IType>();
                this.statementList.get(index).typeCheck(typeEnv);
                IController controller = this.createNewController(this.statementList.get(index));
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    ProgramWindow programWindowController = new ProgramWindow(controller);
                    fxmlLoader.setController(programWindowController);
                    fxmlLoader.setLocation(getClass().getResource("../program/ProgramWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Type check error: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
