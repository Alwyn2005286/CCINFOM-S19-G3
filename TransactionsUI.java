import javax.swing.*;
import java.awt.*;

public class TransactionsUI extends JFrame {

    public TransactionsUI() {
        setTitle("Transactions Management");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnEstablishment = new JButton("Establishment");
        JButton btnInspector = new JButton("Inspector ");
        JButton btnRequirement = new JButton("Inspection Requirement");
        JButton btnViolation = new JButton("Violation");
        JButton btnInspection = new JButton("Inspection");
        JButton btnBack = new JButton("Back");

        // Open Inspection Management UI

        btnInspection.addActionListener(e -> {
            new InspectionUI().setVisible(true);
            dispose();
        });

        // Open Violation Management UI

        btnViolation.addActionListener(e -> {
            new ViolationUI().setVisible(true);
            dispose();
        });

        // ===== PLACEHOLDERS =====

        btnEstablishment.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Establishment  (not implemented yet)")
        );

        btnInspector.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Inspector  (not implemented yet)")
        );

        btnRequirement.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Inspection Requirement  (not implemented yet)")
        );

        // ===== BACK =====

        btnBack.addActionListener(e -> {
            new MainMenuUI().setVisible(true);
            dispose();
        });

        // ===== LAYOUT =====
        setLayout(new GridLayout(6, 1, 10, 10));

        add(btnEstablishment);
        add(btnInspector);
        add(btnRequirement);
        add(btnViolation);
        add(btnInspection);
        add(btnBack);
    }
}