package br.com.iser.interback.exception;

public class CustomerNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CustomerNotFoundException(String string) {
    super(string);
  }
}