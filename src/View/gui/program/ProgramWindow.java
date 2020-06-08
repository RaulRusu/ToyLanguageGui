package View.gui.program;

import Controller.IController;
import CustomException.MyException;
import Model.ADT.IStack;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class ProgramWindow implements Initializable {
    private IController controller;
    private ProgramState selectedProgramState;

    @FXML
    private TextField numberOfProgramStatesTestField;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private Button executeButton;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> heapAddressTableColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, IValue> heapValueTableColumn;

    @FXML
    private ListView<IValue> outListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private TableView<Map.Entry<String, IValue>> symTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symVariableTableColumn;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symValueTableColumn;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> indexTableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Pair<Integer, List<Integer>>>, String> listTableColumn;

    public ProgramWindow(IController controller) {
        this.controller = controller;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.heapAddressTableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getKey()).asObject());
        this.heapValueTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        this.symVariableTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        this.symValueTableColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue().getValue().toString()));

        this.indexTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().toString()));
        this.listTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        this.selectedProgramState = this.controller.getRepository().getProgramList().get(0);
        this.programStateListView.setOnMouseClicked(event -> this.setSelectedProgramState());
        this.executeButton.setOnAction(actionEvent -> executeOneStep());

        this.setAllViews();
    }


    private void executeOneStep() {
        try {
            this.controller.oneStep();
            this.setAllViews();
        }
        catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void setSelectedProgramState() {
        int index = this.programStateListView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        this.selectedProgramState = this.controller.getRepository().getProgramStateById(this.getProgramsStatesIds().get(index));
        this.setSymTableView();
        this.setExecutionStackListView();
    }

    private List<Integer> getProgramsStatesIds() {
        return this.controller.getRepository().getProgramList().stream().map(programState -> programState.getID()).collect(Collectors.toList());
    }

    private void setProgramStateListView() {
        ObservableList<Integer> idObservableList = FXCollections.observableList(this.getProgramsStatesIds());
        this.programStateListView.setItems(idObservableList);
        this.programStateListView.refresh();
    }

    private void setExecutionStackListView() {
        List<String> listExecutionStack = new ArrayList<>();

        ListIterator iterator = this.selectedProgramState.getExecutionStack().getIterator();

        while(iterator.hasPrevious()) {
            listExecutionStack.add(iterator.previous().toString());
        }

        this.executionStackListView.setItems(FXCollections.observableList(listExecutionStack));
        this.executionStackListView.refresh();
    }

    private void setHeapTableView() {
        List<Map.Entry<Integer, IValue>> listHeapTable = new ArrayList<>(this.selectedProgramState.getHeap().getContent().entrySet());
        this.heapTableView.setItems(FXCollections.observableList(listHeapTable));
        this.heapTableView.refresh();
    }

    private void setSemaphoreTableView() {
        List<Map.Entry<Integer, Pair<Integer, List<Integer>>>> listSemaphore = new ArrayList<>(this.selectedProgramState.getSemaphoreTable().getContent().entrySet());
        this.semaphoreTableView.setItems(FXCollections.observableList(listSemaphore));
        this.semaphoreTableView.refresh();
    }

    private void setOutListView() {
        List<IValue> outList = this.selectedProgramState.getOutList().getValues();
        this.outListView.setItems(FXCollections.observableList(outList));
        this.outListView.refresh();
    }

    private void setFileTableListView() {
        List<String> fileTableList = this.selectedProgramState.getFileTable().getContent().keySet().stream().map(fileNames -> fileNames.toString()).collect(Collectors.toList());
        this.fileTableListView.setItems(FXCollections.observableList(fileTableList));
        this.fileTableListView.refresh();
    }

    private void setSymTableView() {
        List<Map.Entry<String, IValue>> symTableList = new ArrayList<>(this.selectedProgramState.getSymbolTable().getContent().entrySet());
        symTableList.stream().peek(element -> System.out.println(element.getKey() + " " + element.getValue())).collect(Collectors.toList());
        this.symTableView.setItems(FXCollections.observableList(symTableList));
        this.symTableView.refresh();
    }

    private void setAllViews() {
        this.setExecutionStackListView();
        this.setHeapTableView();
        this.setOutListView();
        this.setFileTableListView();
        this.setSymTableView();
        this.setProgramStateListView();
        this.setSemaphoreTableView();
    }
}
