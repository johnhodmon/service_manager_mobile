package ie.hodmon.computing.service_manager.model;

/**
 * Created by John on 21/02/2015.
 */
public class Product
{
    private int id;
    private String product_number;
    private String description;
    private int manufacturer_id;

    public Product(int id,String product_number,String description,int manufacturer_id)
    {
        this.id=id;
        this.product_number=product_number;
        this.description=description;
        this.manufacturer_id=manufacturer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }
}
