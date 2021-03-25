package br.com.iser.interback.service;

import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.entity.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface EncryptService {

  Key generateKeys();

  String encrypt(String text, PublicKey publicKey);

  String decrypt(String text, PrivateKey privateKey);

  CustomerDTO encryptCustomer(Long id, String publicKey);

}
