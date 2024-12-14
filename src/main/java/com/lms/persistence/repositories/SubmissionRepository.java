package com.lms.persistence.repositories;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.persistence.entities.DummySubmissionEntity;

@Service
public class SubmissionRepository {

  private final List<DummySubmissionEntity> store = new ArrayList<>();

  public boolean add(DummySubmissionEntity entity) {
    return store.add(entity);
  }

  public List<DummySubmissionEntity> findAll() {
    return new ArrayList<>(store);
  }

  public DummySubmissionEntity findById(int id) {
    return store.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
  }
}
