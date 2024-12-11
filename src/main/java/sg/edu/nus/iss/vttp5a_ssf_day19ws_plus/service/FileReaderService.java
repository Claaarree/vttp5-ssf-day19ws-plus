package sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.repo.HashRepo;
import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.utils.Utility;

@Service
public class FileReaderService {
    
    @Autowired
    HashRepo productRepo;

    public void loadFileToRedis() {
        // getClass().getClassLoader().getResourceAsStream("static/JSON/products.json");
        // above is alternate to line below
        InputStream is = getClass().getResourceAsStream("/static/JSON/products.json");
        JsonReader jReader = Json.createReader(is);
        JsonArray jArray = jReader.readArray();

        for (int i = 0; i < jArray.size(); i++) {
            JsonObject jObject = jArray.getJsonObject(i);
            productRepo.addToHash(Utility.redisProductKey, String.valueOf(jObject.getInt("id")), jObject.toString());
        }
    }
}
