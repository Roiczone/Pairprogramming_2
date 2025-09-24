import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getCategory() {
            return category;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

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

        public static Product fromJson(String json) {
            json = json.trim().replaceAll("[{}\"]", ""); // remove { } and "
            String[] parts = json.split(",");
            int id = 0, stock = 0;
            double price = 0;
            String name = "", category = "";

            for (String part : parts) {
                String[] kv = part.split(":");
                String key = kv[0].trim();
                String value = kv[1].trim();

                switch (key) {
                    case "id":
                        id = Integer.parseInt(value);
                        break;
                    case "name":
                        name = value;
                        break;
                    case "price":
                        price = Double.parseDouble(value);
                        break;
                    case "stock":
                        stock = Integer.parseInt(value);
                        break;
                    case "category":
                        category = value;
                        break;
                }
            }
            return new Product(id, name, price, stock, category);
        }
    }


    public static List<Product> loadProducts(String filename) {
        List<Products.Product> products = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
            return products;
        }

        String json = sb.toString().trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1); // remove [ ]
            String[] items = json.split("},");
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                if (!item.endsWith("}")) item += "}";
                products.add(Products.Product.fromJson(item));
            }
        }
        return products;
    }

    public static void saveProducts(String filename, List<Products.Product> products) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("[");
            for (int i = 0; i < products.size(); i++) {
                pw.print("  " + products.get(i).toJson());
                if (i < products.size() - 1) pw.println(",");
                else pw.println();
            }
            pw.println("]");
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());


        }
    }
}
