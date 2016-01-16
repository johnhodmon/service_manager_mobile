package ie.hodmon.computing.service_manager.model;

/**
 * Created by John on 23/02/2015.
 */
public class Part

{
    private int id;
    private String part_number;
    private String description;
    private double cost;
    private int quantity_in_stock;

    public Part(String part_number, String description,double cost,int quantity_in_stock)
    {
        this.id=id;
        this.part_number=part_number;
        this.description=description;
        this.cost=cost;
        this.quantity_in_stock=quantity_in_stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }
}
