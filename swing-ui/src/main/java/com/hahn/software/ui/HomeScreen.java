package com.hahn.software.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.net.http.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import com.google.gson.*;
import com.hahn.software.constant.Constants;

public class HomeScreen extends JFrame {
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private HashMap<Integer, JsonObject> ticketDataMap = new HashMap<>();
    private JButton updateButton, addTicketButton, searchButton;
    private JComboBox<String> statusComboBox;
    private JTextField ticketIdField;
    private JButton logoutButton;
    private boolean isITSupportUser = false;
    private boolean isEmployeeUser = false;

    public HomeScreen() {
        setTitle("Home - Ticket List");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        checkUserRoles();

        String[] columns = {"ID", "Title", "Category", "Status", "Priority", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        ticketTable = new JTable(tableModel);
        ticketTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Status:"));

        String[] statusOptions = {"","NEW", "IN_PROGRESS", "RESOLVED"};
        statusComboBox = new JComboBox<>(statusOptions);
        searchPanel.add(statusComboBox);

        searchPanel.add(new JLabel("Ticket ID:"));
        ticketIdField = new JTextField(10);
        searchPanel.add(ticketIdField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton detailsButton = new JButton("View Details");
        addTicketButton = new JButton("New Ticket");
        updateButton = new JButton("Update Ticket");
        logoutButton = new JButton("Logout");  // 🔹 Create Logout Button

        buttonPanel.add(detailsButton);

        if (isEmployeeUser) {
            buttonPanel.add(addTicketButton);
        }
        if (isITSupportUser) {
            buttonPanel.add(updateButton);
        }

        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        fetchTickets(null, null);

        searchButton.addActionListener(e -> searchTickets());
        detailsButton.addActionListener(e -> showTicketDetails());
        addTicketButton.addActionListener(e -> new NewTicketScreen(this).setVisible(true));
        updateButton.addActionListener(e -> updateSelectedTicket());
        logoutButton.addActionListener(e -> logout());
        setVisible(true);
    }

    private void searchTickets() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        String ticketId = ticketIdField.getText().trim();
        String status = "All".equals(selectedStatus) ? null : selectedStatus;
        fetchTickets(status, ticketId.isEmpty() ? null : ticketId);
    }

    private void fetchTickets(String status, String ticketId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url;
            if (isITSupportUser) {
                url = Constants.BASE_URL+"/api/v1/it-support/all-ticket-list";
            } else if (isEmployeeUser) {
                url = Constants.BASE_URL+"/api/v1/employee/ticket-list";
            } else {
                JOptionPane.showMessageDialog(this, "Unauthorized user!");
                return;
            }
            StringBuilder apiUrl = new StringBuilder(url);
            if (status != null || ticketId != null) {
                apiUrl.append("?");
                if (status != null) {
                    apiUrl.append("status=").append(status);
                }
                if (ticketId != null) {
                    if (status != null) apiUrl.append("&");
                    apiUrl.append("id=").append(ticketId);
                }
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl.toString()))
                    .header("Authorization", "Bearer " + LoginScreen.accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

                if (jsonResponse.has("tickets") && jsonResponse.get("tickets").isJsonArray()) {
                    JsonArray tickets = jsonResponse.getAsJsonArray("tickets");

                    tableModel.setRowCount(0);
                    ticketDataMap.clear();

                    for (JsonElement ticket : tickets) {
                        JsonObject ticketObj = ticket.getAsJsonObject();
                        int ticketIdVal = ticketObj.get("id").getAsInt();
                        String title = ticketObj.has("title") ? ticketObj.get("title").getAsString() : "N/A";
                        String category = ticketObj.has("category") ? ticketObj.get("category").getAsString() : "N/A";
                        String statusVal = ticketObj.has("status") ? ticketObj.get("status").getAsString() : "N/A";
                        String priority = ticketObj.has("priority") ? ticketObj.get("priority").getAsString() : "N/A";
                        String description = ticketObj.has("description") ? ticketObj.get("description").getAsString() : "N/A";

                        tableModel.addRow(new Object[]{ticketIdVal, title, category, statusVal, priority, description});
                        ticketDataMap.put(ticketIdVal, ticketObj);
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
        int selectedIndex = ticketTable.getSelectedRow();
        if (selectedIndex != -1) {
            int ticketId = (int) tableModel.getValueAt(selectedIndex, 0);
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

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            LoginScreen.accessToken = null;
            new LoginScreen().setVisible(true);
            dispose();
        }
    }
    public void refreshTickets() {
        fetchTickets(null, null);
    }
}
