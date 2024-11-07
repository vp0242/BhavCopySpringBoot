package com.example.BhavCopySpringBoot.model;


import java.util.*;

public class Response {
     String status;
     String message;
     String errorDetails;
     Map<String, Object> response = new LinkedHashMap<>();
    
    public  Response(String status,String message,String error,Object data){
        this.status=status;
        this.message=message;  
        this.errorDetails=error;
        response.put("status", status);
        response.put("message",message);
        response.put("errorInfo", error != null? error : null);
        response.put("data", data);
        
    }


     public Map<String, Object> getResponse() {
         return response;
     }
     
}
