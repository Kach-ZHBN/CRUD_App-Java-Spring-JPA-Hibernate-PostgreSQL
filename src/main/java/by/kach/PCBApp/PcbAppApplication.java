package by.kach.PCBApp;

import by.kach.PCBApp.models.Product;
import by.kach.PCBApp.repositories.ProductsRepository;
import by.kach.PCBApp.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PcbAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcbAppApplication.class, args);
	}

}
