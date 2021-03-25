package br.com.iser.interback.constant;

public class Errors {

  private Errors() {
  }

  public static final String CUSTOMER_NOT_FOUND = "Customer hasn't been found";
  public static final String VALIDATION_NAME = "Invalid name";
  public static final String VALIDATION_EMAIL = "Invalid e-mail";
  public static final String VALIDATION_MULTIPLIER = "Invalid multiplier";
  public static final String VALIDATION_NUMBER = "Invalid number";
  public static final String UNIQUE_EMAIL = "Email must be unique";
  public static final String ENCRYPTING = "Encrypting error";
  public static final String RSA_INSTANCE = "Error loading RSA Instance";
  public static final String ALREADY_ENCRYPTED = "Customer already encrypted";
  public static final String WRONG_PUBLIC_KEY = "Wrong public key";

}
