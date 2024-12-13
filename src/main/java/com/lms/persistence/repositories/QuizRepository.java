package com.lms.persistence.repositories;

import com.lms.persistence.entities.QuizEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuizRepository {

  private final List<QuizEntity> store = new ArrayList<>();

  public boolean add(QuizEntity entity) {
    return store.add(entity);
  }

  public List<QuizEntity> findAll() {
    return new ArrayList<>(store);
  }

  public QuizEntity findById(int id) {
    return store.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
  }
}
