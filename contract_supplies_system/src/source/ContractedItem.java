package source;

public class ContractedItem {

    int itemNo;
    double price;
    int amount;


    public ContractedItem(int item, double p, int a) {
        itemNo = item;
        price = p;
        amount = a;
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
