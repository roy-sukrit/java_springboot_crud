package com.ecomSpring.RoyEcomSpring.controller;


import com.ecomSpring.RoyEcomSpring.model.Product;
import com.ecomSpring.RoyEcomSpring.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;



    @RequestMapping("/")
    public String greet() {
        return "HEllo WOrld!";
    }

     @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){

        Product product = service.getProductById(id);

        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }

        else return  new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){


        try {
            Product productNew = service.addProduct(product,imageFile);

            return new ResponseEntity<>(productNew,HttpStatus.CREATED);
        } catch (RuntimeException e) {

return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageDate();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestPart Product product,
                                                @RequestPart MultipartFile imageFile, @PathVariable int id){

        Product product1 = service.updateProduct(id,product,imageFile);

            if(product1 != null){
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            }

            else return  new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = service.getProductById(id);

        if(product != null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }

        else return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);


    }


    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {


        System.out.println("Searching with" + keyword);
        List<Product> products = service.searchProducts(keyword);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
