package by.kach.PCBApp.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pcb")
public class PCB {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "pcb")
    private List<Product> productList;

    @ManyToMany(mappedBy = "pcbList")
    private List<Stencil> stensilList;

    public PCB() {
    }

    public PCB(String id, String name, List<Product> productList) {
        this.id = id;
        this.name = name;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Stencil> getStensilList() {
        return stensilList;
    }

    public void setStensilList(List<Stencil> stensilList) {
        this.stensilList = stensilList;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", productList=" + productList +
                '}';
    }
}
