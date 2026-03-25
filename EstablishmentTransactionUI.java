import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstablishmentTransactionUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtStatus;

    public EstablishmentTransactionUI() {
        setTitle("Establishment Transaction");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        txtStatus = new JTextField();

        topPanel.add(new JLabel("Operating Status (for filtering):"));
        topPanel.add(txtStatus);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));

        JButton btnViewAll = new JButton("View All");
        JButton btnFailed = new JButton("Failed Inspections Count");
        JButton btnFilter = new JButton("Filter by Status");
        JButton btnAdd = new JButton("Record Establishment");
        JButton btnRecords = new JButton("Open Establishment Records");
        JButton btnBack = new JButton("Back");

        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnFailed);
        buttonPanel.add(btnFilter);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRecords);
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        btnViewAll.addActionListener(e -> loadAll());
        btnFailed.addActionListener(e -> loadFailedCount());
        btnFilter.addActionListener(e -> filterByStatus());
        btnAdd.addActionListener(e -> recordEstablishment());

        btnRecords.addActionListener(e -> {
            new EstablishmentUI().setVisible(true);
            dispose();
        });

        btnBack.addActionListener(e -> {
            new TransactionsUI().setVisible(true);
            dispose();
        });

        // Initial load
        loadAll();
    }

    // ===== LOAD ALL =====
    private void loadAll() {
        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Owner");
            model.addColumn("Status");
            model.addColumn("Last Inspection");

            List<String[]> list = EstablishmentTransactionDAO.getEstablishmentsWithLastInspection();

            for (String[] row : list) {
                model.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== FAILED INSPECTIONS =====
    private void loadFailedCount() {
        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("Establishment");
            model.addColumn("Failed Inspections");

            List<String[]> list = EstablishmentTransactionDAO.getFailedInspectionsCount();

            for (String[] row : list) {
                model.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== FILTER BY STATUS =====
    private void filterByStatus() {
        try {
            String status = txtStatus.getText().toUpperCase().trim();

            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Owner");
            model.addColumn("Status");

            List<String[]> list = EstablishmentTransactionDAO.getEstablishmentsByStatus(status);

            for (String[] row : list) {
                model.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== RECORD ESTABLISHMENT =====
    private void recordEstablishment() {
        try {
            String name = JOptionPane.showInputDialog("Enter Establishment Name:");
            String owner = JOptionPane.showInputDialog("Enter Owner Name:");
            String address = JOptionPane.showInputDialog("Enter Address:");
            String contact = JOptionPane.showInputDialog("Enter Contact Info:");
            String status = JOptionPane.showInputDialog("Enter Operating Status (OPEN/CLOSED/SUSPENDED):");

            Establishment est = new Establishment(
                    0,
                    name,
                    owner,
                    address,
                    contact,
                    status.toUpperCase()
            );

            EstablishmentDAO.addEstablishment(est);

            JOptionPane.showMessageDialog(this, "Establishment Recorded!");
            loadAll();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EstablishmentTransactionUI().setVisible(true);
        });
    }
}
