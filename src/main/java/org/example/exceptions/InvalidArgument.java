package org.example.exceptions;

public final class InvalidArgument extends RuntimeException {
  public InvalidArgument(final String message) {
    super(message);
  }
}
