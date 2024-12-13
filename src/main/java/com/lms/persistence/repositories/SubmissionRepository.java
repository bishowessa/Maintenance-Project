package com.lms.persistence.repositories;

import com.lms.persistence.entities.SubmissionEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SubmissionRepository {

  private final List<SubmissionEntity> store = new ArrayList<>();

  public boolean add(SubmissionEntity entity) {
    return store.add(entity);
  }

  public List<SubmissionEntity> findAll() {
    return new ArrayList<>(store);
  }

  public SubmissionEntity findById(int id) {
    return store.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
  }
}
