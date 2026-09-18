import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Rental{
    private Customer customer;
    private Car car;
    private long days_of_rental;
    private LocalDate start_date;
    private LocalDate end_date;
    public Rental(Customer customer,Car car,LocalDate start_date,LocalDate end_date){
        this.customer = customer;
        this.car = car;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public void setDays_of_rental(long days_of_rental) {
        this.days_of_rental = days_of_rental;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public long getDays_of_rental() {
        return days_of_rental;
    }

    public void setDays_of_rental(int days_of_rental) {
        this.days_of_rental = days_of_rental;
    }

    @Override
    public String toString() {
        LocalDate start_date  = this.start_date;
        LocalDate end_date = this.end_date;
        this.days_of_rental = ChronoUnit.DAYS.between(start_date, end_date);
        return this.car.toString()+" has been rented by" + this.customer.toString() + " from "+this.start_date.toString()+" to "+this.end_date.toString()+" (for"+this.days_of_rental+ " days!)";
    }
}
