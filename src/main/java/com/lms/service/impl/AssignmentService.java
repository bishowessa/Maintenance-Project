package com.lms.service.impl;
import com.lms.business.models.AssignmentModel;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.repositories.AssignmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    private final AssignmentRepository repository;

    public AssignmentService(AssignmentRepository repository) {
        this.repository = repository;
    }

    public boolean createAssignment(AssignmentModel model, String courseId) {
        AssignmentEntity entity = new AssignmentEntity(0, model.getTitle(), courseId ,model.getDescription(),  model.getGrade(), model.getDueDate(), model.getStatus());
        return repository.add(entity);
    }

    public boolean deleteAssignment(int id, String courseId) {
        AssignmentEntity entity = repository.findById(id);
        if (entity != null && entity.getCourseId().equals(courseId)) {
            entity.setStatus("Deleted");
            return true;
        }
        return false;
    }

    public boolean editAssignment(int id, String courseId, AssignmentModel model) {
        AssignmentEntity entity = repository.findById(id);
    
        if (entity != null && entity.getCourseId().equals(courseId)) {
            if (model.getTitle() != null) {
                entity.setTitle(model.getTitle());
            }
            if (model.getDescription() != null) {
                entity.setDescription(model.getDescription());
            }
            if (model.getGrade() != 0) {  
                entity.setGrade(model.getGrade());
            }
            if (model.getDueDate() != null) {
                entity.setDueDate(model.getDueDate());
            }
            if (model.getStatus() != null) {
                entity.setStatus(model.getStatus());
            }
            
            return true;
        }
        return false; 
    }
    

    public List<AssignmentEntity> getAssignmentsByCourse(String courseId) {
        return repository.findAll().stream()
                .filter(a -> a.getCourseId().equals(courseId)) 
                .collect(Collectors.toList());
    }
}
