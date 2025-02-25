package com.hahn.software.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    public static String accessToken;  // 🔹 Store access token globally
    public static String userRole;     // 🔹 Store user role globally

    public LoginScreen() {
        setTitle("Login");
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> authenticateUser());
        add(loginButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void authenticateUser() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String loginUrl = "http://localhost:9090/api/v1/auth/login";
            String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(loginUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

                // 🔹 Store access token
                accessToken = jsonResponse.get("accessToken").getAsString();

                // 🔹 Extract user role (assuming the user has at least one role)
                if (jsonResponse.has("roles") && jsonResponse.get("roles").isJsonArray()) {
                    JsonArray rolesArray = jsonResponse.getAsJsonArray("roles");
                    if (!rolesArray.isEmpty()) {
                        userRole = rolesArray.get(0).getAsString();  // 🔹 Extract first role
                    } else {
                        userRole = "UNKNOWN_ROLE"; // Default if no role is found
                    }
                } else {
                    userRole = "UNKNOWN_ROLE";
                }

                JOptionPane.showMessageDialog(this, "Login successful! Role: " + userRole);
                this.dispose();
                new HomeScreen();  // Open HomeScreen after successful login
            } else {
                JOptionPane.showMessageDialog(this, "Login failed! Invalid credentials.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Login error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
