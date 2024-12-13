package com.lms.persistence.repositories;

import com.lms.persistence.entities.AttendanceEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AttendanceRepository {

  private final List<AttendanceEntity> store = new ArrayList<>();

  public boolean add(AttendanceEntity entity) {
    return store.add(entity);
  }

  public List<AttendanceEntity> findAll() {
    return new ArrayList<>(store);
  }

  public AttendanceEntity findById(int id) {
    return store.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
  }
}
