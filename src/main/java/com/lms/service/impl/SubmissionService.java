package com.lms.service.impl;
import com.lms.business.models.SubmissionModel;
import com.lms.persistence.entities.SubmissionEntity;
import com.lms.persistence.repositories.SubmissionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionService {
    private final SubmissionRepository repository;

    public SubmissionService(SubmissionRepository repository) {
        this.repository = repository;
    }

    public boolean submitAssignment(SubmissionModel model, int assignmentId, int studentId) {
        SubmissionEntity entity = new SubmissionEntity();
        entity.setId(model.getId());
        entity.setStudentId(studentId);
        entity.setScore(model.getScore());
        entity.setFileURL(model.getFileURL());
        entity.setFeedback(model.getFeedback());
        entity.setRelatedId(assignmentId);
        return repository.add(entity);
    }

    public List<SubmissionEntity> getSubmissionsByAssignment(int assignmentId) {
        return repository.findAll().stream()
                .filter(s -> s.getRelatedId() == assignmentId)
                .collect(Collectors.toList());
    }
}
