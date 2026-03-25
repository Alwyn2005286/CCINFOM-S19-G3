import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RecordsManagementUI extends JFrame {

    public RecordsManagementUI() {
        setTitle("Records Management (View Only)");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Inspections", createInspectionPanel());
        tabbedPane.add("Violations", createViolationPanel());

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            new MainMenuUI().setVisible(true);
            dispose();
        });

        add(tabbedPane, BorderLayout.CENTER);
        add(btnBack, BorderLayout.SOUTH);
    }

    // ================= INSPECTIONS TABLE =================
    private JPanel createInspectionPanel() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Inspection ID");
        model.addColumn("Date");
        model.addColumn("Score");
        model.addColumn("Grade");
        model.addColumn("Remarks");
        model.addColumn("Establishment ID");
        model.addColumn("Assignment ID");
        model.addColumn("Violation ID");

        try {
            List<Inspection> list = InspectionDAO.getAllInspections();

            for (Inspection i : list) {
                model.addRow(new Object[]{
                        i.getInspectionId(),
                        i.getInspectionDate(),
                        i.getScore(),
                        i.getGrade(),
                        i.getRemarks(),
                        i.getEstablishmentId(),
                        i.getAssignmentId(),
                        i.getViolationId()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ================= VIOLATIONS TABLE =================
    private JPanel createViolationPanel() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Violation ID");
        model.addColumn("Requirement Code");
        model.addColumn("Inspection ID");

        try {
            List<Violation> list = ViolationDAO.getAllViolations();

            for (Violation v : list) {
                model.addRow(new Object[]{
                        v.getViolationId(),
                        v.getRequirementCode(),
                        v.getInspectionId()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

     // ================= INSPECTORS TABLE =================
    private JPanel createViolationPanel() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Inspector ID");
        model.addColumn("Requirement Code");
        model.addColumn("Inspection ID");

        try {
            List<Violation> list = ViolationDAO.getAllViolations();

            for (Violation v : list) {
                model.addRow(new Object[]{
                        v.getViolationId(),
                        v.getRequirementCode(),
                        v.getInspectionId()
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
