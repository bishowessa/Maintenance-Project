package com.lms.persistence.repositories;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import com.lms.persistence.entities.AssignmentSubmissionEntity;

@Repository
public class AssignmentSubmissionRepository {
    private final List<AssignmentSubmissionEntity> submissions = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public boolean add(AssignmentSubmissionEntity submission) {
        submission.setId(idGenerator.getAndIncrement()); 
        return submissions.add(submission);
    }

    public List<AssignmentSubmissionEntity> findAll() {
        return new ArrayList<>(submissions);
    }

    public AssignmentSubmissionEntity findById(int id) {
        return submissions.stream()
                .filter(submission -> submission.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean update(AssignmentSubmissionEntity updatedSubmission) {
        AssignmentSubmissionEntity existing = findById(updatedSubmission.getId());
        if (existing != null) {
            submissions.remove(existing);
            submissions.add(updatedSubmission);
            return true;
        }
        return false;
    }

    public int generateUniqueId() {
        return idGenerator.getAndIncrement();
    }

    public List<AssignmentSubmissionEntity> findByStudentAndCourse(String studentId, String courseId) {
    return submissions
        .stream()
        .filter(submission -> submission.getStudentId().equals(studentId) && submission.getCourseId().equals(courseId))
        .collect(Collectors.toList());
}
}
