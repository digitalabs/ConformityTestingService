package com.efficio.conformity.dao;

import com.efficio.conformity.bean.ConformityInput;

/**
 * Created by IntelliJ IDEA.
 * User: Daniel Villafuerte

 */
public interface GenotypeInfoDAO {
    public ConformityInput retrieveGenotypeInfoByName(String name) throws Exception;
    public ConformityInput[] retrieveAllGenotypeInfos() throws Exception;
}
