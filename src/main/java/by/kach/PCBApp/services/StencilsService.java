package by.kach.PCBApp.services;

import by.kach.PCBApp.models.Stencil;
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

    @Autowired
    public StencilsService(StencilsRepository stencilsRepository) {
        this.stencilsRepository = stencilsRepository;
    }

    public List<Stencil> findAllStencils(){
        return stencilsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Stencil findStencilbyid(String id){
        return stencilsRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean saveStencil(Stencil stencilToSave){
        if(findStencilbyid(stencilToSave.getId()) == null) {
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
        Stencil stencilToRemove = findStencilbyid(id);
        stencilsRepository.delete(stencilToRemove);
    }
}
