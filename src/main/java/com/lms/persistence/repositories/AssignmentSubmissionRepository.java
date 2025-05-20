package com.lms.persistence.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.events.AssignmentSubmissionEvent;

@Repository
@Scope("singleton")
public class AssignmentSubmissionRepository {
    private final List<AssignmentSubmissionEntity> submissions = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final ApplicationEventPublisher eventPublisher;

    // Constructor injection for ApplicationEventPublisher
    public AssignmentSubmissionRepository(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public boolean add(AssignmentSubmissionEntity submission) {
        submission.setId(idGenerator.getAndIncrement());
        boolean added = submissions.add(submission);
        if (added) {
            // Publish event after successful submission
            eventPublisher.publishEvent(new AssignmentSubmissionEvent(this, submission));
        }
        return added;
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

    public boolean hasStudentSubmittedAssignment(String studentId, int assignmentId) {
        return submissions.stream()
                .anyMatch(submission -> submission.getStudentId().equals(studentId)
                        && submission.getAssignmentId() == assignmentId);
    }

    public boolean existsByStudentId(String studentId) {
        return submissions.stream()
                .anyMatch(submission -> submission.getStudentId().equals(studentId));
    }

    public int generateUniqueId() {
        return idGenerator.getAndIncrement();
    }

    public List<AssignmentSubmissionEntity> findByStudentAndCourse(String studentId, String courseId) {
        return submissions.stream()
                .filter(submission -> submission.getStudentId().equals(studentId) && submission.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public List<AssignmentSubmissionEntity> findByAssignmentSubmissionsByAssignmentId(int id) {
        return submissions.stream()
                .filter(submission -> submission.getAssignmentId() == id)
                .collect(Collectors.toList());
    }
}
