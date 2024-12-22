package com.lms.presentation;

import com.lms.persistence.Enrollment;
import com.lms.persistence.User;
import com.lms.service.EnrollmentService;
import com.lms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testEnrollStudent_Success() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("student@example.com");
        User user = new User();

        user.setRole("Student");
        user.setId("S01");
        when(userService.findByEmail("student@example.com")).thenReturn(Optional.of(user));


        ResponseEntity<String> response = enrollmentController.enrollStudent("S01", "101");


        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Student enrolled successfully", response.getBody());
        verify(enrollmentService, times(1)).enrollStudent("S01", "101");
    }

    @Test
    void testEnrollStudent_UserNotFound() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("unknown@example.com");
        when(userService.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = enrollmentController.enrollStudent("1", "101");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(enrollmentService, never()).enrollStudent(anyString(), anyString());
    }

    @Test
    void testGetEnrollmentsByCourse() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("instructor@example.com");
        User user = new User();
        user.setRole("Instructor");
        user.setId("I01");
        when(userService.findByEmail("instructor@example.com")).thenReturn(Optional.of(user));
        Enrollment enrollment = new Enrollment("E1", "I01", "101");
        when(enrollmentService.getEnrollmentsByCourse("101")).thenReturn(List.of(enrollment));

        // Act
        ResponseEntity<?> response = enrollmentController.getEnrollmentsByCourse("101");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(enrollment), response.getBody());
    }
}
