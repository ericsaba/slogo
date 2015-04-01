package view;

import java.util.ArrayList;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import main.Constants;
import model.turtle.Turtle;
import controller.Controller;

public class TurtleMenu extends AbstractMenu{
	private ObservableMap<String, Turtle> myMap;

	public TurtleMenu(Controller controller, ObservableMap<String, Turtle> turtleMap) {
		super(Constants.TURTLE_MENU, controller);
		myMap = turtleMap;
		
		turtleMap.addListener(new MapChangeListener<String, Turtle>() {
			@Override
			public void onChanged(
					javafx.collections.MapChangeListener.Change<? extends String, ? extends Turtle> arg0) {
				addChoices(new ArrayList<String>(turtleMap.keySet()));
			}
		});
		
		addChoices(new ArrayList<String>(turtleMap.keySet()));
	}

	@Override
	protected void updateOption(String s) {
		Turtle turtle = myMap.get(s);
		
		new EditTurtleView(turtle);
		
	}

}
