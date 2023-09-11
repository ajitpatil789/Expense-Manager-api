package in.ajit.expensetracker.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.ajit.expensetracker.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		ErrorObject eObject = new ErrorObject();

		eObject.setStatusCode(HttpStatus.NOT_FOUND.value());
		eObject.setMessage(ex.getMessage());
		eObject.setTimstamp(new Date());

		return new ResponseEntity<ErrorObject>(eObject, HttpStatus.NOT_FOUND);
	}

	//Bad Request User entered Wrong info while reading expenses by id, he entered string instead of digit
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ErrorObject eObject = new ErrorObject();

		eObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		eObject.setMessage(ex.getMessage());
		eObject.setTimstamp(new Date());

		return new ResponseEntity<ErrorObject>(eObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleGeneralException(Exception ex,
			WebRequest request) {
		ErrorObject eObject = new ErrorObject();

		eObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		eObject.setMessage(ex.getMessage());
		eObject.setTimstamp(new Date());

		return new ResponseEntity<ErrorObject>(eObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// customize hibernate validation annotation response. 
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new HashMap<String,Object>();
		
		body.put("statusCode", HttpStatus.BAD_REQUEST.value());
		body.put("timestamp", new Date());
		
		List<String> errors = ex.getBindingResult()
		.getAllErrors()
		.stream()
		.map(x -> x.getDefaultMessage())
		.collect(Collectors.toList());
		
		body.put("message", errors);
		
		return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<Object> handleItemExistsExceptio(ItemAlreadyExistsException ex, WebRequest request){
		ErrorObject eObject = new ErrorObject();
		eObject.setStatusCode(HttpStatus.CONFLICT.value());
		eObject.setMessage(ex.getMessage());
		eObject.setTimstamp(new Date());
		return new ResponseEntity<Object>(eObject,HttpStatus.CONFLICT);
	}
}


















