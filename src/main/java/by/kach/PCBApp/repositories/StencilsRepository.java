package by.kach.PCBApp.repositories;

import by.kach.PCBApp.models.Stencil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StencilsRepository extends JpaRepository<Stencil, String> {
}
