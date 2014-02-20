package com.efficio.conformity.bean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte

 */
public class ConformityInput {

    private String name;
    private String parent1Name;
    private String parent2Name;

    private String step;
    private String cycle;
    private String group;

    private List<ConformityInputMarkerValue> markers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent1Name() {
        return parent1Name;
    }

    public void setParent1Name(String parent1Name) {
        this.parent1Name = parent1Name;
    }

    public String getParent2Name() {
        return parent2Name;
    }

    public void setParent2Name(String parent2Name) {
        this.parent2Name = parent2Name;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ConformityInputMarkerValue> getMarkers() {
        return markers;
    }

    public void setMarkers(List<ConformityInputMarkerValue> markers) {
        this.markers = markers;
    }
}
