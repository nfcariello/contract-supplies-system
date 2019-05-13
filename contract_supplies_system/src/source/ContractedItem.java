package source;

public class ContractedItem {

    private int itemNo;
    private double price;
    private int amount;

    public ContractedItem(int itemNo, double price, int amount) {
        this.itemNo = itemNo;
        this.price = price;
        this.amount = amount;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
