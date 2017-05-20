package de.fz.dsl;

import java.util.ArrayList;

public class ArchitectureDescription {

    private ArrayList<Component> components;

    public ArchitectureDescription() {
        this.components = new ArrayList<Component>();
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }
}
