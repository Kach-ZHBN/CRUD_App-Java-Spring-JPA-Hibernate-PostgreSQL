package by.kach.PCBApp.controllers;

import by.kach.PCBApp.models.PCB;
import by.kach.PCBApp.models.Product;
import by.kach.PCBApp.models.Stencil;
import by.kach.PCBApp.services.PCBService;
import by.kach.PCBApp.services.ProductsService;
import by.kach.PCBApp.services.StencilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pcb")
public class PCBController {
    private final PCBService pcbService;
    private final ProductsService productsService;
    private final StencilsService stencilsService;

    @Autowired
    public PCBController(PCBService pcbService, ProductsService productsService, StencilsService stencilsService) {
        this.pcbService = pcbService;
        this.productsService = productsService;
        this.stencilsService = stencilsService;
    }

    @GetMapping()
    public String getAllPCB(Model model){
        model.addAttribute("PCBs", pcbService.findAllPCB());
        return "pcb/all-PCB-page";
    }

    @GetMapping("/{id}")
    public String getPCBPage(@PathVariable("id") String id, Model model){
        model.addAttribute("PCB", pcbService.findPCBbyid(id));
        return "pcb/PCB-page";
    }

    @GetMapping("/new")
    public String getNewForm(Model model){
        model.addAttribute("newPCB", new PCB());
        return "pcb/new-PCB-page";
    }

    @PostMapping()
    public String createPCB(@ModelAttribute("newPCB") PCB newPCB){
        boolean isSaved = pcbService.savePCB(newPCB);
        if(isSaved)
            return "redirect:/pcb/" + newPCB.getId();
        else
            return "pcb/duplicate-PCB-page";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") String id, Model model){
        model.addAttribute("pcbToUpdate", pcbService.findPCBbyid(id));
        return "pcb/edit-PCB-page";
    }

    @PatchMapping("/{id}")
    public String updatePCB(@PathVariable("id") String id, @ModelAttribute("pcbToUpdate") PCB pcbToUpdate){
        pcbService.updatePCB(id, pcbToUpdate);
        return "redirect:/pcb/" + pcbToUpdate.getId();
    }

    @DeleteMapping("/{id}")
    public String deletePCB(@PathVariable("id") String id){
        pcbService.deletePCB(id);
        return "redirect:/pcb";
    }

    @GetMapping("/{id}/product")
    public String getProductsForm(@PathVariable("id") String pcbId, Model model){
        model.addAttribute("pcbId", pcbId);
        model.addAttribute("products", productsService.findProductWherePcbIsNull());
        return "pcb/add-product-page";
    }

    @PatchMapping("/{id}/product")
    public String setProduct(@PathVariable("id") String pcbId, @ModelAttribute("productId") String productId){
        PCB pcb = pcbService.findPCBbyid(pcbId);
        Product product = productsService.findProductByid(productId);
        pcb.getProductList().add(product);
        product.setPcb(pcb);

        pcbService.updatePCB(pcbId, pcb);
        productsService.updateProduct(productId, product);

        return "redirect:/pcb/" + pcbId;
    }

    @DeleteMapping("/{id}/product")
    public String deleteProduct(@PathVariable("id") String pcbId, @ModelAttribute("productForDelete") String productId){
        PCB pcb = pcbService.findPCBbyid(pcbId);
        Product product = productsService.findProductByid(productId);

        pcb.getProductList().remove(product);
        product.setPcb(null);

        pcbService.updatePCB(pcbId, pcb);
        productsService.updateProduct(productId, product);
        return "redirect:/pcb/" + pcbId;
    }

    @GetMapping("/{id}/stencil")
    public String getStencilsPage(@PathVariable("id") String pcbId, Model model){
        model.addAttribute("pcbId", pcbId);
        model.addAttribute("stencils", stencilsService.findAllStencils());
        return "pcb/add-stencil-page";
    }

    @PatchMapping("/{id}/stencil")
    public String setStencil(@PathVariable("id") String pcbId, @ModelAttribute("stencilId") String stencilId){
        PCB pcb = pcbService.findPCBbyid(pcbId);
        Stencil stencil = stencilsService.findStencilbyid(stencilId);

        pcb.getStensilList().add(stencil);
        stencil.getPcbList().add(pcb);

        pcbService.updatePCB(pcbId, pcb);
        stencilsService.updateStencil(stencilId, stencil);

        return "redirect:/pcb/" + pcbId;
    }

    @DeleteMapping("/{id}/stencil")
    public String deleteStencil(@PathVariable("id") String pcbId, @ModelAttribute("stencilId") String stencilId){
        PCB pcb = pcbService.findPCBbyid(pcbId);
        Stencil stencil = stencilsService.findStencilbyid(stencilId);

        pcb.getStensilList().remove(stencil);
        stencil.getPcbList().remove(pcb);

        pcbService.updatePCB(pcbId, pcb);
        stencilsService.updateStencil(stencilId, stencil);

        return "redirect:/pcb/" + pcbId;
    }



}
