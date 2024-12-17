package com.lms;

import static com.lms.JosephTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {


  @Test
  void testSignupAdmin() throws IOException {
    String adminEmail = "admin@example.com";
    String adminPassword = "password123";

    JResponse signUpAdmin = signup(
      "A01",
      "John",
      "Doe",
      adminEmail,
      adminPassword,
      "Admin"
    );

    System.out.println(
      signUpAdmin.code == 200 ? "Admin created" : "Admin creation failed"
    );

    JResponse loginAdmin = login(adminEmail, adminPassword);
    String adminToken = getToken(loginAdmin.body);

    System.out.println("AdminToken: " + adminToken);
    // the prented text in the debug console
    assertEquals(200, loginAdmin.code);
  }

}
