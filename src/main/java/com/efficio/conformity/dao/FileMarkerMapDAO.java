package com.efficio.conformity.dao;

import com.efficio.conformity.bean.Marker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte
 */
public class FileMarkerMapDAO implements MarkerMapDAO {

    private String fileName;

    public FileMarkerMapDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, Marker> retrieveMarkerMap() {
        try {
            // init var
            Map<String, Marker> markerMap = new HashMap<String, Marker>();

            // open and start reading file
            File file = openFile();
            Scanner scanner = new Scanner(file);

            int lineCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line == null || line.length() == 0) {
                    continue;
                }

                // skip header line
                if (lineCount == 0) {
                    lineCount++;
                    continue;
                }

                Marker marker = extractMarkerFromLine(line);
                markerMap.put(marker.getLocus(), marker);
                lineCount++;
            }

            return markerMap;

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    private Marker extractMarkerFromLine(String line) {
        Scanner scanner = new Scanner(line);
        Marker marker = new Marker();

        marker.setLocus(scanner.next());
        marker.setChromosomeIndex(scanner.nextInt());
        marker.setQtlIndex(scanner.nextInt());
        marker.setQtlPosition(scanner.nextFloat());

        if (scanner.hasNext()) {
            String allelles = scanner.next();
            String[] favoriteAllelles = allelles.split("/");
            marker.setFavorableAlleles(favoriteAllelles);
        }
        return marker;
    }

    protected File openFile() throws Exception {

        return new File(ClassLoader.getSystemClassLoader().getResource(fileName).toURI());

    }
}
