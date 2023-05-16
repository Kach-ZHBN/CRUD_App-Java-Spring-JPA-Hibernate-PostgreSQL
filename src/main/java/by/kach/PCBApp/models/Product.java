package by.kach.PCBApp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "pcb_id", referencedColumnName = "id")
    private PCB pcb;

    public Product(){}

    public Product(String id, String name, PCB pcb) {
        this.id = id;
        this.name = name;
        this.pcb = pcb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
