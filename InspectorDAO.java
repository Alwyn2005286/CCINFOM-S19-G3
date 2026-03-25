import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InspectorDAO {

    // ===== ADD INSPECTOR =====
    public void addInspector(Inspector inspector) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "INSERT INTO inspector_management (Full_Name, District, Active_Status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, inspector.getFullName());
            stmt.setString(2, inspector.getDistrict());
            stmt.setString(3, inspector.getActiveStatus());
            stmt.executeUpdate();
            System.out.println("Inspector added.");

        } catch (Exception e) {
            System.out.println("Error adding inspector.");
            e.printStackTrace();
        }
    }

    // ===== GET ALL INSPECTORS =====
    public List<Inspector> getAllInspectors() {
        List<Inspector> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT * FROM inspector_management";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Inspector i = new Inspector(
                        rs.getInt("Inspector_Id"),
                        rs.getString("Full_Name"),
                        rs.getString("District"),
                        rs.getString("Active_Status")
                );
                list.add(i);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving inspectors.");
            e.printStackTrace();
        }

        return list;
    }

    // ===== UPDATE INSPECTOR =====
    public void updateInspector(Inspector inspector) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "UPDATE inspector_management SET Full_Name=?, District=?, Active_Status=? WHERE Inspector_Id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, inspector.getFullName());
            stmt.setString(2, inspector.getDistrict());
            stmt.setString(3, inspector.getActiveStatus());
            stmt.setInt(4, inspector.getInspectorId());

            stmt.executeUpdate();

            System.out.println("Inspector updated.");

        } catch (Exception e) {
            System.out.println("Error updating inspector.");
            e.printStackTrace();
        }
    }

    // ===== DELETE INSPECTOR =====
    public void deleteInspector(int id) {
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "DELETE FROM inspector_management WHERE Inspector_Id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Inspector deleted.");

        } catch (Exception e) {
            System.out.println("Error deleting inspector.");
            e.printStackTrace();
        }
    }
}