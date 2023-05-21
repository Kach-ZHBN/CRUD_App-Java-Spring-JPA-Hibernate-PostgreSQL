package by.kach.PCBApp.services;

import by.kach.PCBApp.models.PCB;
import by.kach.PCBApp.repositories.PCBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PCBService {
    private final PCBRepository pcbRepository;

    @Autowired
    public PCBService(PCBRepository pcbRepository) {
        this.pcbRepository = pcbRepository;
    }

    public List<PCB> findAllPCB(){
        return pcbRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public PCB findPCBbyid(String id){
        return pcbRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean savePCB(PCB pcbToSave){
        if(findPCBbyid(pcbToSave.getId()) == null) {
            pcbRepository.save(pcbToSave);
            return true;
        }else
            return false;
    }
    @Transactional
    public PCB updatePCB(String id, PCB pcbToUpdate){
        pcbToUpdate.setId(id);
        return pcbRepository.save(pcbToUpdate);
    }
    @Transactional
    public void deletePCB(String id){
        PCB pcbToRemove = findPCBbyid(id);
        pcbRepository.delete(pcbToRemove);
    }


}
