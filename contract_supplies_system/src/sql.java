import java.sql.*;

public class sql {


    private Connection connect() {
        String url = "jdbc:sqlite:contract_supplies_system/src/db.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert_contracts(int supplier_number, Date contract_date) {
        String sql = "INSERT INTO CONTRACTS(`SUPPLIER-NO`,`DATE-OF-CONTRACT`) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, supplier_number);
            pstmt.setDate(2, contract_date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert_suppliers(String name, String address) {
        String sql = "INSERT INTO Suppliers(`SUPPLIER-NAME`,`SUPPLIER-ADDRESS`) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
