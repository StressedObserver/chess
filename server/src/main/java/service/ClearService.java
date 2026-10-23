package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import requests.*;
import results.*;

public class ClearService {

    private final DataAccess dao;

    public ClearService(DataAccess dao){
        this.dao = dao;
    }

    public ClearResult clear(ClearRequest request) throws DataAccessException {
        dao.clear();

        return new ClearResult();
    }
}
