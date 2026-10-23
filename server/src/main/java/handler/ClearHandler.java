package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import service.*;
import requests.*;
import results.*;
import dataaccess.*;

public class ClearHandler {
    private final ClearService clearService;
    private final Gson gson = new Gson();
    public ClearHandler(ClearService clearService){
        this.clearService = clearService;
    }

    public void clear(@NotNull Context context){
        try {
            ClearRequest request = new ClearRequest();
            ClearResult result = clearService.clear(request);
            context.status(200).json(result);
        } catch(DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }
}
