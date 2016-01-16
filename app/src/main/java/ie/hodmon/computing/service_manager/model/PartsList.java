package ie.hodmon.computing.service_manager.model;

/**
 * Created by John on 23/02/2015.
 */
public class PartsList
{
   private int id;
   private int part_id;
   private int product_id;
   private int quantity;

    public PartsList(int id,int part_id,int product_id, int quantity)
    {
        this.id=id;
        this.part_id=part_id;
        this.product_id=product_id;
        this.quantity=quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
