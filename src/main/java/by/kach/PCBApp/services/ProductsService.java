package by.kach.PCBApp.services;

import by.kach.PCBApp.models.Product;
import by.kach.PCBApp.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Iterable<Product> findAllProducts(){
        return productsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public Product findProductByid(String id){
        return productsRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean saveProduct(Product productToSave){
        if(findProductByid(productToSave.getId()) == null) {
            productsRepository.save(productToSave);
            return true;
        }else
            return false;
    }

    @Transactional
    public Product updateProduct(String id, Product productToUpdate){
        productToUpdate.setId(id);
        return productsRepository.save(productToUpdate);
    }

    @Transactional
    public void deleteProduct(String id){
        Product productToRemove = findProductByid(id);
        productsRepository.delete(productToRemove);
    }
}
