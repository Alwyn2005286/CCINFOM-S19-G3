import javax.swing.*;
import java.awt.*;

public class RecordsManagementUI extends JFrame {

    public RecordsManagementUI() {
        setTitle("Records Management");
        setSize(1000, 700);
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

        btnEstablishment.addActionListener(e -> {
            new EstablishmentUI().setVisible(true);
            dispose();
        });

        // ===== PLACEHOLDERS =====

        btnInspector.addActionListener(e -> {
            new InspectorUI().setVisible(true);
            dispose();
        });

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

     // ================= INSPECTORS TABLE =================
    private JPanel createInspectionPanel() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Inspector ID");
        model.addColumn("Full Name");
        model.addColumn("Disctrict");
        model.addColumn("Active Status");

        try {
            List<Inspector> list = InspectorDAO.getAllInspectors();

            for (Inspector is : list) {
                model.addRow(new Object[]{
                        is.getInspectorId(),
                        is.getFullName(),
                        is.getDistrict(),
                        is.getActiveStatus()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
