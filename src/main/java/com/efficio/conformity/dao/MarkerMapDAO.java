package com.efficio.conformity.dao;

import com.efficio.conformity.bean.Marker;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte
 */
public interface MarkerMapDAO {
    public Map<String, Marker> retrieveMarkerMap();
}
