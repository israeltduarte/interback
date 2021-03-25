package br.com.iser.interback.exception;

public class ValidationErrorException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ValidationErrorException(String string) {
    super(string);
  }
}