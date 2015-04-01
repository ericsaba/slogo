package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import model.data.AbstractDataModel;
import view.AbstractView;


public abstract class AbstractController implements PropertyChangeListener {
    private List<AbstractView> registeredViews;
    private List<AbstractDataModel> registeredModels;

    public AbstractController() {
        registeredViews = new ArrayList<AbstractView>();
        registeredModels = new ArrayList<AbstractDataModel>();
    }

    public void addModel(AbstractDataModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractDataModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }

    public void addView(AbstractView view) {
        registeredViews.add(view);
    }

    public void removeView(AbstractView view) {
        registeredViews.remove(view);
    }

    public List<AbstractView> getViews() {
        return registeredViews;
    }

    public List<AbstractDataModel> getModels() {
        return registeredModels;
    }

    // Use this to observe property changes from registered models
    // and propagate them on to all the views.

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        for (AbstractView view : registeredViews) {
            view.modelPropertyChange(evt);
        }
    }

    /**
     * This is a convenience method that subclasses can call upon
     * to fire property changes back to the models. This method
     * uses reflection to inspect each of the model classes
     * to determine whether it is the owner of the property
     * in question. If it isn't, a NoSuchMethodException is thrown,
     * which the method ignores.
     *
     * @param propertyName = The name of the property.
     * @param newValue = An object that represents the new value
     *        of the property.
     */
    protected void setModelProperty(String propertyName, Object newValue) {

        for (AbstractDataModel model : registeredModels) {
            try {
                Method method = model.getClass().
                        getMethod("set" + propertyName, new Class[] {
                                  newValue.getClass()
                        });
                method.invoke(model, newValue);

            }
            catch (Exception ex) {

            }
        }
    }

}
