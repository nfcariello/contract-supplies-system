import source.OrderedItem;

import java.sql.*;
import java.util.LinkedList;

public class sql {


    private Connection connect() {
        String url = "jdbc:sqlite:db.SQLite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //Query 1
    public void insert_suppliers(String name, String address) {
        String sql = "INSERT INTO Suppliers(`SUPPLIER-NAME`,`SUPPLIER-ADDRESS`) VALUES(?,?)";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Query 2
    //item number is auto generated primary key
    public void insert_item(String description) {
        String sql = "INSERT INTO Items(`ITEM-DESCRIPTION`) VALUES(?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Query 3
    //project number is autogenerated primary key
    public void insert_project(String projectData) {
        String sql = "INSERT INTO Projects(`PROJECT-DATA`) VALUES(?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, projectData);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Query 4
    // TODO: UI - Item Number, Date of Contract, Contract Price, Contract Amount
//     TODO: SQL - Supplier Number, Date of Contract
    public void insert_contracts(int supplier_number, Date contract_date) {
        String sql = "INSERT INTO CONTRACTS(`SUPPLIER-NO`,`DATE-OF-CONTRACT`) VALUES(?,?)";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, supplier_number);
            pstmt.setDate(2, contract_date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Query 6
    public ResultSet find_items_in_order(int order_no) {
        String sql = "SELECT `ITEM-NO` FROM `Made-Of` WHERE `ORDER-NO`=" + order_no;
        return getResultSet(sql);
    }


    public ResultSet find_order(int project_number, int contract_number) {
        String sql = "SELECT `ORDER-NO` FROM Orders WHERE `PROJECT-NO`=" + project_number + " AND `CONTRACT-NO`=" + contract_number;
        return getResultSet(sql);
    }

    private ResultSet getResultSet(String sql) {
        ResultSet rs;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rs = null;
        }
        return rs;
    }


    public void new_order(Date date_required, int project_number, int contract_number, LinkedList<OrderedItem> items) {
        String sql = "INSERT INTO Orders(`DATE-REQUIRED`,`PROJECT-NO`,`CONTRACT-NO`) VALUES(?,?,?)";
        int on;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, date_required);
            pstmt.setInt(2, project_number);
            pstmt.setInt(3, contract_number);
            pstmt.executeUpdate();

            on = find_order(project_number, contract_number).getInt(1);
            while (items != null) {
                OrderedItem current = items.pop();

                String sql2 = "INSERT INTO `Made-Of`(`ORDER-NO`,`ITEM-NO`,`ORDER-QTY`) VALUES(?,?,?)";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1, on);
                pstmt2.setInt(2, current.getItemNo());
                pstmt2.setInt(3, current.getOrderQty());
                pstmt2.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Query 7
    public ResultSet price_of_item_in_order(int item_no, int contract_no) {
        String sql = "SELECT `CONTRACT-PRICE` FROM `To-Supply` WHERE `ITEM-NO`=" + item_no +
                " AND `CONTRACT-NO`= " + contract_no;
        ResultSet rm;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rm = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = null;
        }
        return rm;
    }


    // Query 8
    public ResultSet find_orders_for_item(int item_no) {
        String sql = "SELECT `ORDER-NO` FROM `Made-Of` WHERE `ITEM-NO`=" + item_no;
        ResultSet rm;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rm = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = null;
        }
        return rm;
    }


    // Query 9
    public ResultSet price_of_item_in_contract(int item_no, int contract_no) {
        String sql = "SELECT `CONTRACT-PRICE` FROM `To-Supply` WHERE `ITEM-NO`=" + item_no + " AND `CONTRACT-NO`=" + contract_no;
        ResultSet rm;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rm = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = null;
        }
        return rm;
    }

    //Query 10
    public ResultSet find_supplier_no_for_contract(int contract_no) {
        String sql = "SELECT `SUPPLIER-NO` FROM `CONTRACTS` WHERE `CONTRACT-NO`=" + contract_no;
        ResultSet rm;
        ResultSet supplier_info = null;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rm = pstmt.executeQuery();

            while (rm.next()) {
                supplier_info = find_supplier(rm.getInt("SUPPLIER-NO"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return supplier_info;
    }

    //Query 10 part2
    public ResultSet find_supplier(int supplier_no) {
        String sql = "SELECT * FROM `Suppliers` WHERE `SUPPLIER-NO`=" + supplier_no;
        ResultSet rm;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rm = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = null;
        }
        return rm;
    }
}
