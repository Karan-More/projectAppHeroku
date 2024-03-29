package com.capstone.kanbantool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationErrorServiceMap {

    public ResponseEntity<?> ValidationServiceMap(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errorMap = new HashMap<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        // if no error found
        return null;
    }
}
