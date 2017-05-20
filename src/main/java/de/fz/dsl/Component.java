package de.fz.dsl;

import java.util.ArrayList;


public class Component {

    private String componentName;

    private ArrayList<Package> consist;

    private ArrayList<Component> uses;


    public Component(String componentName) {
        this.componentName = componentName;
        this.consist = new ArrayList<Package>();
        this.uses = new ArrayList<Component>();
    }

    public ArrayList<Package> getConsist() {
        return consist;
    }

    public void setConsist(ArrayList<Package> consist) {
        this.consist = consist;
    }

    public ArrayList<Component> getUses() {
        return uses;
    }

    public void setUses(ArrayList<Component> uses) {
        this.uses = uses;
    }

    public Component addUses(Component component) {
        this.uses.add(component);
        return this;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
}
