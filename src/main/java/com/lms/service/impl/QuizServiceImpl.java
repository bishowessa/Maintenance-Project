package com.lms.service.impl;

import com.lms.persistence.entities.QuestionBank;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.repositories.RepositoryFacade;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
class QuizServiceImpl {

  private final RepositoryFacade repository;

  public QuizServiceImpl(RepositoryFacade repository) {
    this.repository = repository;
  }

  public Quiz createQuiz(
    String courseId,
    String name,
    int questionsNumber,
    int duration,
    String status
  ) {
    QuestionBank questionBank = repository.findQuestionBankByCourseId(courseId);

    if (questionBank == null) {
      throw new IllegalArgumentException(
        "Question bank not found for the course."
      );
    }

    List<Question> availableQuestions = questionBank.getQuestions();
    if (availableQuestions.size() < questionsNumber) {
      throw new IllegalArgumentException(
        "Not enough questions in the question bank to create the quiz."
      );
    }

    Collections.shuffle(availableQuestions);
    List<Question> selectedQuestions = availableQuestions.subList(
      0,
      questionsNumber
    );

    Quiz quiz = new Quiz(
      UUID.randomUUID().toString(),
      courseId,
      name,
      questionsNumber,
      duration,
      status,
      selectedQuestions
    );

    repository.saveQuiz(quiz);
    return quiz;
  }

  public void markQuizAsDeleted(String quizId) {
    repository
      .findQuizById(quizId)
      .ifPresent(quiz -> {
        quiz.setStatus("deleted");
        repository.updateQuiz(quiz);
      });
  }

  public void markQuizAsOpened(String quizId) {
    repository
      .findQuizById(quizId)
      .ifPresent(quiz -> {
        quiz.setStatus("opened");
        repository.updateQuiz(quiz);
      });
  }

  public List<Quiz> getAllQuizzes() {
    return repository.findAllQuizzes();
  }

  public Quiz getQuizById(String quizId) {
    return repository.findQuizById(quizId).orElse(null);
  }
}
