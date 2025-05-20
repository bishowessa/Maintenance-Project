package com.lms.persistence.repositories;

import com.lms.persistence.entities.AssignmentSubmissionEntity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AssignmentSubmissionRepository {

    private final List<AssignmentSubmissionEntity> submissions = new CopyOnWriteArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public boolean add(AssignmentSubmissionEntity submission) {
        submission.setId(idGenerator.getAndIncrement());
        return submissions.add(submission);
    }

    public List<AssignmentSubmissionEntity> findAll() {
        return List.copyOf(submissions);
    }

    public AssignmentSubmissionEntity findById(int id) {
        return submissions.stream()
                .filter(submission -> submission.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean update(AssignmentSubmissionEntity updatedSubmission) {
        for (int i = 0; i < submissions.size(); i++) {
            if (submissions.get(i).getId() == updatedSubmission.getId()) {
                submissions.set(i, updatedSubmission);
                return true;
            }
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

    public List<AssignmentSubmissionEntity> findByStudentAndCourse(String studentId, String courseId) {
        return submissions.stream()
                .filter(submission -> submission.getStudentId().equals(studentId)
                        && submission.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public List<AssignmentSubmissionEntity> findByAssignmentId(int assignmentId) {
        return submissions.stream()
                .filter(submission -> submission.getAssignmentId() == assignmentId)
                .collect(Collectors.toList());
    }
}