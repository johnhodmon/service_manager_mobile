package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 17/01/16.
 */
public class CustomerProduct
{
    private int id;
    private int customer_id;
    private int product_id;
    private String serial_number;

    public CustomerProduct(int id, int customer_id, int product_id,String serial_number)
    {
        this.id=id;
        this.customer_id=customer_id;
        this.product_id=product_id;
        this.serial_number=serial_number;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }
}
