import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class InspectionReportUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtYear, txtMonth;

    public InspectionReportUI() {
        setTitle("Inspection Report");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        txtYear = new JTextField();
        txtMonth = new JTextField();

        topPanel.add(new JLabel("Year:"));
        topPanel.add(txtYear);
        topPanel.add(new JLabel("Month (1-12):"));
        topPanel.add(txtMonth);

        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton btnGenerate = new JButton("Generate Report");
        JButton btnBack = new JButton("Back");

        buttonPanel.add(btnGenerate);
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        btnGenerate.addActionListener(e -> generateReport());

        btnBack.addActionListener(e -> {
            new ReportsUI().setVisible(true);
            dispose();
        });
    }

    private void generateReport() {
        String yearText = txtYear.getText().trim();
        String monthText = txtMonth.getText().trim();

        if (yearText.isEmpty() || monthText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Year and Month.");
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            int month = Integer.parseInt(monthText);

            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("Establishment");
            model.addColumn("Total Inspections");
            model.addColumn("Average Score");

            String sql = "SELECT e.Establishment_Name AS Establishment, " +
                         "COUNT(i.Inspection_Id) AS total_inspections, " +
                         "AVG(i.Score) AS avg_score " +
                         "FROM inspection i " +
                         "JOIN establishment e ON i.Establishment_Id = e.Establishment_Id " +
                         "WHERE YEAR(i.Inspection_Date) = ? AND MONTH(i.Inspection_Date) = ? " +
                         "GROUP BY e.Establishment_Name";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, year);
                ps.setInt(2, month);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getString("Establishment"));
                    row.add(rs.getInt("total_inspections"));
                    row.add(rs.getDouble("avg_score"));
                    model.addRow(row);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year and Month must be numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InspectionReportUI().setVisible(true));
    }
}
