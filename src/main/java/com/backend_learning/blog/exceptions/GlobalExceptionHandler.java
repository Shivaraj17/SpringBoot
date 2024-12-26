package com.backend_learning.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend_learning.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	//write the coustom exception generated of other file in this file also 
	// Handle ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodargsNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		//the below are object error are object error 
//		Provides the complete result of the validation process, 
//including error count and field info.	
		
		//BindingResult: 3 errors
//		Field error in object 'userDto' on field 'username': rejected value []; default message [Username is required]
//	    Field error in object 'userDto' on field 'email': rejected value [invalid-email]; default message [Invalid email format]
//		Field error in object 'userDto' on field 'password': rejected value [123]; default message [Password must be at least 8 characters long]

//	Returns a list of individual validation errors 
//(as ObjectError or FieldError objects).
//getAllErrors()
//		[
//		    FieldError: field='username', rejectedValue='', defaultMessage='Username is required',
//		    FieldError: field='email', rejectedValue='invalid-email', defaultMessage='Invalid email format',
//		    FieldError: field='password', rejectedValue='123', defaultMessage='Password must be at least 8 characters long'
//		]
		ex.getBindingResult().getAllErrors().forEach((error)->{ 
			// Cast the error to FieldError to get field-specific info
			String fieldName =((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
	        // Customize the response, you can return more information if needed
	        String errorMessage = ex.getMessage();
	        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	    }
		@ExceptionHandler(ApiException.class)
	    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
	        String message = ex.getMessage();
	        ApiResponse apiResponse = new ApiResponse(message, true);
	        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	    }
}
