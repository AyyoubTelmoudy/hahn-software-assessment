package com.hahn.software.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.hahn.software.model.Ticket;
import com.hahn.software.service.TicketService;

public class TicketUI extends JFrame {
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public TicketUI() {
        setTitle("Ticket Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Define the table columns
        String[] columnNames = {"ID", "Title", "Description", "Category", "Status", "Priority"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);

        // Add table to UI
        add(new JScrollPane(ticketTable), BorderLayout.CENTER);

        // Add refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> fetchTickets());
        add(refreshButton, BorderLayout.SOUTH);

        fetchTickets(); // Load initial data
    }

    public void loadTickets(List<Ticket> ticketList) {
        tableModel.setRowCount(0); // Clear table

        for (Ticket ticket : ticketList) {
            tableModel.addRow(new Object[]{
                    ticket.getId(),
                    ticket.getTitle(),
                    ticket.getDescription(),
                    ticket.getCategory(),
                    ticket.getStatus(),
                    ticket.getPriority()
            });
        }
    }

    public void fetchTickets() {
        List<Ticket> tickets = TicketService.getAllTickets(); // Fetch from API
        loadTickets(tickets);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicketUI().setVisible(true));
    }
}
