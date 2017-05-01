package com.example.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.HomeController;


/*
试验发现抛error先进入ErrorHandleControllerAdvice，然后可能进入MyErrorController，
比如当url扩展名是html时会进入到 MyErrorController.errorHtml中处理
 */
@ControllerAdvice(basePackageClasses = HomeController.class)
public class ErrorHandleControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		HttpStatus status = getStatus(request);
		HashMap<String , Object> data = new HashMap<>();
		data.put("status", status.value());
		data.put("errmsg", ex.getMessage());
		data.put("errstack", Util1.getExceptionStackTrace(ex));
		//Object obj = new CustomErrorType(status.value(), ex.getMessage());
		return new ResponseEntity<>(data, status);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}
}