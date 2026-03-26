import javax.swing.*;
import java.awt.*;

public class ReportsUI extends JFrame {

    public ReportsUI() {
        setTitle("Reports Menu");
        setSize(1000, 700); // match MainMenuUI
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 20, 20)); // 5 rows: 4 reports + back

        // ===== REPORT BUTTONS =====
        JButton btnInspectionReport = new JButton("Inspection Report");
        JButton btnViolationFinesReport = new JButton("Violation and Fines Report");
        JButton btnComplianceTrendReport = new JButton("Establishment Compliance Trend Report");
        JButton btnInspectorActivityReport = new JButton("Inspector Activity Report");
        JButton btnBack = new JButton("Back");

        // ===== ACTIONS =====

        btnInspectorActivityReport.addActionListener(e -> {
            new InspectorActivityUI().setVisible(true); 
            dispose();
        });

        // placeholders 

        btnInspectionReport.addActionListener(e -> {
            new InspectionReportUI().setVisible(true);
            dispose();
        });

        btnViolationFinesReport.addActionListener(e -> {
            
            JOptionPane.showMessageDialog(this, "Violation & Fines Report (not implemented yet)");
        });

        btnComplianceTrendReport.addActionListener(e -> {
            
            JOptionPane.showMessageDialog(this, "Compliance Trend Report (not implemented yet)");
        });

        

        btnBack.addActionListener(e -> {
            new MainMenuUI().setVisible(true);
            dispose();
        });

        // ===== ADD TO LAYOUT =====
        add(btnInspectionReport);
        add(btnViolationFinesReport);
        add(btnComplianceTrendReport);
        add(btnInspectorActivityReport);
        add(btnBack);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportsUI().setVisible(true));
    }
}
