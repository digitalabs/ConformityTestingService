package com.efficio.conformity.service;

import com.efficio.conformity.bean.ConformityInput;
import com.efficio.conformity.bean.Marker;
import com.efficio.conformity.dao.FileGenotypeInfoDAO;
import com.efficio.conformity.dao.FileMarkerMapDAO;
import com.efficio.conformity.dao.GenotypeInfoDAO;
import com.efficio.conformity.dao.MarkerMapDAO;
import com.efficio.conformity.exception.ConformityException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte
 */

@RunWith(JUnit4.class)
public class ConformityTestingServiceTest {

    private ConformityInput info;
    private ConformityInput[] infos;
    private Map<String, Marker> markerMap;
    private ConformityTestingService conformityTestingService;

    @Before
    public void setup() {
        try {
            MarkerMapDAO markerMapDAO = new FileMarkerMapDAO("moreau.map");
            markerMap = markerMapDAO.retrieveMarkerMap();

            GenotypeInfoDAO genotypeInfoDAO = new FileGenotypeInfoDAO("moreau.dat");
            infos = genotypeInfoDAO.retrieveAllGenotypeInfos();
            info = infos[0];

            conformityTestingService = new ConformityTestingServiceImpl(infos, markerMap);
        } catch (Exception e) {
            fail(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testAll() {
        try {
            boolean status = conformityTestingService.testConformity();

            assertTrue(status);
        } catch (ConformityException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testMarkerNameMatchingPositive() {
        try {
            conformityTestingService.checkMarkerMapping(info, markerMap);
        } catch (ConformityException e) {
            e.printStackTrace();
            fail("Received unexpected exception");
        }
    }

    /*@Test
    public void testMarkerNameMatchingNegative() {

        // try adding an extra marker value to the genotype info

        info.getMarkers().add(new ConformityInputMarkerValue("test", "test"));

        try {
            conformityTestingService.checkMarkerMapping(info, markerMap);
        } catch (ConformityException e) {
            return;
        }

        fail("Did not receive expected exception");
    }*/

    @Test
    public void testMarkerStepValuePositive() {
        try {
            conformityTestingService.checkStepValue(info);
        } catch (ConformityException e) {
            e.printStackTrace();
            fail("Received unexpected exception when checking step value");
        }
    }

    @Test
    public void testMarkerStepValueNegative() {
        try {

            info.setStep("C1");

            conformityTestingService.checkStepValue(info);
        } catch (ConformityException e) {
            return;
        }

        fail("Unable to properly check step value");
    }
}