package model.LinkedList;

public class ProductRow {
    private String id;
    private String name;
    private String price;
    private String stock;
    private String type;
    private String regDate;

    public ProductRow(String id, String name, String price, String stock, String type, String regDate) {
        this.id      = id;
        this.name    = name;
        this.price   = price;
        this.stock   = stock;
        this.type    = type;
        this.regDate = regDate;
    }

    public String getId()      { return id; }
    public String getName()    { return name; }
    public String getPrice()   { return price; }
    public String getStock()   { return stock; }
    public String getType()    { return type; }
    public String getRegDate() { return regDate; }
}

