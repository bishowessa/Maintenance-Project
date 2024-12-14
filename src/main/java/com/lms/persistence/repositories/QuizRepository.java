package com.lms.persistence.repositories;

import com.lms.persistence.entities.Quiz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class QuizRepository {

  private final Map<String, Quiz> quizzes = new HashMap<>();

  public void save(Quiz quiz) {
    quizzes.put(quiz.getId(), quiz);
  }

  public Quiz findById(String id) {
    return quizzes.get(id);
  }

  public List<Quiz> findAll() {
    return new ArrayList<>(quizzes.values());
  }
}
