package model;

public class Trade {

    private int userId;
    private int productId;
    private int quantity;
    private String status;

    public Trade(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = "PENDING";
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
