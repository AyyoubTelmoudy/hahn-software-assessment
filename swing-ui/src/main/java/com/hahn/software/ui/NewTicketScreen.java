package com.hahn.software.ui;

import com.google.gson.JsonObject;
import com.hahn.software.constant.Constants;

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
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        titleField = new JTextField(20);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(descriptionLabel, gbc);

        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);


        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(priorityLabel, gbc);

        String[] priorities = {"LOW", "MEDIUM", "HIGH"};
        priorityBox = new JComboBox<>(priorities);
        priorityBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(priorityBox, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(categoryLabel, gbc);

        String[] categories = {"NETWORK", "HARDWARE", "SOFTWARE", "OTHER"};
        categoryBox = new JComboBox<>(categories);
        categoryBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(categoryBox, gbc);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(e -> submitTicket());

        add(panel);
        setVisible(true);
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
                    .uri(URI.create(Constants.BASE_URL+"/api/v1/employee/create-ticket"))
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
