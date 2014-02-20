package com.efficio.conformity.service;

import com.efficio.conformity.bean.ConformityInput;
import com.efficio.conformity.bean.Marker;
import com.efficio.conformity.exception.ConformityException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte
 */
public interface ConformityTestingService {
    public boolean testConformity() throws ConformityException;
    public void checkMarkerMapping(ConformityInput info, Map<String, Marker> markerMap) throws ConformityException;
    public void checkStepValue(ConformityInput info) throws ConformityException;
}
