package by.kach.PCBApp.repositories;

import by.kach.PCBApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, String> {

}
