public class CartItem {
    private Products.Product product;
    private int quantity;

    public CartItem(Products.Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Products.Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity + " = $" + (product.getPrice() * quantity);
    }
}