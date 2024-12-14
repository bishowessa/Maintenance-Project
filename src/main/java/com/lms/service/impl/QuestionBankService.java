package com.lms.service.impl;

import com.lms.persistence.entities.QuestionBank;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.repositories.QuestionBankRepository;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class QuestionBankService {

  private final QuestionBankRepository questionBankRepository;

  public QuestionBankService(QuestionBankRepository questionBankRepository) {
    this.questionBankRepository = questionBankRepository;
  }

  public void addQuestion(String courseId, Question question) {
    if (!question.validate()) {
      throw new IllegalArgumentException("Invalid question data.");
    }

    QuestionBank questionBank = questionBankRepository.findByCourseId(courseId);
    if (questionBank == null) {
        questionBank = new QuestionBank();
        questionBank.setCourseId(courseId);
        questionBank.setQuestions(new ArrayList<>());
        questionBankRepository.save(questionBank);
    }

    questionBank.addQuestion(question);
    questionBankRepository.save(questionBank);
  }

  public void deleteQuestion(String courseId, String questionId) {
    QuestionBank questionBank = questionBankRepository.findByCourseId(courseId);
    if (questionBank == null) {
      throw new IllegalArgumentException(
        "Question bank not found for the course."
      );
    }
    questionBank.deleteQuestion(questionId);
  }

  public List<Question> getQuestions(String courseId) {
    QuestionBank questionBank = questionBankRepository.findByCourseId(courseId);
    if (questionBank == null) {
      throw new IllegalArgumentException(
        "Question bank not found for the course."
      );
    }
    return questionBank.getQuestions();
  }
}
