package source;

public class SummarizedPurchase {

    int contract_no;
    int item_no;
    int order_qty;

    public SummarizedPurchase(int contract_no, int item_no, int order_qty) {
        this.contract_no = contract_no;
        this.item_no = item_no;
        this.order_qty = order_qty;
    }

    public int getContract_no() {
        return contract_no;
    }

    public void setContract_no(int contract_no) {
        this.contract_no = contract_no;
    }

    public int getItem_no() {
        return item_no;
    }

    public void setItem_no(int item_no) {
        this.item_no = item_no;
    }

    public int getOrder_qty() {
        return order_qty;
    }

    public void setOrder_qty(int order_qty) {
        this.order_qty = order_qty;
    }
}
