package com.hahn.software.ui;

import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class NewTicketScreen extends JFrame {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> priorityBox, categoryBox;
    private JButton submitButton;
    private HomeScreen homeScreen;

    public NewTicketScreen(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;

        setTitle("Create New Ticket");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        add(new JScrollPane(descriptionArea));

        add(new JLabel("Priority:"));
        String[] priorities = {"LOW", "MEDIUM", "HIGH"};
        priorityBox = new JComboBox<>(priorities);
        add(priorityBox);

        add(new JLabel("Category:"));
        String[] categories = {"NETWORK", "HARDWARE", "SOFTWARE", "OTHER"};
        categoryBox = new JComboBox<>(categories);
        add(categoryBox);

        submitButton = new JButton("Submit");
        add(submitButton);

        submitButton.addActionListener(e -> submitTicket());
    }

    private void submitTicket() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            JsonObject ticketData = new JsonObject();

            ticketData.addProperty("title", titleField.getText());
            ticketData.addProperty("description", descriptionArea.getText());
            ticketData.addProperty("priority", priorityBox.getSelectedItem().toString());
            ticketData.addProperty("category", categoryBox.getSelectedItem().toString());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9090/api/v1/employee/create-ticket"))
                    .header("Authorization", "Bearer " + LoginScreen.accessToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(ticketData.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Ticket created successfully!");
                homeScreen.refreshTickets();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create ticket!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
