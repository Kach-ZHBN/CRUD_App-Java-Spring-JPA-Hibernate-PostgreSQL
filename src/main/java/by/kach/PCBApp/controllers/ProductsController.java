package by.kach.PCBApp.controllers;

import by.kach.PCBApp.models.Product;
import by.kach.PCBApp.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping()
    public String getAllProducts(Model model){
        model.addAttribute("products", productsService.findAllProducts());
        return "products/all-products-page";
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable("id") String id, Model model){
        model.addAttribute("product", productsService.findProductByid(id));
        return "products/product-page";
    }

    @GetMapping("/new")
    public String getNewForm(Model model){
        model.addAttribute("newProduct", new Product());
        return "products/new-product-page";
    }

    @PostMapping()
    public String createProduct(@ModelAttribute("newProduct") Product product){
        boolean isSaved = productsService.saveProduct(product);
        if(isSaved)
            return "redirect:/products/" + product.getId();
        else
            return "products/duplicate-product-page";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") String id, Model model){
        model.addAttribute("productToUpdate", productsService.findProductByid(id));
        return "products/edit-product-page";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("productToUpdate") Product productToUpdate, @PathVariable("id") String id){
        productsService.updateProduct(id, productToUpdate);
        return "redirect:/products/" + productToUpdate.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") String id){
        productsService.deleteProduct(id);
        return "redirect:/products";
    }

}
