import java.awt.*;
import java.sql.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InspectionUI extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private InspectionDAO dao;
    private JTextField dateField, scoreField, remarksField;
    private JComboBox<Integer> establishmentIdCombo;
    private JComboBox<String> gradeCombo;

    public InspectionUI() {
        dao = new InspectionDAO();
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        inputPanel.add(new JLabel("Est. ID:"));
        establishmentIdCombo = new JComboBox<>();
        inputPanel.add(establishmentIdCombo);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Score:"));
        scoreField = new JTextField();
        inputPanel.add(scoreField);

        inputPanel.add(new JLabel("Grade:"));
        gradeCombo = new JComboBox<>(new String[]{"PASS", "FAIL"});
        inputPanel.add(gradeCombo);

        inputPanel.add(new JLabel("Remarks:"));
        remarksField = new JTextField();
        inputPanel.add(remarksField);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Est. ID", "Date", "Score", "Grade", "Remarks"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> save());
        
        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> refreshData());

        JPanel controlPanel = new JPanel();
        controlPanel.add(saveBtn);
        controlPanel.add(refreshButton);
        add(controlPanel, BorderLayout.SOUTH);

        refreshData();
    }

    private void refreshData() {
        establishmentIdCombo.removeAllItems();
        for (Integer id : dao.getAvailableEstablishmentIds()) establishmentIdCombo.addItem(id);
        loadTableData();
    }

    private void save() {
        try {
            int estId = (Integer) establishmentIdCombo.getSelectedItem();
            Date d = Date.valueOf(dateField.getText());
            double s = Double.parseDouble(scoreField.getText());
            String g = (String) gradeCombo.getSelectedItem();
            String r = remarksField.getText();

            dao.addInspection(new Inspection(0, estId, d, s, g, r));
            loadTableData();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Inspection i : dao.getAllInspections()) {
            tableModel.addRow(new Object[]{i.getInspectionId(), i.getEstablishmentId(), i.getInspectionDate(), i.getScore(), i.getGrade(), i.getRemarks()});
        }
    }
}