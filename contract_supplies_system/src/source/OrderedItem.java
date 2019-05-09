package source;

import static java.sql.Types.NULL;

public class OrderedItem {

    int itemNo;
    int orderQty;


    public OrderedItem() {
        itemNo = NULL;
        orderQty = NULL;
    }

    public OrderedItem(int item, int qty) {
        itemNo = item;
        orderQty = qty;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }
}
