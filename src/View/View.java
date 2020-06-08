package View;

import Controller.IController;
import CustomException.MyException;

import java.util.ArrayList;
import java.util.Scanner;

public class View implements IView{
    private ArrayList<IController> controllerList;

    public View() {
        this.controllerList = new ArrayList<IController>();
    }

    @Override
    public void add(IController controller) {
        this.controllerList.add(controller);
    }

    public void run() {
        try {
            while (true)
            {
                Scanner console = new Scanner(System.in);
                int command = console.nextInt();
                if (command == 1)
                    this.controllerList.get(0).allStep();
                if (command == 2)
                    this.controllerList.get(1).allStep();
                if (command == 3)
                    this.controllerList.get(2).allStep();
            }
        }
        catch (MyException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
