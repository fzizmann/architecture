package de.app.controller;

import de.app.model.ModelA;
import de.app.view.ViewA;

public class ControllerA {

    private ViewA view;

    private ModelA model;

    public ViewA getView() {
        return view;
    }

    public void setView(ViewA view) {
        this.view = view;
    }

    public ModelA getModel() {
        return model;
    }

    public void setModel(ModelA model) {
        this.model = model;
    }
}
