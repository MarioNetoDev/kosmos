package model;

public class Rating {

    private int ratingId;
    private int userId;
    private int productId;
    private int value;

    public Rating(int ratingId, int userId, int productId, int value) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.productId = productId;
        setValue(value);
    }

    public int getRatingId() {
        return ratingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException(
                    "Rating deve estar entre 1 e 5"
            );
        }
        this.value = value;
    }
}
