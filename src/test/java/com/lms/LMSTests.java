package com.lms;

import static com.lms.LMSTestFunctions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LMSTests {

  static String adminEmail = "admin@example.com";
  static String adminPassword = "password123";

  static String instructorEmail = "instructor@example.com";
  static String instructorPassword = "password123";

  static String studentEmail = "jojo.1922005@gmail.com";
  static String studentPassword = "password123";

  static String adminToken;
  static String instructorToken;
  static String studentToken;

  static Boolean instructorCreated = false;
  static Boolean studentCreated = false;

  @Test
  void testSignupAdmin() throws IOException {
    if (adminToken == null) {
      LMSResponse signUpAdmin = signup(
        "A01",
        "John",
        "Doe",
        adminEmail,
        adminPassword,
        "Admin"
      );

      // System.out.println(
      //   signUpAdmin.code == 200 ? "Admin created" : "Admin creation failed"
      // );

      LMSResponse loginAdmin = login(adminEmail, adminPassword);
      adminToken = getToken(loginAdmin.body);

      System.out.println("AdminToken: \n" + "Bearer " + adminToken);
      // the prented text in the debug console
      assertEquals(200, loginAdmin.code);
    }
  }

  @Test
  void testCreateInstructor() throws IOException {
    if (adminToken == null) {
      testSignupAdmin();
    }

    LMSResponse createInstructor = createUser(
      adminToken,
      "I01",
      "John",
      "Doe",
      instructorEmail,
      instructorPassword,
      "Instructor"
    );

    System.out.println("Instructor created: " + createInstructor.code);
    instructorCreated = true;

    assertEquals(200, createInstructor.code);
  }

  @Test
  void testLoginInstructor() throws IOException {
    if (instructorCreated == false) {
      testCreateInstructor();
    }

    LMSResponse loginInstructor = login(instructorEmail, instructorPassword);
    instructorToken = getToken(loginInstructor.body);

    System.out.println("InstructorToken: \n" + "Bearer " + instructorToken);
    assertEquals(200, loginInstructor.code);
  }

  @Test
  void testCreateStudent() throws IOException {
    if (adminToken == null) {
      testSignupAdmin();
    }

    LMSResponse createStudent = createUser(
      adminToken,
      "S01",
      "John",
      "Doe",
      studentEmail,
      studentPassword,
      "Student"
    );

    System.out.println("Student created: " + createStudent.code);
    studentCreated = true;
    assertEquals(200, createStudent.code);
  }

  @Test
  void testLoginStudent() throws IOException {
    if (studentCreated == false) {
      testCreateStudent();
    }

    LMSResponse loginStudent = login(studentEmail, studentPassword);
    studentToken = getToken(loginStudent.body);

    System.out.println("StudentToken: \n" + "Bearer " + studentToken);
    assertEquals(200, loginStudent.code);
  }
}
