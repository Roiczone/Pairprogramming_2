import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonFileUtils {
    public static List<Products.Product> loadProducts(String filename) {
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
