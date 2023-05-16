package by.kach.PCBApp.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Stencil")
public class Stencil {

    @Id
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "pcb_side")
    private PCBSide pcbSide;

    @Column(name = "thickness")
    private double thickness;

    @Column(name = "area")
    private double area;

    @ManyToMany
    @JoinTable(
            name = "pcb_stencil",
            joinColumns = @JoinColumn(name = "stencil_id"),
            inverseJoinColumns = @JoinColumn(name = "pcb_id"))
    private List<PCB> pcbList;

    public Stencil() {
    }

    public Stencil(String id, PCBSide pcbSide, double thickness, double area) {
        this.id = id;
        this.pcbSide = pcbSide;
        this.thickness = thickness;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PCBSide getPcbSide() {
        return pcbSide;
    }

    public void setPcbSide(PCBSide pcbSide) {
        this.pcbSide = pcbSide;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public List<PCB> getPcbList() {
        return pcbList;
    }

    public void setPcbList(List<PCB> pcbList) {
        this.pcbList = pcbList;
    }

    @Override
    public String toString() {
        return "Stencil{" +
                "id='" + id + '\'' +
                ", pcbSide=" + pcbSide +
                ", thickness=" + thickness +
                ", area=" + area +
                '}';
    }
}
