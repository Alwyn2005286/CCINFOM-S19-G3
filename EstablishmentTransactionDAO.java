import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstablishmentTransactionDAO {

    // 🔹 1. View all establishments with last inspection date
    public static List<String[]> getEstablishmentsWithLastInspection() throws SQLException {
        List<String[]> list = new ArrayList<>();

        String query =
            "SELECT e.Establishment_Id, e.Establishment_Name, e.Owner_Name, e.Operating_Status, MAX(i.Inspection_Date) AS Last_Inspection " +
            "FROM establishment e " +
            "LEFT JOIN inspection i ON e.Establishment_Id = i.Establishment_Id " +
            "GROUP BY e.Establishment_Id, e.Establishment_Name, e.Owner_Name, e.Operating_Status";

        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new String[]{
                rs.getString("Establishment_Id"),
                rs.getString("Establishment_Name"),
                rs.getString("Owner_Name"),
                rs.getString("Operating_Status"),
                rs.getString("Last_Inspection")
            });
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    // 🔹 2. Filter by operating status
    public static List<String[]> getEstablishmentsByStatus(String status) throws SQLException {
        List<String[]> list = new ArrayList<>();

        String query =
            "SELECT Establishment_Id, Establishment_Name, Owner_Name, Operating_Status " +
            "FROM establishment WHERE Operating_Status = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, status);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new String[]{
                rs.getString("Establishment_Id"),
                rs.getString("Establishment_Name"),
                rs.getString("Owner_Name"),
                rs.getString("Operating_Status")
            });
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    // 🔹 3. Count establishments with failed inspections
    public static List<String[]> getFailedInspectionsCount() throws SQLException {
        List<String[]> list = new ArrayList<>();

        String query =
            "SELECT e.Establishment_Name, COUNT(i.Inspection_Id) AS Failed_Count " +
            "FROM establishment e " +
            "JOIN inspection i ON e.Establishment_Id = i.Establishment_Id " +
            "WHERE i.Grade = 'FAIL' " +
            "GROUP BY e.Establishment_Name " +
            "HAVING COUNT(i.Inspection_Id) > 0";

        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new String[]{
                rs.getString("Establishment_Name"),
                rs.getString("Failed_Count")
            });
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }
}
