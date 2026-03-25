import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends JFrame {

    public MainMenuUI() {
        setTitle("Main Menu");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnRecords = new JButton("Records Management");
        JButton btnTransactions = new JButton("Transactions");
        JButton btnReports = new JButton("Reports");

        // Open Records Management
        btnRecords.addActionListener(e -> {
            new RecordsManagementUI().setVisible(true);
            dispose();
        });

        // Open Transactions Management
        btnTransactions.addActionListener(e -> {
            new TransactionsUI().setVisible(true);
            dispose();
        });

        // Placeholder: Reports
        btnReports.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Opened Reports");
        });

        setLayout(new GridLayout(3, 1, 10, 10));

        add(btnRecords);
        add(btnTransactions);
        add(btnReports);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenuUI().setVisible(true);
        });
    }
}