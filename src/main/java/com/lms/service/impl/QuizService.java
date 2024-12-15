package com.lms.service.impl;

import com.lms.persistence.entities.QuestionBank;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.repositories.QuestionBankRepository;
import com.lms.persistence.repositories.QuizRepository;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

  private final QuizRepository quizRepository;
  private final QuestionBankRepository questionBankRepository;

  public QuizService(
    QuizRepository quizRepository,
    QuestionBankRepository questionBankRepository
  ) {
    this.quizRepository = quizRepository;
    this.questionBankRepository = questionBankRepository;
  }

  public Quiz createQuiz(
    String courseId,
    String name,
    int questionsNumber,
    int duration,
    String status
  ) {
    QuestionBank questionBank = questionBankRepository.findByCourseId(courseId);

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

    quizRepository.save(quiz);
    return quiz;
  }

  public void markQuizAsDeleted(String quizId) {
    quizRepository
      .findById(quizId)
      .ifPresent(quiz -> {
        quiz.setStatus("deleted");
        quizRepository.update(quiz);
      });
  }

  public void markQuizAsOpened(String quizId) {
    quizRepository
      .findById(quizId)
      .ifPresent(quiz -> {
        quiz.setStatus("opened");
        quizRepository.update(quiz);
      });
  }

  public List<Quiz> getAllQuizzes() {
    return quizRepository.findAll();
  }

  public Quiz getQuizById(String quizId) {
    return quizRepository.findById(quizId).orElse(null);
  }
}
