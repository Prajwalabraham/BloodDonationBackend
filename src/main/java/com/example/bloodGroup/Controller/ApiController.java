package com.example.bloodGroup.Controller;

import com.example.bloodGroup.Models.Admin;
import com.example.bloodGroup.Models.Donors;
import com.example.bloodGroup.Models.Requests;
import com.example.bloodGroup.Models.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hi")
    public String hello() {
        String hello = "Hello";
        return hello;
    }
    private static final String USERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Nisarga's Project\\bloodGroup\\src\\main\\java\\com\\example\\bloodGroup\\JSONFiles\\users.json";
    private static final String DONORS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Nisarga's Project\\bloodGroup\\src\\main\\java\\com\\example\\bloodGroup\\JSONFiles\\donors.json";
    private static final String REQUEST_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Nisarga's Project\\bloodGroup\\src\\main\\java\\com\\example\\bloodGroup\\JSONFiles\\requests.json";
    private static final String ADMIN_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Nisarga's Project\\bloodGroup\\src\\main\\java\\com\\example\\bloodGroup\\JSONFiles\\admins.json";

    //---------------------------------------------Login and Signup APIs Start-------------------------------------------//
    private final List<Users> users = new ArrayList<>();

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody Users newUser) {
        try {
            // Load existing users from the JSON file
            loadUsers();

            // Set the ID for the new user
            Long nextId = getNextUserId();
            newUser.setId(nextId);

            // Add the new user to the list
            users.add(newUser);

            // Save the updated user list to the JSON file
            saveUsers();

            // Create a response payload with the userID and username
            // You can customize the response structure as per your requirements
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userId", newUser.getId());
            responseBody.put("username", newUser.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during signup");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users loginUser) {
        try {
            // Load existing users from the JSON file
            loadUsers();

            // Find the user with matching username and password
            for (Users user : users) {
                if (user.getUsername().equals(loginUser.getUsername()) && user.getPassword().equals(Users.hashPassword(loginUser.getPassword()))) {
                    // Create a response payload with the userID and username
                    // You can customize the response structure as per your requirements
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("userId", user.getId());
                    responseBody.put("username", user.getUsername());

                    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during login");
        }
    }
    // Helper method to load users from the JSON file
    private void loadUsers() throws IOException {
        File file = new File(USERS_FILE_PATH);

        // If the file exists, load the users
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            users.clear();
            users.addAll(mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Users.class)));
        }
    }

    // Helper method to save users to the JSON file
    private void saveUsers() throws IOException {
        File file = new File(USERS_FILE_PATH);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, users);
    }

    // Helper method to generate the next available user ID
    private Long getNextUserId() {
        Long maxId = 0L;
        for (Users user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }

//---------------------------------------------Login and Signup APIs Finish-------------------------------------------//




//---------------------------------------------API for Making requests-------------------------------------------------//

    private final List<Requests> requests = new ArrayList<>();

    @PostMapping("/makerequest")
    public ResponseEntity<Object> makeRequest(@RequestBody Requests newRequest) {
        try {
            // Load existing users from the JSON file
            loadRequests();

            // Set the ID for the new user
            Long nextId = getNextRequestId();
            newRequest.setId(nextId);

            // Add the new user to the list
            requests.add(newRequest);

            // Save the updated user list to the JSON file
            saveRequest();


            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during signup");
        }
    }

    private void loadRequests() throws IOException {
        File file = new File(REQUEST_FILE_PATH);

        // If the file exists, load the users
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            requests.clear();
            requests.addAll(mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Requests.class)));
        }
    }

    private void saveRequest() throws IOException {
        File file = new File(REQUEST_FILE_PATH);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, requests);
    }
    private Long getNextRequestId() {
        Long maxId = 0L;
        for (Requests request : requests) {
            if (request.getId() > maxId) {
                maxId = request.getId();
            }
        }
        return maxId + 1;
    }



    @GetMapping("/getrequests")
    public ResponseEntity<List<Requests>> getAllRequests() {
        try {
            // Load existing requests from the JSON file
            loadRequests();

            return ResponseEntity.status(HttpStatus.OK).body(requests);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/requests/{id}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable Long id) {
        try {
            // Load existing requests from the JSON file
            loadRequests();

            // Find the request with the matching ID
            for (Requests request : requests) {
                if (request.getId().equals(id)) {
                    // Update the status to "accepted"
                    request.setStatus("accepted");

                    // Save the updated requests to the JSON file
                    saveRequest();

                    return ResponseEntity.status(HttpStatus.OK).body("Request accepted!");
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while accepting the request");
        }
    }

    //------------------------------------------Make Request Finish-------------------------------------------------//

    //------------------------------------------APIs for Donors-----------------------------------------------------//


    private final List<Donors> donors = new ArrayList<>();

    @PostMapping("/createdonor")
    public ResponseEntity<Object> createdonors(@RequestBody Donors newRequest) {
        try {
            // Load existing users from the JSON file
            loadDonors();

            // Set the ID for the new user
            Long nextId = getNextDonorId();
            newRequest.setId(nextId);

            // Add the new user to the list
            donors.add(newRequest);

            // Save the updated user list to the JSON file
            saveDonor();


            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during signup");
        }
    }

    private void loadDonors() throws IOException {
        File file = new File(DONORS_FILE_PATH);

        // If the file exists, load the users
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            donors.clear();
            donors.addAll(mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Donors.class)));
        }
    }

    private void saveDonor() throws IOException {
        File file = new File(DONORS_FILE_PATH);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, donors);
    }
    private Long getNextDonorId() {
        Long maxId = 0L;
        for (Donors request : donors) {
            if (request.getId() > maxId) {
                maxId = request.getId();
            }
        }
        return maxId + 1;
    }



    @GetMapping("/getdonors")
    public ResponseEntity<List<Donors>> getAllDonors() {
        try {
            // Load existing donors from the JSON file
            loadDonors();

            return ResponseEntity.status(HttpStatus.OK).body(donors);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//------------------------------------------------Donors APIs Finished------------------------------------------------//


//-----------------------------------------------Admin APIs Start-----------------------------------------------------//


    private final List<Admin> adminUsers = new ArrayList<>();

    @PostMapping("/adminsignup")
    public ResponseEntity<Object> signUp(@RequestBody Admin newAdmin) {
        try {
            // Load existing users from the JSON file
            loadAdmins();

            // Set the ID for the new user
            Long nextId = getNextAdminId();
            newAdmin.setId(nextId);

            // Add the new user to the list
            adminUsers.add(newAdmin);

            // Save the updated user list to the JSON file
            saveAdmins();

            // Create a response payload with the userID and username
            // You can customize the response structure as per your requirements
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userId", newAdmin.getId());
            responseBody.put("username", newAdmin.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during signup");
        }
    }

    @PostMapping("/adminlogin")
    public ResponseEntity<Object> login(@RequestBody Admin loginAdmin) {
        try {
            // Load existing users from the JSON file
            loadAdmins();

            // Find the user with matching username and password
            for (Admin admin : adminUsers) {
                if (admin.getUsername().equals(loginAdmin.getUsername()) && admin.getPassword().equals(Users.hashPassword(loginAdmin.getPassword()))) {
                    // Create a response payload with the userID and username
                    // You can customize the response structure as per your requirements
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("userId", admin.getId());
                    responseBody.put("username", admin.getUsername());

                    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during login");
        }
    }
    // Helper method to load users from the JSON file
    private void loadAdmins() throws IOException {
        File file = new File(ADMIN_FILE_PATH);

        // If the file exists, load the users
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            adminUsers.clear();
            adminUsers.addAll(mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Users.class)));
        }
    }

    // Helper method to save users to the JSON file
    private void saveAdmins() throws IOException {
        File file = new File(ADMIN_FILE_PATH);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, adminUsers);
    }

    // Helper method to generate the next available user ID
    private Long getNextAdminId() {
        Long maxId = 0L;
        for (Admin admin : adminUsers) {
            if (admin.getId() > maxId) {
                maxId = admin.getId();
            }
        }
        return maxId + 1;
    }



}
