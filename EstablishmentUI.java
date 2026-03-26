import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EstablishmentUI extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private EstablishmentDAO dao;
    private JTextField idField, nameField;
    private JComboBox<Integer> ownerIdCombo, cityIdCombo;

    public EstablishmentUI() {
        dao = new EstablishmentDAO();
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Establishment Details"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Owner ID:"));
        ownerIdCombo = new JComboBox<>();
        inputPanel.add(ownerIdCombo);

        inputPanel.add(new JLabel("City ID:"));
        cityIdCombo = new JComboBox<>();
        inputPanel.add(cityIdCombo);

        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Est. ID", "Owner ID", "Name", "City ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Save");
        JButton refreshButton = new JButton("Refresh Data");
        
        addButton.addActionListener(e -> save());
        refreshButton.addActionListener(e -> refreshData());
        
        controlPanel.add(addButton);
        controlPanel.add(refreshButton);
        add(controlPanel, BorderLayout.SOUTH);

        refreshData();
    }

    private void refreshData() {
        // Update: Now using getAvailableCityIds() from the DAO
        cityIdCombo.removeAllItems();
        for (Integer id : dao.getAvailableCityIds()) cityIdCombo.addItem(id);
        
        ownerIdCombo.removeAllItems();
        for (Integer id : dao.getAvailableOwnerIds()) ownerIdCombo.addItem(id);
        
        loadTableData();
    }

    private void save() {
        try {
            String name = nameField.getText().trim();
            int ownerId = (Integer) ownerIdCombo.getSelectedItem();
            int cityId = (Integer) cityIdCombo.getSelectedItem();

            // Constructor now expects int for cityId
            Establishment est = new Establishment(0, ownerId, name, cityId);
            dao.addEstablishment(est);
            loadTableData();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Establishment est : dao.getAllEstablishments()) {
            // Update: Displaying getCityId() instead of getCityName()
            Object[] row = { est.getEstablishmentId(), est.getOwnerId(), est.getEstablishmentName(), est.getCityId() };
            tableModel.addRow(row);
        }
    }
}