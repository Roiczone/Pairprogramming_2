import java.util.*;
import java.time.LocalDate;

public class Main {
    private static final String PRODUCTS_FILE = "products.json";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Products.Product> products = JsonFileUtils.loadProducts(PRODUCTS_FILE);
        List<CartItem> cart = new ArrayList<>();

        while (true) {
            System.out.println("\n=== Online Shopping Cart ===");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    products.forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    Products.Product product = products.stream()
                            .filter(p -> p.getId() == pid)
                            .findFirst().orElse(null);
                    if (product != null && product.getStock() >= qty) {
                        cart.add(new CartItem(product, qty));
                        System.out.println("Added to cart!");
                    } else {
                        System.out.println("Invalid product or insufficient stock.");
                    }
                    break;

                case 3:
                    cart.forEach(System.out::println);
                    double total = cart.stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
                    System.out.println("Total: $" + total);
                    break;

                case 4:
                    double checkoutTotal = cart.stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
                    System.out.println("Checkout total: $" + checkoutTotal);

                    for (CartItem item : cart) {
                        item.getProduct().setStock(item.getProduct().getStock() - item.getQuantity());
                    }

                    JsonFileUtils.saveProducts(PRODUCTS_FILE, products);
                    cart.clear();
                    System.out.println("Order placed on " + LocalDate.now() + "!");
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
