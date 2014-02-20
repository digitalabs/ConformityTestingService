package com.efficio.conformity.dao;

import com.efficio.conformity.bean.ConformityInput;
import com.efficio.conformity.bean.ConformityInputMarkerValue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte
 */
public class FileGenotypeInfoDAO implements GenotypeInfoDAO {

    private String fileName;
    public final static int CONSTANT_HEADER_COUNT = 6;

    public FileGenotypeInfoDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public ConformityInput retrieveGenotypeInfoByName(String name) throws Exception {
        ConformityInput[] infos = retrieveAllGenotypeInfos();

        for (ConformityInput info : infos) {
            if (info.getName().equals(name)) {
                return info;
            }
        }

        return null;
    }

    @Override
    public ConformityInput[] retrieveAllGenotypeInfos() throws Exception {
        List<ConformityInput> conformityInputList = new LinkedList<ConformityInput>();
        List<String> markerHeaders = new LinkedList<String>();

        File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).toURI());
        Scanner scanner = new Scanner(file);

        int lineCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter("\t");

            // retrieve the header names of markers contained in the file
            if (lineCount == 0) {
                // position the scanner to the start of markers

                for (int i = 0; i < CONSTANT_HEADER_COUNT; i++) {
                    rowScanner.next();
                }

                while (rowScanner.hasNext()) {
                    markerHeaders.add(rowScanner.next());
                }


            } else {
                ConformityInput info = new ConformityInput();
                info.setName(rowScanner.next());
                info.setParent1Name(rowScanner.next());
                info.setParent2Name(rowScanner.next());
                info.setStep(rowScanner.next());
                info.setCycle(rowScanner.next());
                info.setGroup(rowScanner.next());


                int currentMarkerCount = 0;
                List<ConformityInputMarkerValue> values = new LinkedList<ConformityInputMarkerValue>();
                while (currentMarkerCount < markerHeaders.size()) {

                    ConformityInputMarkerValue value = new ConformityInputMarkerValue();
                    value.setName(markerHeaders.get(currentMarkerCount));

                    if (rowScanner.hasNext()) {
                        value.setValue(rowScanner.next());
                    } else {
                        value.setValue("");
                    }

                    values.add(value);
                    currentMarkerCount++;
                }

                info.setMarkers(values);

                conformityInputList.add(info);
            }

            lineCount++;
        }

        ConformityInput[] returnVal = new ConformityInput[conformityInputList.size()];
        returnVal = conformityInputList.toArray(returnVal);
        return returnVal;
    }
}
