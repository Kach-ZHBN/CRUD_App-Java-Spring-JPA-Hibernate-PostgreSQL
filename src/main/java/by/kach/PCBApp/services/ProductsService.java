package by.kach.PCBApp.services;

import by.kach.PCBApp.models.PCB;
import by.kach.PCBApp.models.Product;
import by.kach.PCBApp.repositories.PCBRepository;
import by.kach.PCBApp.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final PCBRepository pcbRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, PCBRepository pcbRepository) {
        this.productsRepository = productsRepository;
        this.pcbRepository = pcbRepository;
    }

    public Iterable<Product> findAllProducts(){
        return productsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public Product findProductByid(String id){
        return productsRepository.findById(id).orElse(null);
    }

    public List<Product> findProductWherePcbIsNull(){
        return productsRepository.findByPcbIsNull();
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

    @Transactional
    public void setPCBtoProduct(String productId, String pcbId){
        Product product = findProductByid(productId);
        PCB pcb = pcbRepository.findById(pcbId).orElse(null);
        product.setPcb(pcb);
        pcb.getProductList().add(product);

        updateProduct(productId, product);
        pcbRepository.save(pcb);
    }

    @Transactional
    public void deletePCB(String productId){
        Product product = findProductByid(productId);
        PCB pcb = pcbRepository.findById(product.getPcb().getId()).orElse(null);
        product.setPcb(null);
        pcb.getProductList().remove(product);

        updateProduct(productId, product);
        pcbRepository.save(pcb);
    }
}
