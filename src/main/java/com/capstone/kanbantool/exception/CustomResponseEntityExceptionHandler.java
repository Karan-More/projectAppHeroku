package com.capstone.kanbantool.exception;

import com.capstone.kanbantool.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException e, WebRequest webRequest){

        ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(e.getMessage());

        return new ResponseEntity(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest webRequest){

        ProjectNotFoundExceptionResponce projectNotFoundExceptionResponce = new ProjectNotFoundExceptionResponce(ex.getMessage());

        return new ResponseEntity(projectNotFoundExceptionResponce, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameExistInDatabase(UsernameExistsInDatabaseException ex, WebRequest webRequest){

        UsernameExistInDatabaseResponse usernameExistInDatabaseResponse = new UsernameExistInDatabaseResponse(ex.getMessage());

        return new ResponseEntity(usernameExistInDatabaseResponse, HttpStatus.BAD_REQUEST);
    }

}
