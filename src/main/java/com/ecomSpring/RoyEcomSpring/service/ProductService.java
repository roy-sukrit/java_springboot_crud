package com.ecomSpring.RoyEcomSpring.service;


import com.ecomSpring.RoyEcomSpring.model.Product;
import com.ecomSpring.RoyEcomSpring.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Component
public class ProductService {

    @Autowired
    ProductRepo repo;


    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(new Product());
    }

    public Product addProduct(Product product, MultipartFile imageFile) {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try {
            product.setImageDate(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return repo.save(product);

    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) {


        try {
            product.setImageDate(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        product.setImageName((imageFile.getOriginalFilename()));
        product.setImageType(imageFile.getContentType());
        return repo.save(product);

    }

    public void deleteProduct(int id) {

        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {

        return repo.searchProducts(keyword);
    }
}
