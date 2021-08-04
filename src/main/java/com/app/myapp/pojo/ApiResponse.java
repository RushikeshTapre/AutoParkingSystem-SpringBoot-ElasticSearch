package com.app.myapp.pojo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse {


    boolean status;
    String message;
    LocalDateTime timeStamp;

    public ApiResponse(boolean status) {
        this.status=status;
        if(status==true){
            this.message="Successful";
        }
        else
            this.message="fail";
        this.timeStamp=LocalDateTime.now();
    }

    public static Map<String ,Object> generateResponse(boolean status, String message) {

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("time stamp", LocalDateTime.now());
        response.put("status","successful");
        if (!status){
            response.put("status","fail");
            response.put("message",message);
        }
        return response;
    }
    public static Map<String ,Object> generateResponse(boolean status) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("time stamp", LocalDateTime.now());
        response.put("status","fail");
        if (status){
            response.put("status", "successful");
        }
        return response;
    }

    public ApiResponse(boolean status, String message, LocalDateTime timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }


}
