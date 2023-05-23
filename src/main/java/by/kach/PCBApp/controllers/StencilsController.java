package by.kach.PCBApp.controllers;

import by.kach.PCBApp.models.Stencil;
import by.kach.PCBApp.services.PCBService;
import by.kach.PCBApp.services.StencilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stencils")
public class StencilsController {
    private final StencilsService stencilsService;
    private final PCBService pcbService;

    @Autowired
    public StencilsController(StencilsService stencilsService, PCBService pcbService) {
        this.stencilsService = stencilsService;
        this.pcbService = pcbService;
    }

    @GetMapping()
    public String getAllStencils(Model model){
        model.addAttribute("stencils", stencilsService.findAllStencils());
        return "stencils/all-stencils-page";
    }

    @GetMapping("/{id}")
    public String getStencilPage(@PathVariable("id") String id, Model model){
        model.addAttribute("stencil", stencilsService.findStencilbyId(id));
        return "stencils/stencil-page";
    }

    @GetMapping("/new")
    public String getNewForm(Model model){
        model.addAttribute("newStencil", new Stencil());
        return "stencils/new-stencil-page";
    }

    @PostMapping()
    public String createStencil(@ModelAttribute("newStencil") Stencil newStencil){
        boolean isSaved = stencilsService.saveStencil(newStencil);
        if(isSaved)
            return "redirect:/stencils/" + newStencil.getId();
        else
            return "stencils/duplicate-stencil-page";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") String id, Model model){
        model.addAttribute("stencilToUpdate", stencilsService.findStencilbyId(id));
        return "stencils/edit-stencil-page";
    }

    @PatchMapping("/{id}")
    public String updateStencil(@PathVariable("id") String id, @ModelAttribute("stencilToUpdate") Stencil stencilToUpdate){

        stencilsService.updateStencil(id, stencilToUpdate);
        return "redirect:/stencils/" + stencilToUpdate.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteStencil(@PathVariable("id") String id){
        stencilsService.deleteStencil(id);
        return "redirect:/stencils";
    }

    @GetMapping("/{id}/pcb")
    public String getPCBForm(@PathVariable("id") String stencilId, Model model){
        model.addAttribute("stencilId", stencilId);
        model.addAttribute("pcbs", pcbService.findAllPCB());
        return "stencils/add-pcb-page";
    }

    @PatchMapping("/{id}/pcb")
    public String setPcb(@PathVariable("id") String stencilId, @ModelAttribute("pcbId") String pcbId){
        stencilsService.setPCB(stencilId, pcbId);
        return "redirect:/stencils/" + stencilId;
    }

    @DeleteMapping("/{id}/pcb")
    public String deletePcb(@PathVariable("id") String stencilId, @ModelAttribute("pcbId") String pcbId){
        stencilsService.deletePCB(stencilId, pcbId);
        return "redirect:/stencils/" + stencilId;
    }




}
