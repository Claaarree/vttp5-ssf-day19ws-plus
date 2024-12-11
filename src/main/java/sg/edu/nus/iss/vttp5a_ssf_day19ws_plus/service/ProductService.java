package sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.model.Product;
import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.repo.HashRepo;
import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.utils.Utility;

@Service
public class ProductService {
    
    @Autowired
    HashRepo productRepo;

    public List<Product> getAllProducts() {
        Set<String> fieldNames = productRepo.getAllFields(Utility.redisProductKey);
        List<Product> productsList = new ArrayList<>();

        for (String id : fieldNames) {
            String product = productRepo.getFieldValue(Utility.redisProductKey, id);
            
            Product p = jsonMapToObject(product);
            
            productsList.add(p);
        }
        return productsList;
    }

    public Product updateRedisBuyProduct(String id) {
        String productFound = productRepo.getFieldValue(Utility.redisProductKey, id);
        // System.out.println(productFound);
        Product p = jsonMapToObject(productFound);
        if (p.getStock()==p.getBuy()){
            return null;
        } else {
            p.setBuy(p.getBuy() + 1);
    
            // System.out.println(p.getBuy());
    
            JsonObject jObject = Json.createObjectBuilder()
                    .add("id", p.getId())
                    .add("title", p.getTitle())
                    .add("description", p.getDescription())
                    .add("price", p.getPrice())
                    .add("discountPercentage", p.getDiscountPercentage())
                    .add("rating", p.getRating())
                    .add("stock", p.getStock())
                    .add("brand", p.getBrand())
                    .add("category", p.getCategory())
                    .add("dated", p.getDated().getTime())
                    .add("buy", p.getBuy())
                    .build();
    
            productRepo.addToHash(Utility.redisProductKey, id, jObject.toString());
        }
        return p;
    }

    public Product jsonMapToObject(String product) {
        JsonReader jReader = Json.createReader(new StringReader(product));
        JsonObject productJson = jReader.readObject();
        // System.out.println(productJson.toString());
        
        Long datedLong = productJson.getJsonNumber("dated").longValueExact();
        Date dated = new Date(datedLong);

        Product p = new Product();
        p.setId(productJson.getInt("id"));
        p.setTitle(productJson.getString("title"));
        p.setDescription(productJson.getString("description"));
        p.setPrice(productJson.getInt("price"));
        p.setDiscountPercentage(productJson.getJsonNumber("discountPercentage").doubleValue());
        p.setRating(productJson.getJsonNumber("rating").doubleValue());
        p.setStock(productJson.getInt("stock"));
        p.setBrand(productJson.getString("brand"));
        p.setCategory(productJson.getString("category"));
        p.setDated(dated);
        p.setBuy(productJson.getInt("buy"));

        return p;
    }
}
