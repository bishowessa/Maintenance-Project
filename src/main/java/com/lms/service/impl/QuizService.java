package com.lms.service.impl;
import com.lms.business.models.QuizModel;
import com.lms.persistence.entities.QuizEntity;
import com.lms.persistence.repositories.QuizRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

  private final QuizRepository repository;

  public QuizService(QuizRepository repository) {
    this.repository = repository;
  }

  public boolean createQuiz(QuizModel model, int courseId) {
    QuizEntity entity = new QuizEntity();
    entity.setId(model.getId());
    entity.setQuestions(model.getQuestions());
    entity.setGrade(model.getGrade());
    entity.setCourseId(courseId);
    return repository.add(entity);
  }

  public List<QuizEntity> getQuizzesByCourse(int courseId) {
    return repository
      .findAll()
      .stream()
      .filter(q -> q.getCourseId() == courseId)
      .collect(Collectors.toList());
  }
}
