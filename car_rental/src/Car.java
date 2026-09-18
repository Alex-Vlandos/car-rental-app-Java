public class Car {
    private int car_id;
    private Car_category category;
    private String model;
    private double cost_per_day;
    private int cubic_capacity;
    private int seats;
    public Car(int car_id,Car_category category,String model,double cost_per_day,int cubic_capacity,int seats){
        this.car_id = car_id;
        this.category = category;
        this.model = model;
        this.cost_per_day = cost_per_day;
        this.cubic_capacity = cubic_capacity;
        this.seats = seats;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public void setCubic_capacity(int cubic_capacity) {
        this.cubic_capacity = cubic_capacity;
    }

    public Car_category getCategory() {
        return category;
    }

    public void setCategory(Car_category category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getCost_per_day() {
        return cost_per_day;
    }

    public void setCost_per_day(double cost_per_day) {
        this.cost_per_day = cost_per_day;
    }

    public int getCubic_capacity() {
        return cubic_capacity;
    }


    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    @Override
    public String toString(){
        return "Category( "+this.category.toString()+"), Model: "+this.model+", Cost per day: "+this.cost_per_day+", Cubic capacity: "+this.cubic_capacity +", Seats: "+this.seats;
    }
}
