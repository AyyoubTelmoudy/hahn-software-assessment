package com.hahn.software.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.http.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.*;

public class HomeScreen extends JFrame {
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private HashMap<Integer, JsonObject> ticketDataMap = new HashMap<>();
    private JButton updateButton, addTicketButton;
    private boolean isITSupportUser = false;
    private boolean isEmployeeUser = false;

    public HomeScreen() {
        setTitle("Home - Ticket List");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Extract user roles
        checkUserRoles();

        // Table Columns
        String[] columns = {"ID", "Title", "Category", "Status", "Priority", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        ticketTable = new JTable(tableModel);
        ticketTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton detailsButton = new JButton("View Details");
        addTicketButton = new JButton("New Ticket");
        updateButton = new JButton("Update Ticket");

        buttonPanel.add(detailsButton);

        if (isEmployeeUser) {
            buttonPanel.add(addTicketButton); // Only employees can add new tickets
        }
        if (isITSupportUser) {
            buttonPanel.add(updateButton); // Only IT Support can update tickets
        }

        add(buttonPanel, BorderLayout.SOUTH);

        // Fetch tickets from API
        fetchTickets();

        // Button Actions
        detailsButton.addActionListener(e -> showTicketDetails());
        addTicketButton.addActionListener(e -> new NewTicketScreen(this).setVisible(true));
        updateButton.addActionListener(e -> updateSelectedTicket());
    }

    private void fetchTickets() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9090/api/v1/it-support/all-ticket-list"))
                    .header("Authorization", "Bearer " + LoginScreen.accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

                if (jsonResponse.has("tickets") && jsonResponse.get("tickets").isJsonArray()) {
                    JsonArray tickets = jsonResponse.getAsJsonArray("tickets");

                    tableModel.setRowCount(0); // Clear table
                    ticketDataMap.clear(); // Clear previous data

                    for (JsonElement ticket : tickets) {
                        JsonObject ticketObj = ticket.getAsJsonObject();
                        int ticketId = ticketObj.get("id").getAsInt();
                        String title = ticketObj.has("title") ? ticketObj.get("title").getAsString() : "N/A";
                        String category = ticketObj.has("category") ? ticketObj.get("category").getAsString() : "N/A";
                        String status = ticketObj.has("status") ? ticketObj.get("status").getAsString() : "N/A";
                        String priority = ticketObj.has("priority") ? ticketObj.get("priority").getAsString() : "N/A";
                        String description = ticketObj.has("description") ? ticketObj.get("description").getAsString() : "N/A";

                        // Add row to table
                        tableModel.addRow(new Object[]{ticketId, title, category, status, priority, description});
                        ticketDataMap.put(ticketId, ticketObj); // Store full ticket details
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No tickets found!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch tickets! Error: " + response.statusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching tickets!");
        }
    }

    private void updateSelectedTicket() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow != -1) {
            int ticketId = (int) tableModel.getValueAt(selectedRow, 0);
            JsonObject ticketObj = ticketDataMap.get(ticketId);

            if (ticketObj != null) {
                new UpdateTicketScreen(ticketId, ticketObj, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error: Ticket details not found!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a ticket!");
        }
    }

    private void checkUserRoles() {
        try {
            if (LoginScreen.accessToken == null || LoginScreen.accessToken.isEmpty()) return;

            String[] parts = LoginScreen.accessToken.split("\\.");
            if (parts.length != 3) return;

            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            JsonObject jsonPayload = JsonParser.parseString(payload).getAsJsonObject();

            if (jsonPayload.has("roles") && jsonPayload.get("roles").isJsonArray()) {
                JsonArray roles = jsonPayload.getAsJsonArray("roles");

                for (JsonElement role : roles) {
                    String roleStr = role.getAsString();
                    if ("ROLE_IT_SUPPORT".equals(roleStr)) {
                        isITSupportUser = true;
                    }
                    if ("ROLE_EMPLOYEE".equals(roleStr)) {
                        isEmployeeUser = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showTicketDetails() {
        int selectedIndex = ticketTable.getSelectedRow(); // Get selected row index
        if (selectedIndex != -1) {
            int ticketId = (int) tableModel.getValueAt(selectedIndex, 0); // Get ticket ID from table
            JsonObject ticketObj = ticketDataMap.get(ticketId);

            if (ticketObj != null) {
                List<String> commentsList = new ArrayList<>();
                JsonArray commentsArray = ticketObj.getAsJsonArray("comments");

                for (JsonElement comment : commentsArray) {
                    commentsList.add(comment.getAsJsonObject().get("text").getAsString());
                }

                new DetailsScreen(
                        ticketId,
                        ticketObj.get("title").getAsString(),
                        ticketObj.get("description").getAsString(),
                        ticketObj.get("category").getAsString(),
                        ticketObj.get("status").getAsString(),
                        ticketObj.get("priority").getAsString(),
                        commentsList
                ).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error: Ticket details not found!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a ticket!");
        }
    }

    public void refreshTickets() {
        fetchTickets();
    }
}
