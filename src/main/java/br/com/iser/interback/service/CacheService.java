package br.com.iser.interback.service;

public interface CacheService {

  Integer get(String concatenated);

  void insert(String concatenated, Integer result);

  void remove(String concatenated);

  Boolean validateEntry();

}
