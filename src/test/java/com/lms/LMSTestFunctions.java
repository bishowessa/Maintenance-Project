package com.lms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LMSTestFunctions {

  public static LMSResponse signup(
    String id,
    String firstName,
    String lastName,
    String email,
    String password,
    String role
  ) throws IOException {
    String url = "http://localhost:8080/auth/signup";
    OkHttpClient client = new OkHttpClient();

    String jsonData =
      "{\"id\":\"" +
      id +
      "\",\"firstName\":\"" +
      firstName +
      "\",\"lastName\":\"" +
      lastName +
      "\",\"email\":\"" +
      email +
      "\",\"password\":\"" +
      password +
      "\",\"role\":\"" +
      role +
      "\"}";

    RequestBody body = RequestBody.create(
      jsonData,
      okhttp3.MediaType.get("application/json; charset=utf-8")
    );
    Request request = new Request.Builder().url(url).post(body).build();

    Response response = client.newCall(request).execute();
    return new LMSResponse(response.code(), response.body().string());
  }

  public static LMSResponse login(String email, String password)
    throws IOException {
    String url = "http://localhost:8080/auth/login";
    OkHttpClient client = new OkHttpClient();

    String jsonData =
      "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

    RequestBody body = RequestBody.create(
      jsonData,
      okhttp3.MediaType.get("application/json; charset=utf-8")
    );
    Request request = new Request.Builder().url(url).post(body).build();

    Response response = client.newCall(request).execute();
    return new LMSResponse(response.code(), response.body().string());
  }

  public static String getToken(String body)
    throws JsonMappingException, JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode = mapper.readTree(body);
    return rootNode.get("token").asText();
  }

  public static LMSResponse createUser(
    String adminToken,
    String id,
    String firstName,
    String lastName,
    String email,
    String password,
    String role
  ) throws IOException {
    String url = "http://localhost:8080/admin/createUser";
    OkHttpClient client = new OkHttpClient();

    String jsonData =
      "{\"id\":\"" +
      id +
      "\",\"firstName\":\"" +
      firstName +
      "\",\"lastName\":\"" +
      lastName +
      "\",\"email\":\"" +
      email +
      "\",\"password\":\"" +
      password +
      "\",\"role\":\"" +
      role +
      "\"}";

    RequestBody body = RequestBody.create(
      jsonData,
      okhttp3.MediaType.get("application/json; charset=utf-8")
    );

    Request request = new Request.Builder()
      .url(url)
      .post(body)
      .header("Authorization", "Bearer " + adminToken)
      .build();

    Response response = client.newCall(request).execute();
    return new LMSResponse(response.code(), response.body().string());
  }
  // public static void main(String[] args) {
  //   try {
  //     String adminEmail = "admin@example.com";
  //     String adminPassword = "password123";

  //     JResponse signUpAdmin = signup(
  //       "A01",
  //       "John",
  //       "Doe",
  //       adminEmail,
  //       adminPassword,
  //       "Admin"
  //     );

  //     System.out.println(
  //       signUpAdmin.code == 200 ? "Admin created" : "Admin creation failed"
  //     );

  //     JResponse loginAdmin = login(adminEmail, adminPassword);
  //     String adminToken = getToken(loginAdmin.body);

  //     System.out.println("AdminToken: " + adminToken);

  //     String instructorEmail = "instructor@example.com";
  //     String instructorPassword = "password123";

  //     JResponse createUser = createUser(
  //       adminToken,
  //       "I01",
  //       "John",
  //       "Doe",
  //       instructorEmail,
  //       instructorPassword,
  //       "Instructor"
  //     );
  //     System.out.println("User created: " + createUser.code);

  //     JResponse loginInstructor = login(instructorEmail, instructorPassword);
  //     String instructorToken = getToken(loginInstructor.body);
  //     System.out.println("Authorization Bearer");
  //     System.out.println("InstructorToken: " + instructorToken);

  //     String studentEmail = "student@example.com";
  //     String studentPassword = "password123";

  //     JResponse createStudent = createUser(
  //       adminToken,
  //       "S01",
  //       "John",
  //       "Doe",
  //       studentEmail,
  //       studentPassword,
  //       "Student"
  //     );
  //     System.out.println("User created: " + createStudent.code);

  //     JResponse loginstudent = login(studentEmail, studentPassword);
  //     String studentToken = getToken(loginstudent.body);
  //     System.out.println("Authorization Bearer");
  //     System.out.println("StudentToken: " + studentToken);
  //   } catch (Exception e) {
  //     System.out.println(
  //       e.getMessage() + "\nRun the Learning Management System First"
  //     );
  //   }
  // }
}
