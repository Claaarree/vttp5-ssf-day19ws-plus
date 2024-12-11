package sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.model.Product;
import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public ModelAndView getAllProducts() {
        List<Product> productsList = productService.getAllProducts();
        
        ModelAndView mav = new ModelAndView("products");
        mav.addObject("products", productsList);

        return mav;
    }

    @GetMapping("/buy/{id}")
    public ModelAndView buyProduct(@PathVariable String id) {
        // System.out.println(id);
        ModelAndView mav = new ModelAndView();
        Product productBought = productService.updateRedisBuyProduct(id);
        if(productBought != null) {
            mav.addObject("product", productBought);
            mav.setViewName("productDetails");
        } else {
            mav.setViewName("noStock");
        }
        return mav;
    }

}
