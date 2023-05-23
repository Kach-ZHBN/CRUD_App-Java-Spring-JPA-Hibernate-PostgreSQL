package by.kach.PCBApp.services;

import by.kach.PCBApp.models.PCB;
import by.kach.PCBApp.models.Stencil;
import by.kach.PCBApp.repositories.PCBRepository;
import by.kach.PCBApp.repositories.StencilsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StencilsService {
    private final StencilsRepository stencilsRepository;
    private final PCBRepository pcbRepository;

    @Autowired
    public StencilsService(StencilsRepository stencilsRepository, PCBRepository pcbRepository) {
        this.stencilsRepository = stencilsRepository;
        this.pcbRepository = pcbRepository;
    }

    public List<Stencil> findAllStencils(){
        return stencilsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Stencil findStencilbyId(String id){
        return stencilsRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean saveStencil(Stencil stencilToSave){
        if(findStencilbyId(stencilToSave.getId()) == null) {
            stencilsRepository.save(stencilToSave);
            return true;
        }else
            return false;
    }

    @Transactional
    public Stencil updateStencil(String id, Stencil stencilToUpdate){
        stencilToUpdate.setId(id);
        return stencilsRepository.save(stencilToUpdate);
    }

    @Transactional
    public void deleteStencil(String id){
        Stencil stencilToRemove = findStencilbyId(id);
        stencilsRepository.delete(stencilToRemove);
    }

    @Transactional
    public void setPCB(String stencilId, String pcbId){
        Stencil stencil = findStencilbyId(stencilId);
        PCB pcb = pcbRepository.findById(pcbId).orElse(null);
        stencil.getPcbList().add(pcb);
        pcb.getStensilList().add(stencil);

        updateStencil(stencilId, stencil);
        pcbRepository.save(pcb);
    }

    @Transactional
    public void deletePCB(String stencilId, String pcbId){
        Stencil stencil = findStencilbyId(stencilId);
        PCB pcb = pcbRepository.findById(pcbId).orElse(null);

        stencil.getPcbList().remove(pcb);
        pcb.getStensilList().remove(stencil);

        updateStencil(stencilId, stencil);
        pcbRepository.save(pcb);
    }
}
