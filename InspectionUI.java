import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;


public class InspectionUI extends JFrame {

    private JTextField txtId, txtDate, txtScore, txtGrade, txtRemarks, txtEstId, txtAssignId, txtViolationId;
    private JTable table;
    private DefaultTableModel model;

    public InspectionUI() {
        setTitle("Inspection Management");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP PANEL (FORM) =====
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));

        txtId = new JTextField();
        txtDate = new JTextField("YYYY-MM-DD");
        txtScore = new JTextField();
        txtGrade = new JTextField();
        txtRemarks = new JTextField();
        txtEstId = new JTextField();
        txtAssignId = new JTextField();
        txtViolationId = new JTextField();

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        formPanel.add(txtDate);

        formPanel.add(new JLabel("Score:"));
        formPanel.add(txtScore);

        formPanel.add(new JLabel("Grade:"));
        formPanel.add(txtGrade);

        formPanel.add(new JLabel("Remarks:"));
        formPanel.add(txtRemarks);

        formPanel.add(new JLabel("Establishment ID:"));
        formPanel.add(txtEstId);

        formPanel.add(new JLabel("Assignment ID:"));
        formPanel.add(txtAssignId);

        formPanel.add(new JLabel("Violation ID:"));
        formPanel.add(txtViolationId);

        add(formPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Date");
        model.addColumn("Score");
        model.addColumn("Grade");
        model.addColumn("Remarks");

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnBack = new JButton("Back");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);


        add(buttonPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====

        btnAdd.addActionListener(e -> addInspection());
        btnUpdate.addActionListener(e -> updateInspection());
        btnDelete.addActionListener(e -> deleteInspection());
        btnRefresh.addActionListener(e -> loadTable());
        btnBack.addActionListener(e -> {this.dispose(); 
            new RecordsManagementUI().setVisible(true);
        });

        // Load data initially
        loadTable();

        // Table row click → populate fields
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtId.setText(model.getValueAt(row, 0).toString());
                txtDate.setText(model.getValueAt(row, 1).toString());
                txtScore.setText(model.getValueAt(row, 2).toString());
                txtGrade.setText(model.getValueAt(row, 3).toString());
                txtRemarks.setText(model.getValueAt(row, 4).toString());
            }
        });
    }

    // ===== LOAD TABLE =====
    private void loadTable() {
        try {
            model.setRowCount(0);
            List<Inspection> list = InspectionDAO.getAllInspections();

            for (Inspection i : list) {
                model.addRow(new Object[]{
                        i.getInspectionId(),
                        i.getInspectionDate(),
                        i.getScore(),
                        i.getGrade(),
                        i.getRemarks()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== ADD =====
    private void addInspection() {
        try {
            Inspection i = new Inspection(
                    0,
                    LocalDate.parse(txtDate.getText()),
                    Float.parseFloat(txtScore.getText()),
                    txtGrade.getText(),
                    txtRemarks.getText(),
                    Integer.parseInt(txtEstId.getText()),
                    Integer.parseInt(txtAssignId.getText()),
                    Integer.parseInt(txtViolationId.getText())
            );

            InspectionDAO.addInspection(i);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== UPDATE =====
    private void updateInspection() {
        try {
            Inspection i = new Inspection(
                    Integer.parseInt(txtId.getText()),
                    LocalDate.parse(txtDate.getText()),
                    Float.parseFloat(txtScore.getText()),
                    txtGrade.getText(),
                    txtRemarks.getText(),
                    Integer.parseInt(txtEstId.getText()),
                    Integer.parseInt(txtAssignId.getText()),
                    Integer.parseInt(txtViolationId.getText())
            );

            InspectionDAO.updateInspection(i);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== DELETE =====
    private void deleteInspection() {
        try {
            int id = Integer.parseInt(txtId.getText());
            InspectionDAO.deleteInspection(id);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InspectionUI().setVisible(true);
        });
    }
}