package by.kach.PCBApp.controllers;

import by.kach.PCBApp.models.Stencil;
import by.kach.PCBApp.services.StencilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stencils")
public class StencilsController {
    private final StencilsService stencilsService;

    @Autowired
    public StencilsController(StencilsService stencilsService) {
        this.stencilsService = stencilsService;
    }

    @GetMapping()
    public String getAllStencils(Model model){
        model.addAttribute("stencils", stencilsService.findAllStencils());
        return "stencils/all-stencils-page";
    }

    @GetMapping("/{id}")
    public String getStencilPage(@PathVariable("id") String id, Model model){
        model.addAttribute("stencil", stencilsService.findStencilbyid(id));
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
        model.addAttribute("stencilToUpdate", stencilsService.findStencilbyid(id));
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

}
