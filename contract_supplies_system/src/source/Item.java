package source;

public class Item {

    int itemNo;
    String desc;

    public Item(int itemNo, String desc) {
        this.itemNo = itemNo;
        this.desc = desc;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
