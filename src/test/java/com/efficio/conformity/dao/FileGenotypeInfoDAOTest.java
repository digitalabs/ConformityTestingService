package com.efficio.conformity.dao;

import com.efficio.conformity.bean.ConformityInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte

 */

@RunWith(JUnit4.class)
public class FileGenotypeInfoDAOTest {
    private GenotypeInfoDAO dut;

    @Before
    public void before() {
        dut = new FileGenotypeInfoDAO("moreau.dat");
    }

    @Test
    public void testRetrieveGenotypeInfo() {
        try {
            ConformityInput[] infos = dut.retrieveAllGenotypeInfos();

            assertNotNull(infos);
            assertTrue(infos.length > 0);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            fail(e.getMessage());
        }
    }


}
