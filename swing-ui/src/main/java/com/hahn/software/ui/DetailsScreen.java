package com.hahn.software.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DetailsScreen extends JFrame {
    public DetailsScreen(int ticketId, String title, String description, String category, String status, String priority, List<String> comments) {
        setTitle("Ticket Details - #" + ticketId);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(new JLabel("Title: " + title));
        panel.add(new JLabel("Description: " + description));
        panel.add(new JLabel("Category: " + category));
        panel.add(new JLabel("Status: " + status));
        panel.add(new JLabel("Priority: " + priority));


        JTextArea commentsArea = new JTextArea();
        commentsArea.setEditable(false);
        commentsArea.setText(String.join("\n", comments));

        JScrollPane scrollPane = new JScrollPane(commentsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Comments"));

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
