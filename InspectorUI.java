import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InspectorUI extends JFrame {

    private JTextField txtId, txtName, txtDistrict, txtStatus;
    private JTable table;
    private DefaultTableModel model;

    public InspectorUI() {
        setTitle("Inspector Management");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        txtId = new JTextField();
        txtName = new JTextField();
        txtDistrict = new JTextField();
        txtStatus = new JTextField();

        formPanel.add(new JLabel("Inspector ID:"));
        formPanel.add(txtId);

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("District:"));
        formPanel.add(txtDistrict);

        formPanel.add(new JLabel("Status (ACTIVE/INACTIVE):"));
        formPanel.add(txtStatus);

        add(formPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("Inspector ID");
        model.addColumn("Full Name");
        model.addColumn("District");
        model.addColumn("Status");

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
        btnAdd.addActionListener(e -> addInspector());
        btnUpdate.addActionListener(e -> updateInspector());
        btnDelete.addActionListener(e -> deleteInspector());
        btnRefresh.addActionListener(e -> loadTable());
        btnBack.addActionListener(e -> {
            this.dispose();
            new RecordsManagementUI().setVisible(true); // same pattern as your classmate
        });

        // Load table initially
        loadTable();

        // ===== TABLE CLICK =====
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtDistrict.setText(model.getValueAt(row, 2).toString());
                txtStatus.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    // ===== LOAD TABLE =====
    private void loadTable() {
        try {
            model.setRowCount(0);

            List<Inspector> list = new InspectorDAO().getAllInspectors();

            for (Inspector i : list) {
                model.addRow(new Object[]{
                        i.getInspectorId(),
                        i.getFullName(),
                        i.getDistrict(),
                        i.getActiveStatus()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== ADD INSPECTOR =====
    private void addInspector() {
        try {
            Inspector i = new Inspector(
                    txtName.getText(),
                    txtDistrict.getText(),
                    txtStatus.getText()
            );

            new InspectorDAO().addInspector(i);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== UPDATE INSPECTOR =====
    private void updateInspector() {
        try {
            Inspector i = new Inspector(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    txtDistrict.getText(),
                    txtStatus.getText()
            );

            new InspectorDAO().updateInspector(i);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== DELETE INSPECTOR =====
    private void deleteInspector() {
        try {
            int id = Integer.parseInt(txtId.getText());

            new InspectorDAO().deleteInspector(id);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InspectorUI().setVisible(true);
        });
    }
}