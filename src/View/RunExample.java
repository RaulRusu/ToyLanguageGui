package View;

import Controller.IController;
import CustomException.MyException;

public class RunExample extends Command {
    private IController controller;
    public RunExample(String key, String description, IController controller){
        super(key, description);
        this.controller=controller;
    }
    @Override
    public void execute() {
        try{
            controller.allStep();
        } catch (MyException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
