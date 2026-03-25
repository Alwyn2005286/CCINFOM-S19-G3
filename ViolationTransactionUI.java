import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViolationTransactionUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtInspectionId;

    public ViolationTransactionUI() {
        setTitle("Violation Transaction");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        txtInspectionId = new JTextField();

        topPanel.add(new JLabel("Inspection ID (for filtering):"));
        topPanel.add(txtInspectionId);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));

        JButton btnViewAll = new JButton("View All");
        JButton btnRepeated = new JButton("Repeated Violations");
        JButton btnFilter = new JButton("Filter by Inspection");
        JButton btnAdd = new JButton("Record Violation");
        JButton btnRecords = new JButton("Open Violation Records");
        JButton btnBack = new JButton("Back");

        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnRepeated);
        buttonPanel.add(btnFilter);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRecords);
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== ACTIONS =====

        btnViewAll.addActionListener(e -> loadAll());
        btnRepeated.addActionListener(e -> loadRepeated());
        btnFilter.addActionListener(e -> filterByInspection());
        btnAdd.addActionListener(e -> recordViolation());

        btnRecords.addActionListener(e -> {
            new ViolationUI().setVisible(true);
            dispose();
        });

        btnBack.addActionListener(e -> {
            new TransactionsUI().setVisible(true);
            dispose();
        });

        // Initial load
        loadAll();
    }

    // ===== LOAD ALL (JOIN) =====
    private void loadAll() {
        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("Violation ID");
            model.addColumn("Requirement Code");
            model.addColumn("Inspection ID");
            model.addColumn("Establishment");

            List<String[]> list = ViolationTransactionDAO.getViolationsWithEstablishment();

            for (String[] row : list) {
                model.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== REPEATED VIOLATIONS PER ESTABLISHMENT =====
    private void loadRepeated() {
        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("Establishment");
            model.addColumn("Total Violations");

            List<String[]> list = ViolationTransactionDAO.getRepeatedViolations();

            for (String[] row : list) {
                model.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== FILTER BY INSPECTION =====
    private void filterByInspection() {
        try {
            int id = Integer.parseInt(txtInspectionId.getText());

            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("Violation ID");
            model.addColumn("Requirement Code");
            model.addColumn("Inspection ID");

            List<String[]> list = ViolationTransactionDAO.getViolationsByInspection(id);

            for (String[] row : list) {
                model.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== RECORD VIOLATION =====
    private void recordViolation() {
        try {
            String req = JOptionPane.showInputDialog("Enter Requirement Code:");
            String ins = JOptionPane.showInputDialog("Enter Inspection ID:");

            Violation v = new Violation(
                    0,
                    Integer.parseInt(req),
                    Integer.parseInt(ins)
            );

            ViolationDAO.addViolation(v);

            JOptionPane.showMessageDialog(this, "Violation Recorded!");
            loadAll();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViolationTransactionUI().setVisible(true);
        });
    }
}