package org.example.exceptions;

public class InvalidArgument extends RuntimeException {
  public InvalidArgument(String message) {
    super(message);
  }
}