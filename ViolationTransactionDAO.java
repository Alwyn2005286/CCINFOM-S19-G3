import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViolationTransactionDAO {

    // 🔹 1. View violations with establishment (JOIN)
    public static List<String[]> getViolationsWithEstablishment() throws SQLException {
        List<String[]> list = new ArrayList<>();

        String query =
            "SELECT v.Violation_Id, v.Requirement_Code, v.Inspection_ID, e.Establishment_Name " +
            "FROM violations v " +
            "JOIN inspection i ON v.Inspection_ID = i.Inspection_Id " +
            "JOIN establishment e ON i.Establishment_Id = e.Establishment_Id";

        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            list.add(new String[]{
                rs.getString("Violation_Id"),
                rs.getString("Requirement_Code"),
                rs.getString("Inspection_ID"),
                rs.getString("Establishment_Name")
            });
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    // 🔹 2. Track repeated violations
    public static List<String[]> getRepeatedViolations() throws SQLException {
    List<String[]> list = new ArrayList<>();

    String query =
        "SELECT e.Establishment_Name, COUNT(v.Violation_Id) AS total " +
        "FROM violations v " +
        "JOIN inspection i ON v.Inspection_ID = i.Inspection_Id " +
        "JOIN establishment e ON i.Establishment_Id = e.Establishment_Id " +
        "GROUP BY e.Establishment_Name " +
        "HAVING COUNT(v.Violation_Id) > 1";

    Connection conn = DBConnection.getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(query);

    while (rs.next()) {
        list.add(new String[]{
            rs.getString("Establishment_Name"),
            rs.getString("total")
        });
    }

    rs.close();
    stmt.close();
    conn.close();

    return list;
    }      

    // 🔹 3. Filter by inspection
    public static List<String[]> getViolationsByInspection(int inspectionId) throws SQLException {
        List<String[]> list = new ArrayList<>();

        String query =
            "SELECT Violation_Id, Requirement_Code, Inspection_ID " +
            "FROM violations WHERE Inspection_ID = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, inspectionId);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(new String[]{
                rs.getString("Violation_Id"),
                rs.getString("Requirement_Code"),
                rs.getString("Inspection_ID")
            });
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }
}