import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class InspectionDAO {

    // CREATE
    public static int addInspection(Inspection inspection) throws SQLException {
        String query = "INSERT INTO inspection (Inspection_Date, Score, Grade, Remarks, Establishment_Id, Assignment_Id, Violation_Id) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setDate(1, Date.valueOf(inspection.getInspectionDate()));
        stmt.setFloat(2, inspection.getScore());
        stmt.setString(3, inspection.getGrade());
        stmt.setString(4, inspection.getRemarks());
        stmt.setInt(5, inspection.getEstablishmentId());
        stmt.setInt(6, inspection.getAssignmentId());
        stmt.setInt(7, inspection.getViolationId());

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int generatedId = -1;
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();

        return generatedId;
    }

    // READ ALL
    public static List<Inspection> getAllInspections() throws SQLException {
        List<Inspection> inspections = new ArrayList<>();

        String query = "SELECT * FROM inspection ORDER BY Inspection_Date DESC";

        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Inspection inspection = new Inspection(
                rs.getInt("Inspection_Id"),
                rs.getDate("Inspection_Date").toLocalDate(),
                rs.getFloat("Score"),
                rs.getString("Grade"),
                rs.getString("Remarks"),
                rs.getInt("Establishment_Id"),
                rs.getInt("Assignment_Id"),
                rs.getInt("Violation_Id")
            );

            inspections.add(inspection);
        }

        rs.close();
        stmt.close();
        conn.close();

        return inspections;
    }

    // UPDATE
    public static boolean updateInspection(Inspection inspection) throws SQLException {
        String query = "UPDATE inspection SET Inspection_Date = ?, Score = ?, Grade = ?, Remarks = ?, " +
                       "Establishment_Id = ?, Assignment_Id = ?, Violation_Id = ? WHERE Inspection_Id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setDate(1, Date.valueOf(inspection.getInspectionDate()));
        stmt.setFloat(2, inspection.getScore());
        stmt.setString(3, inspection.getGrade());
        stmt.setString(4, inspection.getRemarks());
        stmt.setInt(5, inspection.getEstablishmentId());
        stmt.setInt(6, inspection.getAssignmentId());
        stmt.setInt(7, inspection.getViolationId());
        stmt.setInt(8, inspection.getInspectionId());

        int rowsAffected = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return rowsAffected > 0;
    }

    // DELETE
    public static boolean deleteInspection(int inspectionId) throws SQLException {
        String query = "DELETE FROM inspection WHERE Inspection_Id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, inspectionId);

        int rowsAffected = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return rowsAffected > 0;
    }
}