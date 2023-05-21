package by.kach.PCBApp.controllers;

import by.kach.PCBApp.models.PCB;
import by.kach.PCBApp.models.Product;
import by.kach.PCBApp.services.PCBService;
import by.kach.PCBApp.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;
    private final PCBService pcbService;

    @Autowired
    public ProductsController(ProductsService productsService, PCBService pcbService) {
        this.productsService = productsService;
        this.pcbService = pcbService;
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

    @GetMapping("/{id}/pcb")
    public String getPCBform(@PathVariable("id") String id, Model model){
        model.addAttribute("productId", id);
        model.addAttribute("PCBs", pcbService.findAllPCB());
        return "products/add-pcb-page";
    }

    @PatchMapping("/{productId}/pcb")
    public String setPCB(@PathVariable("productId") String productId, @ModelAttribute("pcbId") String pcbId){
        productsService.setPCBtoProduct(productId, pcbId);
        return "redirect:/products/" + productId;
    }

    @DeleteMapping("/{productId}/pcb")
    public String deletePCB(@PathVariable("productId") String productId){
        productsService.deletePCB(productId);
        return "redirect:/products/" + productId;
    }


}
