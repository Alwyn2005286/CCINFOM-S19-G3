import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViolationUI extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ViolationDAO dao;
    private JTextField remarksField;
    private JComboBox<Integer> inspectionIdCombo, requirementCodeCombo;
    private JComboBox<String> statusCombo;

    public ViolationUI() {
        dao = new ViolationDAO();
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Violation Details"));

        inputPanel.add(new JLabel("Inspection ID:"));
        inspectionIdCombo = new JComboBox<>();
        inputPanel.add(inspectionIdCombo);

        inputPanel.add(new JLabel("Requirement Code:"));
        requirementCodeCombo = new JComboBox<>();
        inputPanel.add(requirementCodeCombo);

        inputPanel.add(new JLabel("Remarks:"));
        remarksField = new JTextField();
        inputPanel.add(remarksField);

        inputPanel.add(new JLabel("Status:"));
        statusCombo = new JComboBox<>(new String[]{"PASS", "FAIL"});
        inputPanel.add(statusCombo);

        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Inspection ID", "Req Code", "Remarks", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel ctrl = new JPanel();
        JButton addBtn = new JButton("Save");
        addBtn.addActionListener(e -> save());
        ctrl.add(addBtn);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> refreshData());
        ctrl.add(refreshButton);
        
        add(ctrl, BorderLayout.SOUTH);

        refreshData();
    }

    private void refreshData() {
        inspectionIdCombo.removeAllItems();
        for (Integer id : dao.getAvailableInspectionIds()) inspectionIdCombo.addItem(id);
        
        requirementCodeCombo.removeAllItems();
        for (Integer id : dao.getAvailableRequirementCodes()) requirementCodeCombo.addItem(id);
        
        loadTableData();
    }

    private void save() {
        int insId = (Integer) inspectionIdCombo.getSelectedItem();
        int reqCode = (Integer) requirementCodeCombo.getSelectedItem();
        String remarks = remarksField.getText();
        String status = (String) statusCombo.getSelectedItem();
        
        dao.addViolation(new Violation(0, insId, reqCode, remarks, status));
        loadTableData();
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Violation v : dao.getAllViolations()) {
            Object[] row = { v.getViolationId(), v.getInspectionId(), v.getRequirementCode(), v.getRemarks(), v.getStatus() };
            tableModel.addRow(row);
        }
    }
}