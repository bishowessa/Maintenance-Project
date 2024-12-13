package com.lms.web;

import com.lms.business.models.QuizModel;
import com.lms.persistence.entities.QuizEntity;
import com.lms.service.impl.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/{courseId}/quizzes")
class QuizController {
    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public String createQuiz(@PathVariable int courseId, @RequestBody QuizModel model) {
        if (service.createQuiz(model, courseId)) {
            return "Quiz created successfully.";
        } else {
            return "Failed to create quiz.";
        }
    }

    @GetMapping
    public List<QuizEntity> getQuizzesByCourse(@PathVariable("courseId") int courseId) {
        return service.getQuizzesByCourse(courseId);
    }
}
