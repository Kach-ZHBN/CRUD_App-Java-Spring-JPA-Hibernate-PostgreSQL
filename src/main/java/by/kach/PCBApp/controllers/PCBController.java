package by.kach.PCBApp.controllers;

import by.kach.PCBApp.models.PCB;
import by.kach.PCBApp.services.PCBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pcb")
public class PCBController {
    private final PCBService pcbService;

    @Autowired
    public PCBController(PCBService pcbService) {
        this.pcbService = pcbService;
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
}
