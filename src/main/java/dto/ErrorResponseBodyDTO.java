package dto;

import java.time.LocalDateTime;

public class ErrorResponseBodyDTO {
    private String message;
    private int statusCode;
    private String timestamp;
    public ErrorResponseBodyDTO(String message, int statusCode){
            this.message = message;
            this.statusCode = statusCode;
            this.timestamp = LocalDateTime.now().toString();
    }
    public String getMessage(){
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
