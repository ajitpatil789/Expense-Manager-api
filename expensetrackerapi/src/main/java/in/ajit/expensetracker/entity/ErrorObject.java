package in.ajit.expensetracker.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorObject {
	
	private Integer statusCode;
	
	private String Message;
	
	private Date timstamp;
	

}
