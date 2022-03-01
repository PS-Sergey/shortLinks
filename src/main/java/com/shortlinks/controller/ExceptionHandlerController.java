package com.shortlinks.controller;

import com.shortlinks.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Message> resourceNotFoundException(RuntimeException ex, WebRequest request) {

        Message message = new Message(ex.getMessage());

        return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Message> illegalFormatException(RuntimeException ex, WebRequest request) {

        Message message = new Message(ex.getMessage());

        return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
    }
}
