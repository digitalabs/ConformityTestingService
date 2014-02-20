package com.efficio.conformity.bean;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte

 */
public class Marker {
    private String locus;
    private Integer chromosomeIndex;
    private Integer qtlIndex;
    private Float qtlPosition;
    private String[] favorableAlleles;

    public String getLocus() {
        return locus;
    }

    public void setLocus(String locus) {
        this.locus = locus;
    }

    public Integer getChromosomeIndex() {
        return chromosomeIndex;
    }

    public void setChromosomeIndex(Integer chromosomeIndex) {
        this.chromosomeIndex = chromosomeIndex;
    }

    public Integer getQtlIndex() {
        return qtlIndex;
    }

    public void setQtlIndex(Integer qtlIndex) {
        this.qtlIndex = qtlIndex;
    }

    public Float getQtlPosition() {
        return qtlPosition;
    }

    public void setQtlPosition(Float qtlPosition) {
        this.qtlPosition = qtlPosition;
    }

    public String[] getFavorableAlleles() {
        return favorableAlleles;
    }

    public void setFavorableAlleles(String[] favorableAlleles) {
        this.favorableAlleles = favorableAlleles;
    }
}
