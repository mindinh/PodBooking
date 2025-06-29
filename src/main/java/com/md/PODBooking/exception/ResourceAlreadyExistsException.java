package com.md.PODBooking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceAlreadyExistsException extends RuntimeException {
  public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {

    super(String.format("%s already exists with the given input data %s: %s", resourceName, fieldName, fieldValue));
  }

}
