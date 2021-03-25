package br.com.iser.interback.service.impl;

import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.entity.Key;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.exception.EncryptingException;
import br.com.iser.interback.exception.RSAInstanceException;
import br.com.iser.interback.repository.CustomerRepository;
import br.com.iser.interback.repository.KeyRepository;
import br.com.iser.interback.service.EncryptService;
import com.google.common.base.Charsets;
import com.sun.jersey.core.util.Base64;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Data
public class EncryptServiceImpl implements EncryptService {

  private final ModelMapper mapper;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private KeyRepository keyRepository;

  @Override
  public CustomerDTO encryptCustomer(Long id, String publicKeySent) {

    Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));
    if (Boolean.TRUE.equals(customer.getKey().getIsActive())) {
      throw new EncryptingException(Errors.ALREADY_ENCRYPTED);
    }

    if (!customer.getKey().getPublicKey().equals(publicKeySent)) {
      throw new EncryptingException(Errors.WRONG_PUBLIC_KEY);
    }

    Key key = customer.getKey();
    key.setIsActive(Boolean.TRUE);
    customer.setKey(key);

    PublicKey publicKey = this.generatePublicKeyFromString(publicKeySent);

    customer.setName(this.encrypt(customer.getName(), publicKey));
    customer.setEmail(this.encrypt(customer.getEmail(), publicKey));

    this.customerRepository.save(customer);

    CustomerDTO customerDTO = this.mapper.map(customer, CustomerDTO.class);

    log.debug("EncryptServiceImpl.encryptCustomer - End - CustomerDTO: {}", customerDTO);

    return customerDTO;
  }

  @Override
  public Key generateKeys() {

    try {

      KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");

      KeyPair keyPair = keyGenerator.generateKeyPair();

      keyGenerator.initialize(Constants.CRYPTO_SIZE, new SecureRandom());

      Key key = Key.builder()
          .publicKey(new String(Base64.encode(keyPair.getPublic().getEncoded())))
          .privateKey(new String(Base64.encode(keyPair.getPrivate().getEncoded())))
          .isActive(Boolean.FALSE)
          .build();

      log.debug("EncryptServiceImpl.generateKeys - End - Key: {}", key);

      return key;
    } catch (Exception e) {
      throw new RSAInstanceException(Errors.RSA_INSTANCE);
    }
  }

  @Override
  public String encrypt(String text, PublicKey publicKey) {

    try {

      final Cipher cipher;
      cipher = Cipher.getInstance(Constants.CRYPTO_TYPE);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);

      String result = new String(Base64.encode(cipher.doFinal(text.getBytes())), Charsets.UTF_8);

      log.debug("EncryptServiceImpl.encrypt - End - String: {}", result);

      return result;
    } catch (Exception e) {
      throw new EncryptingException(Errors.ENCRYPTING);
    }
  }

  @Override
  public String decrypt(String text, PrivateKey privateKey) {

    try {

      final Cipher cipher;
      cipher = Cipher.getInstance(Constants.CRYPTO_TYPE);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);

      String result = new String(cipher.doFinal(Base64.decode(text.getBytes(Charsets.UTF_8))));

      log.debug("EncryptServiceImpl.decrypt - End - String: {}", result);

      return result;
    } catch (Exception e) {
      throw new EncryptingException(Errors.ENCRYPTING);
    }
  }

  private PublicKey generatePublicKeyFromString(String publicKey) {

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey.getBytes()));

    try {

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");

      PublicKey result = keyFactory.generatePublic(keySpec);

      log.debug("EncryptServiceImpl.getPublicKeyFromString - End - PublicKey: {}", result);

      return result;
    } catch (Exception e) {
      throw new EncryptingException(Errors.ENCRYPTING);
    }
  }
}
