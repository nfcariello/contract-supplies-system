package source;

public class OrderedItem {

    private int itemNo;
    private int orderQty;

    public OrderedItem(int itemNo, int orderQty) {
        this.itemNo = itemNo;
        this.orderQty = orderQty;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }
}
