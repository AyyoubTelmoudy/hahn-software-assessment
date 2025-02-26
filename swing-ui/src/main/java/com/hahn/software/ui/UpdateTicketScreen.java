package com.hahn.software.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.hahn.software.constant.Constants;

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
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        titleField = new JTextField(ticketObj.get("title").getAsString());
        titleField.setEditable(false);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(categoryLabel, gbc);

        categoryField = new JTextField(ticketObj.get("category").getAsString());
        categoryField.setEditable(false);
        categoryField.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        panel.add(categoryField, gbc);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(statusLabel, gbc);

        String[] statuses = {"NEW", "IN_PROGRESS", "RESOLVED"};
        statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(ticketObj.get("status").getAsString().toUpperCase());
        statusComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(statusComboBox, gbc);

        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(priorityLabel, gbc);

        priorityField = new JTextField(ticketObj.get("priority").getAsString());
        priorityField.setEditable(false);
        priorityField.setFont(new Font("Arial", Font.PLAIN, 14));
        priorityField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        panel.add(priorityField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(descriptionLabel, gbc);

        descriptionArea = new JTextArea(ticketObj.get("description").getAsString(), 3, 20);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        JButton updateButton = new JButton("Update Status");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(updateButton, gbc);

        updateButton.addActionListener(e -> updateTicketStatus());

        add(panel);
        setVisible(true);
    }

    private void updateTicketStatus() {
        try {
            JsonObject updateData = new JsonObject();
            updateData.addProperty("ticketId", ticketId);
            updateData.addProperty("status", (String) statusComboBox.getSelectedItem());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Constants.BASE_URL+"/api/v1/it-support/change-ticket-status"))
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
