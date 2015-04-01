package model.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.data.TurtleData;


public class TurtleList implements Iterable<Turtle> {
    private List<Turtle> currentTurtles;
    private List<Turtle> allTurtles;
    private List<Turtle> tellTurtles;
    private Map<Integer, Turtle> ids;
    private TurtleData turtData;
    private boolean tellCalled;

    public TurtleList(TurtleData turtleData) {
        currentTurtles = new ArrayList<Turtle>();
        allTurtles = new ArrayList<Turtle>();
        tellTurtles = new ArrayList<Turtle>();
        turtData = turtleData;

        ids = new HashMap<Integer, Turtle>();
        Turtle turtle = new Turtle(1);
        ids.put(1, turtle);
        currentTurtles.add(turtle);
        allTurtles.add(turtle);
        tellTurtles.add(turtle);
        turtData.addTurtles(turtle);

    }

    public void linkTurtleData(TurtleData in) {
        turtData = in;
        // turtData.addTurtles(allTurtles);
    }

    public Turtle[] getCurrentTurtles() {
        return currentTurtles.toArray(new Turtle[currentTurtles.size()]);
    }

    public Turtle[] getAllTurtles() {
        return allTurtles.toArray(new Turtle[allTurtles.size()]);
    }

    public void setTempTurtles(int[] t) {
        currentTurtles.clear();
        for (int i : t) {
            addTurtle(currentTurtles, i);
        }
    }

    public void setTellCalled() {
        tellCalled = true;
    }

    public boolean getTellCalled() {
        return tellCalled;
    }

    public void resetTellTurtles() {
        currentTurtles = tellTurtles;
        tellCalled = false;
    }

    public List<Turtle> getTellTurtles() {
        tellCalled = false;
        return tellTurtles;
    }

    // Adds the graphic to the composition.
    public double tell(Double[] identities) {
        tellTurtles.clear();
        for (double i : identities) {
            addTurtle(tellTurtles, i);
        }
        currentTurtles = tellTurtles;
        return tellTurtles.get(tellTurtles.size() - 1).getName();
    }

    private void addTurtle(List<Turtle> currentTurtles2, double i) {
        if (ids.keySet().contains((int)i)) {
            currentTurtles2.add(ids.get((int)i));
        }
        else {
            Turtle jTurtle = new Turtle(ids.size() + 1);
            ids.put(jTurtle.getName(), jTurtle);
            allTurtles.add(jTurtle);
            turtData.addTurtles(jTurtle);
            currentTurtles2.add(jTurtle);
        }
    }

    // Removes the graphic from the composition.
    public void remove(Turtle t) {
        currentTurtles.remove(t);
    }

    public double turtles() {
        return ids.keySet().size();
    }

    public void refresh() {
        currentTurtles.forEach((t) -> t.refresh());
    }

    public void move(double distance) {
        currentTurtles.forEach((t) -> t.move(distance));
    }

    public void rotate(double ang) {
        currentTurtles.forEach((t) -> t.rotate(ang));
    }

    public void towards(double targetX, double targetY) {
        currentTurtles.forEach((t) -> t.towards(targetX, targetY));
    }

    public void setPosition(double newX, double newY) {
        currentTurtles.forEach((t) -> t.setPosition(newX, newY));
    }

    public void setHeading(double newAng) {
        currentTurtles.forEach((t) -> t.setHeading(newAng));
    }

    public void setPenDown(boolean down) {
        currentTurtles.forEach((t) -> t.setPenDown(down));
    }

    public void setPenSize(double index) {
        currentTurtles.forEach((t) -> t.setPenSize(index));
    }

    public void setPenShape(double index) {
        currentTurtles.forEach((t) -> t.setPenShape(index));
    }

    public void setPenColor(double index) {
        currentTurtles.forEach((t) -> t.setPenColor(index));
    }

    public void setVisible(boolean vis) {
        currentTurtles.forEach((t) -> t.setVisible(vis));
    }

    public void setClear(boolean clr) {
        currentTurtles.forEach((t) -> t.setClear(clr));
    }

    public double getTranslationDistance() {
        return currentTurtles.get(currentTurtles.size() - 1).getTranslationDistance();
    }

    public double getRotationAngle() {
        return currentTurtles.get(currentTurtles.size() - 1).getRotationAngle();
    }

    public double getX() {
        return currentTurtles.get(currentTurtles.size() - 1).getX();
    }

    public double getOldX() {
        return currentTurtles.get(currentTurtles.size() - 1).getOldX();
    }

    public double getY() {
        return currentTurtles.get(currentTurtles.size() - 1).getY();
    }

    public double getOldY() {
        return currentTurtles.get(currentTurtles.size() - 1).getOldY();
    }

    public double getHeading() {
        return currentTurtles.get(currentTurtles.size() - 1).getHeading();
    }

    public double getOldHeading() {
        return currentTurtles.get(currentTurtles.size() - 1).getOldHeading();
    }

    public Integer getName() {
        return currentTurtles.get(currentTurtles.size() - 1).getName();
    }

    public double isShowing() {
        return currentTurtles.get(currentTurtles.size() - 1).isShowing();
    }

    public double isPenDown() {
        return currentTurtles.get(currentTurtles.size() - 1).isPenDown();
    }

    public boolean isClear() {
        return currentTurtles.get(currentTurtles.size() - 1).isClear();
    }

    @Override
    public Iterator<Turtle> iterator() {
        return currentTurtles.iterator();
    }

}
