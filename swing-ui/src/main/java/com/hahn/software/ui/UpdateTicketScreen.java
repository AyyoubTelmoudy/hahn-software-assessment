package com.hahn.software.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;

public class UpdateTicketScreen extends JFrame {
    private JTextField titleField, categoryField, priorityField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusComboBox;
    private int ticketId;
    private HomeScreen homeScreen;

    public UpdateTicketScreen(int ticketId, JsonObject ticketObj, HomeScreen homeScreen) {
        this.ticketId = ticketId;
        this.homeScreen = homeScreen;

        setTitle("Update Ticket #" + ticketId);
        setSize(400, 350);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Title:"));
        titleField = new JTextField(ticketObj.get("title").getAsString());
        titleField.setEditable(false); // Title is not updated in this request
        add(titleField);

        add(new JLabel("Category:"));
        categoryField = new JTextField(ticketObj.get("category").getAsString());
        categoryField.setEditable(false); // Category is not updated in this request
        add(categoryField);

        add(new JLabel("Status:"));
        String[] statuses = {"NEW", "IN_PROGRESS", "RESOLVED"};  // Match enum values
        statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(ticketObj.get("status").getAsString().toUpperCase());
        add(statusComboBox);

        add(new JLabel("Priority:"));
        priorityField = new JTextField(ticketObj.get("priority").getAsString());
        priorityField.setEditable(false); // Priority is not updated in this request
        add(priorityField);

        add(new JLabel("Description:"));
        descriptionArea = new JTextArea(ticketObj.get("description").getAsString(), 3, 20);
        descriptionArea.setEditable(false); // Description is not updated in this request
        add(new JScrollPane(descriptionArea));

        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(e -> updateTicketStatus());
        add(updateButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void updateTicketStatus() {
        try {
            JsonObject updateData = new JsonObject();
            updateData.addProperty("ticketId", ticketId);
            updateData.addProperty("status", (String) statusComboBox.getSelectedItem()); // Send only status and ID

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9090/api/v1/it-support/change-ticket-status"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + LoginScreen.accessToken)
                    .POST(HttpRequest.BodyPublishers.ofString(updateData.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Ticket status updated successfully!");
                homeScreen.refreshTickets();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed! Error: " + response.statusCode() + "\n" + response.body());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating ticket status: " + ex.getMessage());
        }
    }
}
