package com.efficio.conformity.service;

import com.efficio.conformity.bean.ConformityInput;
import com.efficio.conformity.bean.ConformityInputMarkerValue;
import com.efficio.conformity.bean.Marker;
import com.efficio.conformity.exception.ConformityException;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte
 */
public class ConformityTestingServiceImpl implements ConformityTestingService {

    public static final String EXPECTED_STEP_PATTERN_STRING = "IL|CR|RIL|DH|S.*";
    public static final Pattern STEP_PATTERN = Pattern.compile(EXPECTED_STEP_PATTERN_STRING);

    private ConformityInput[] conformityInputs;
    private Map<String, Marker> markerMap;
    private Map<String, Character> genotypeInfo;
    private Map<Character, String> reverseGenotypeInfo;

    private Character genotypeCount = 0;

    public ConformityTestingServiceImpl(ConformityInput[] conformityInputs, Map<String, Marker> markerMap) {
        this.conformityInputs = conformityInputs;
        this.markerMap = markerMap;

        genotypeInfo = new LinkedHashMap<String, Character>();
        reverseGenotypeInfo = new LinkedHashMap<Character, String>();
    }

    @Override
    public boolean testConformity() throws ConformityException {
        for (ConformityInput conformityInput : conformityInputs) {

            checkMarkerMapping(conformityInput, markerMap);
            checkPedigree(conformityInput);
            checkStepValue(conformityInput);

            if (conformityInput.getStep().equals("IL")) {
                processGenotypeInfo(conformityInput);
            }

            for (ConformityInputMarkerValue markerValue : conformityInput.getMarkers()) {
                if (!markerValue.getValue().contains("/")) {
                    processHomozygousMarker(conformityInput, markerValue);
                } else {
                    processHeterozygousMarker(conformityInput, markerValue);
                }
            }
        }

        return true;
    }

    public void checkMarkerMapping(ConformityInput info, Map<String, Marker> markerMap) throws ConformityException {
        List<ConformityInputMarkerValue> values = info.getMarkers();
        Set<String> keys = markerMap.keySet();

        for (String key : keys) {
            if (!key.startsWith("qtl")) {

                // TODO optimize algo
                boolean found = false;
                for (ConformityInputMarkerValue value : values) {
                    if (value.getName().equals(key)) {
                        found = true;
                    }
                }

                if (!found) {
                    throw new ConformityException("Error: marker " + info.getName() + " of the genotype file doesn't match the one of the map file");
                }
            }
        }
    }

    public void processHomozygousMarker(ConformityInput input, ConformityInputMarkerValue marker) throws ConformityException {
        // blank marker value
        if (marker.getValue().isEmpty() || marker.getValue().equals("-")) {
            if (input.getStep().equals("IL")) {
                throw new ConformityException("Error: Missing data at marker " + marker.getName() + " IL parental individual " + input.getName() +
                        " in the genotype/pedigree file");
            }
        } else {
            if (!genotypeInfo.containsKey(marker.getValue())) {
                throw new ConformityException("Genotyping error at marker " + marker.getName() + " (different to founders) in the genotype/pedigree file");
            }
        }
    }

    public void processHeterozygousMarker(ConformityInput input, ConformityInputMarkerValue marker) throws ConformityException {
        String[] values = marker.getValue().split("/");

        for (String value : values) {
            if (!genotypeInfo.containsKey(value) && !value.equals("-")) {
                throw new ConformityException("Genotyping error at marker " + marker.getName() + " (different to founders) in the genotype/pedigree file");
            }
        }
    }

    public void checkPedigree(ConformityInput info) throws ConformityException {
        if (info.getParent1Name().isEmpty() || info.getParent2Name().isEmpty()) {
            throw new ConformityException("Error: pedigree name of the individual " + info.getName() + " of the genotype file");
        }
    }

    public void checkStepValue(ConformityInput info) throws ConformityException {
        if (!STEP_PATTERN.matcher(info.getStep()).matches()) {
            throw new ConformityException("Error: mating step (Step column)  of individual " + info.getName() + " of the genotype/pedigree file");
        }
    }

    public void processGenotypeInfo(ConformityInput input) throws ConformityException {

        for (ConformityInputMarkerValue value : input.getMarkers()) {
            int index = value.getValue().indexOf("/");
            if (index != -1) {

                // heterozyous 2nd allelle
                String key = value.getValue().substring(index + 1);
                addGenotypeInfo(key);

                //heterozygous first allelle
                key = value.getValue().substring(0, index);
                addGenotypeInfo(key);
            } else {
                addGenotypeInfo(value.getValue());
            }
        }

        // maintain reverse map for correspondence checking later on
        for (Map.Entry<String, Character> entry : genotypeInfo.entrySet()) {
            reverseGenotypeInfo.put(entry.getValue(), entry.getKey());
        }
    }

    public void addGenotypeInfo(String key) throws ConformityException {
        if (!genotypeInfo.containsKey(key)) {
            if (genotypeCount == 255) {
                throw new ConformityException("Error: The number of valid (none missing) different genotypes cannot exceed 255");
            }

            genotypeInfo.put(key, genotypeCount++);
        }
    }
}
