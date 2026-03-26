import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

public class EstablishmentComplianceTrendReportUI extends JFrame {
    private JComboBox<Integer> yearComboBox;
    private JButton generateButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private EstablishmentComplianceTrendReportDAO dao;

    public EstablishmentComplianceTrendReportUI() {
        dao = new EstablishmentComplianceTrendReportDAO();
        setTitle("Establishment Compliance Trend Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Year:"));
        yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 2000; i--) {
            yearComboBox.addItem(i);
        }
        topPanel.add(yearComboBox);

        generateButton = new JButton("Generate Report");
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Establishment Name", "Passed Inspections", "Failed Inspections"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedYear = (Integer) yearComboBox.getSelectedItem();
                List<String[]> data = dao.getComplianceTrend(selectedYear);
                tableModel.setRowCount(0); // Clear existing data
                for (String[] row : data) {
                    tableModel.addRow(row);
                }
            }
        });
    }
}
