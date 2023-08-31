package it.shop.shoes.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class DtoError {
	
	private Instant time;
    private int status;
    private String error;
    private String exception;
    private String message;

    
    
    public static final String internalError500 = "{\r\n" + "  \"status\": 500,\r\n" + "\"error\": Bad Request,\r\n"
            + "  \"message\": \"Exception custom 500, internal server error\"\r\n" + "}";
    
    public static final String error400 = "{\r\n" + "  \"status\": 500,\r\n" + "\"error\": Bad Request,\r\n"
            + "  \"message\": \"Exception custom 400, error\"\r\n" + "}";
    
    
}
