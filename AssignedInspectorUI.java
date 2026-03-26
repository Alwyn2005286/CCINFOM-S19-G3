import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AssignedInspectorUI extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private AssignedInspectorDAO dao;
    private JComboBox<Integer> inspectionIdCombo, inspectorIdCombo;

    public AssignedInspectorUI() {
        dao = new AssignedInspectorDAO();
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Assign Inspector"));

        inputPanel.add(new JLabel("Inspection ID:"));
        inspectionIdCombo = new JComboBox<>();
        inputPanel.add(inspectionIdCombo);

        inputPanel.add(new JLabel("Inspector ID:"));
        inspectorIdCombo = new JComboBox<>();
        inputPanel.add(inspectorIdCombo);

        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Assignment ID", "Inspection ID", "Inspector ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel ctrl = new JPanel();
        JButton addBtn = new JButton("Save");
        addBtn.addActionListener(e -> save());
        ctrl.add(addBtn);
        
        JButton refreshBtn = new JButton("Refresh Data");
        refreshBtn.addActionListener(e -> refreshData());
        ctrl.add(refreshBtn);
        
        add(ctrl, BorderLayout.SOUTH);
        refreshData();
    }

    private void refreshData() {
        inspectionIdCombo.removeAllItems();
        for (Integer id : dao.getAvailableInspectionIds()) inspectionIdCombo.addItem(id);
        
        inspectorIdCombo.removeAllItems();
        for (Integer id : dao.getAvailableInspectorIds()) inspectorIdCombo.addItem(id);
        
        loadTableData();
    }

    private void save() {
        int insId = (Integer) inspectionIdCombo.getSelectedItem();
        int inspId = (Integer) inspectorIdCombo.getSelectedItem();
        dao.addAssignment(new AssignedInspector(0, insId, inspId));
        loadTableData();
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (AssignedInspector ai : dao.getAllAssignments()) {
            Object[] row = { ai.getAssignmentId(), ai.getInspectionId(), ai.getInspectorId() };
            tableModel.addRow(row);
        }
    }
}