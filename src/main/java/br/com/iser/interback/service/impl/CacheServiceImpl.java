package br.com.iser.interback.service.impl;

import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.service.CacheService;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Transient;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

  @Transient
  static Map<String, Integer> cache = new LinkedHashMap<>();

  @Override
  public Integer get(String concatenated) {

    return CacheServiceImpl.cache.get(concatenated);
  }

  @Override
  public void insert(String concatenated, Integer result) {

    CacheServiceImpl.cache.put(concatenated, result);
  }

  @Override
  public void remove(String concatenated) {

    CacheServiceImpl.cache.remove(cache.keySet().toArray()[0]);
  }

  @Override
  public Boolean validateEntry() {

    return CacheServiceImpl.cache.size() < Constants.CACHE_SIZE;
  }
}
