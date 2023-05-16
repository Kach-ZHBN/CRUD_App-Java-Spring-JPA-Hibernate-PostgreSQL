package by.kach.PCBApp.repositories;

import by.kach.PCBApp.models.PCB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PCBRepository extends JpaRepository<PCB, String> {
}
