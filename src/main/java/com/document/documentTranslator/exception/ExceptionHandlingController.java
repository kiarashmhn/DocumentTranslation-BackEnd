package com.document.documentTranslator.exception;

import com.document.documentTranslator.enums.ExceptionMessages;
import com.document.documentTranslator.response.Response;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@EnableWebMvc
public class ExceptionHandlingController {

	Logger logger = Logger.getLogger(ExceptionHandlingController.class);

	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseBody
	public ResponseEntity<Response> missingRequestHeaderException(HttpServletRequest request, Exception e) {

		Response response = new Response();

		response.setSuccess(false);
		response.setMessage(e.getCause().getMessage());

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Response> exceptionException(HttpServletRequest request, Exception e) {

		Response response = new Response();

		response.setSuccess(false);
		response.setMessage(ExceptionMessages.EXCEPTION_MESSAGE.getPersianMessage());

		logger.error("", e);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseEntity<Response> dataIntegrityViolationException(HttpServletRequest request, Exception e) {

		Response response = new Response();

		response.setSuccess(false);
		response.setMessage(ExceptionMessages.DATA_INTEGRITY_VIOLATION_EXCEPTION.getPersianMessage());

		logger.error("", e);

		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
	}

	@ExceptionHandler(DomainException.class)
	@ResponseBody
	public ResponseEntity<Response> domainException(DomainException domainException, @ModelAttribute("mappingFEMsg") final Object mappingFEMsgObject) {

		Response response = new Response();

		response.setSuccess(false);
		response.setMessage(domainException.getMessage());

		logger.error("", domainException);

		return ResponseEntity.ok().body(response);
	}

}
