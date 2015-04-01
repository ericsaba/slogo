package main;

import model.data.DisplayData;
import model.data.FunctionData;
import model.data.TurtleData;
import model.data.VariableData;
import view.ViewCollection;
import controller.Controller;

public class Workspace {

	public Workspace(){
		Controller myController = new Controller();
		myController.addModel(new DisplayData());
		myController.addModel(new TurtleData());
		myController.addModel(new VariableData());
		myController.addModel(new FunctionData());
		new ViewCollection(myController);
	}

}
