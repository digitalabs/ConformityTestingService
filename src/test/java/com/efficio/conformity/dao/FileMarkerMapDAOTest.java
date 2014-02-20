package com.efficio.conformity.dao;

import com.efficio.conformity.bean.Marker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte

 */

@RunWith(JUnit4.class)
public class FileMarkerMapDAOTest {

    private MarkerMapDAO dut;

    @Before
    public void setup() {
        dut = new FileMarkerMapDAO("moreau.map");
    }

    @Test
    public void testExtractMap() {
        Map<String, Marker> markerMap = dut.retrieveMarkerMap();

        assertNotNull(markerMap);
        assertTrue(markerMap.size() > 0);

        assertNotNull(markerMap.get("qtl2"));
    }


}
