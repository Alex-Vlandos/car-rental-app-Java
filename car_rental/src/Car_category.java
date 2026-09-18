public class Car_category {
    private String size;
    private String price;
    private String type;

    public Car_category(String size,String price,String type){
        this.size = size;
        this.price = price;
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString(){
        return "Size: "+size+", Price: "+price+", Type: "+type;
    }
}
