package com.lms.persistence.repositories;

import com.lms.persistence.entities.AssignmentEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AssignmentRepository {

  private final List<AssignmentEntity> store = new ArrayList<>();

  public boolean add(AssignmentEntity entity) {
    return store.add(entity);
  }

  public List<AssignmentEntity> findAll() {
    return new ArrayList<>(store);
  }

  public AssignmentEntity findById(int id) {
    return store.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
  }
}
