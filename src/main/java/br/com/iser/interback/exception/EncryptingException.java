package br.com.iser.interback.exception;

public class EncryptingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public EncryptingException(String string) {
    super(string);
  }
}