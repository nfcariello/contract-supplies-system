import source.*;

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

    private ResultSet getResultSet(String sql) {
        ResultSet rs1;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs1 = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rs1 = null;
        }
        return rs1;
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
    public void insert_contracts(int supplier_number, Date contract_date, LinkedList<ContractedItem> itemList) {
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

        // GET CONTRACT NUMBER THAT WAS JUST MADE
        int contract_no;
        ResultSet temp;
        String sql3 = "SELECT last_insert_rowid()";
        try {
            Connection conn = this.connect();
            PreparedStatement ps = conn.prepareStatement(sql3);
            temp = ps.executeQuery();
            contract_no = temp.getInt(1);
        } catch (SQLException e) {
            contract_no = 0;
        }

        for (ContractedItem x : itemList) {
            String sql2 = "INSERT INTO `To-Supply` (`CONTRACT-NO`, `ITEM-NO`, `CONTRACT-AMOUNT`, `CONTRACT-PRICE`) " +
                    "VALUES(?,?,?,?)";

            try {
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql2);
                pstmt.setInt(1, contract_no);
                pstmt.setInt(2, x.getItemNo());
                pstmt.setInt(3, x.getAmount());
                pstmt.setDouble(4, x.getPrice());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Query 5
    public void insert_order(Date date_required, int project_number, int contract_number, LinkedList<OrderedItem> items) {
        String sql = "INSERT INTO Orders(`DATE-REQUIRED`,`PROJECT-NO`,`CONTRACT-NO`) VALUES(?,?,?)";
        int on;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, date_required);
            pstmt.setInt(2, project_number);
            pstmt.setInt(3, contract_number);
            pstmt.executeUpdate();

            on = find_order(project_number, contract_number);

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

    //Query 6
    //WILL RETURN LINKED LIST OF ITEM CLASS WITH NUMBER AND DESCRIPTION
    public LinkedList<Item> find_items_in_order(int order_no) {
        String sql = "SELECT `ITEM-NO`, `ITEM-DESCRIPTION` FROM Items WHERE `ITEM-NO` IN ( " +
                " SELECT `ITEM-NO` FROM 'Made-Of' WHERE `ORDER-NO`=" + order_no + ")";
        //Not sure why its not allowing closing parentheses - Resolved
        ResultSet rs = getResultSet(sql);
        LinkedList<Item> items = new LinkedList<>();
        try {
            if (rs != null) while (rs.next()) {
                items.add(new Item(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            items = null;
        }

        return items;
    }


    public int find_order(int project_number, int contract_number) {
        String sql = "SELECT `ORDER-NO` FROM Orders WHERE `PROJECT-NO`=" + project_number + " AND `CONTRACT-NO`=" + contract_number;
        ResultSet rs = getResultSet(sql);
        int rm;
        try {
            rm = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = 0;
        }

        return rm;
    }

    // Query 7
    public double price_of_item_in_order(int item_no, int contract_no) {
        String sql = "SELECT `CONTRACT-PRICE` FROM `To-Supply` WHERE `ITEM-NO`=" + item_no +
                " AND `CONTRACT-NO`= " + contract_no;
        ResultSet rs = getResultSet(sql);
        double rm;
        try {
            rm = rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = 0;
        }

        return rm;
    }


    // Query 8
    public LinkedList<Integer> find_orders_for_item(int item_no) {
        String sql = "SELECT `ORDER-NO` FROM 'Made-Of' WHERE `ITEM-NO`=" + item_no;
        ResultSet rs = getResultSet(sql);
        LinkedList<Integer> order_nums = new LinkedList<>();
        try {
            while (rs.next()) {
                order_nums.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order_nums;
    }


    // Query 9
    public double price_of_item_in_contract(int item_no, int contract_no) {
        String sql = "SELECT `CONTRACT-PRICE` FROM 'To-Supply' WHERE `ITEM-NO`=" + item_no + " AND `CONTRACT-NO`=" + contract_no;
        ResultSet rs = getResultSet(sql);
        double rm;
        try {
            rm = rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rm = 0;
        }
        return rm;
    }

    //Query 10
    public Supplier find_supplier_no_for_contract(int contract_no) {
        String sql = "SELECT `SUPPLIER-NO` FROM 'CONTRACTS' WHERE `CONTRACT-NO`=" + contract_no;
        ResultSet rm;
        Supplier supplier = null;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rm = pstmt.executeQuery();

            while (rm.next()) {
                ResultSet rs = find_supplier(rm.getInt("SUPPLIER-NO"));
                supplier = new Supplier(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return supplier;
    }

    //Query 10 part2
    public ResultSet find_supplier(int supplier_no) {
        String sql = "SELECT * FROM `Suppliers` WHERE `SUPPLIER-NO`=" + supplier_no;
        ResultSet rm = getResultSet(sql);
        return rm;
    }


    //Query 11
    public int find_quantity_left(int item_no, int contract_no) {
        String sql = "SELECT `CONTRACT-AMOUNT` FROM 'To-Supply' WHERE `CONTRACT-NO`=" + contract_no + " AND `ITEM-NO`= " + item_no;
        ResultSet contract_amount;
        int contract_amt;
        int sum_qty;

        contract_amount = getResultSet(sql);
        try {
            contract_amt = contract_amount.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            contract_amt = 0;
        }

        String s = "SELECT SUM(`ORDER-QTY`) FROM 'Made-Of' WHERE (SELECT `ORDER-NO` FROM Orders WHERE 'CONTRACT-NO'=" + contract_no + ") AND 'ITEM-NO'=" + item_no;
        ResultSet sum_quantity = getResultSet(s);
        System.out.println("ResultSet: " + sum_quantity + "Contract Number: " + contract_no + " ItemNo: " + item_no);

        try {
            sum_qty = sum_quantity.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            sum_qty = 0;
        }

        return contract_amt - sum_qty;
    }

    public LinkedList<SummarizedPurchase> summerize_purchases() {

        String sql = "SELECT `CONTRACT-NO`, `ORDER-NO`, `ITEM-NO`, `ORDER-QTY` INTO `Temp_table` FROM 'Orders', `Made-Of`";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql2 = "ALTER TABLE `Temp_table` DROP `ORDER-NO`";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql3 = "SELECT `CONTRACT-NO`, `ITEM-NO`, SUM(`ORDER-QTY`) FROM `Temp_table` GROUP BY `CONTRACT-NO`, `ORDER-NO`";
        ResultSet rs = getResultSet(sql3);
        LinkedList<SummarizedPurchase> rm = new LinkedList<>();
        try {
            while (rs.next())
                rm.add(new SummarizedPurchase(rs.getInt(1), rs.getInt(2), rs.getInt(3)));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rm;

    }

}
