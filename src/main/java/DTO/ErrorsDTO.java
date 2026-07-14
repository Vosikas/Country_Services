package DTO;

import java.time.LocalDateTime;

public class ErrorsDTO {
    private String message;
    private int statusCode;
    private String timestamp;
    public ErrorsDTO(String message,int statusCode){
            this.message = message;
            this.statusCode =statusCode;
            this.timestamp = LocalDateTime.now().toString();


    }


}
