public class Products {

    public static class Product {
        private int id;
        private String name;
        private double price;
        private int stock;
        private String category;

        public Product(int id, String name, double price, int stock, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.category = category;
        }

        public int getId() {return id;}

        public String getName() {return name;}

        public double getPrice() {return price;}

        public int getStock() {return stock;}

        public String getCategory() {return category;}

        public void setStock(int stock) {this.stock = stock;}

        @Override
        public String toString() {
            return id + " | " + name + " | $" + price + " | Stock: " + stock + " | " + category;
        }

        public String toJson() {
            return String.format(
                    "{\"id\":%d,\"name\":\"%s\",\"price\":%.2f,\"stock\":%d,\"category\":\"%s\"}",
                    id, name, price, stock, category
            );
        }

    }
}
