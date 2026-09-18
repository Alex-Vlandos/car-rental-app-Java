import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class RentalGUIForm extends JFrame implements FormWithPanel,RefresherListener{
    private JButton carRental_btn;
    private JLabel customer;
    private JLabel car;
    private JLabel rental_days;
    private JPanel rental_panel;
    private JComboBox customer_dropdown;
    private JComboBox car_dropdown;
    private ConnectionToDatabase database;
    private JTextField txtFl_rental_days;
    private JFormattedTextField formattedTxt_startDate;
    private JFormattedTextField formattedTxt_endDate;
    private ArrayList<Car> carsAvailable;
    private ArrayList<Customer> customerRentals;
    private ArrayList<Rental> thisCarRentals;

    public RentalGUIForm() {
            refreshData();
            try {
                MaskFormatter dateMask = new MaskFormatter("####-##-##");
                dateMask.setPlaceholderCharacter('_');
                dateMask.setValidCharacters("0123456789");
                formattedTxt_startDate.setFormatterFactory(new DefaultFormatterFactory(dateMask));
                formattedTxt_endDate.setFormatterFactory(new DefaultFormatterFactory(dateMask));
            }catch(ParseException e){
                JOptionPane.showMessageDialog(null, "Invalid dates!");
                return;
            }
        carRental_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customer = (String) customer_dropdown.getSelectedItem();
                if(customer_dropdown.getSelectedItem().equals("Select customer")){
                    JOptionPane.showMessageDialog(null, "Please select a customer!");
                    return;
                }
                if(car_dropdown.getSelectedItem().equals("Select car")){
                    JOptionPane.showMessageDialog(null, "Please select a car!");
                    return;
                }
                Car car = (Car) car_dropdown.getSelectedItem();

                String start_date_string;
                String end_date_string;
                LocalDate start_date;
                LocalDate end_date;
                Car_category category = car.getCategory();
                String category_size = category.getSize();
                String category_price = category.getPrice();
                String category_type = category.getType();
                int car_id = car.getCar_id();
                String model = car.getModel();
                double cost_per_day = car.getCost_per_day();
                int cubic_capacity = car.getCubic_capacity();
                int seats = car.getSeats();
                String[] customer_attributes = customer.toString().split(", ");
                String customer_fname = customer_attributes[0].replace("Customer(First name: ", "").trim();
                String customer_lname = customer_attributes[1].replace("Last name: ", "").trim();
                String customer_sex = customer_attributes[2].replace("Sex: ", "").trim();
                String customer_address = customer_attributes[3].replace("Address: ", "").trim();
                String customer_email = customer_attributes[4].replace("Email: ", "").trim();
                String customer_phone = customer_attributes[5].replace("Phone: ", "").replace(")", "").trim();
                Car_category myCarCategory = new Car_category(category_size, category_price, category_type);
                Car myCar = new Car(car_id,myCarCategory,model,cost_per_day,cubic_capacity,seats);
                Customer myCustomer = new Customer(customer_fname,customer_lname,customer_sex,customer_address,customer_email,customer_phone);
//                Rental myRental = new Rental(myCustomer,myCar,start_date,end_date);
                try {
                    start_date_string = formattedTxt_startDate.getText();
                    end_date_string = formattedTxt_endDate.getText();
                    start_date = LocalDate.parse(start_date_string);
                    end_date = LocalDate.parse(end_date_string);
                    if(start_date_string.length()<10){
                        JOptionPane.showMessageDialog(null, "Invalid dates!Fill all spaces in the start date field");
                        return;
                    }
                    if(end_date_string.length()<10){
                        JOptionPane.showMessageDialog(null, "Invalid dates!Fill all spaces in the end date field");
                        return;
                    }
                    if(start_date.isBefore(LocalDate.now()) || end_date.isBefore(LocalDate.now())){
                        JOptionPane.showMessageDialog(null, "You must fill a date in the future!");
                        return;
                    }
                    long rental_days = ChronoUnit.DAYS.between(start_date, end_date);

                    if (rental_days <= 0) {
                        JOptionPane.showMessageDialog(null, "The end date must be after the start date of rental!");
                        return;
                    }
                    Connection connection = database.getConnection();
                    Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String find_customer_id_sql = "SELECT customer_id FROM customers WHERE first_name = '" + customer_fname + "' AND last_name = '"
                            + customer_lname + "' AND sex = '" + customer_sex + "' AND address = '"
                            + customer_address + "' AND email = '" + customer_email + "' AND phone = '" + customer_phone + "'";
                    ResultSet resultSet1 = statement1.executeQuery(find_customer_id_sql);
                    String customer_id_string;
                    int customer_id;
                    if (resultSet1.next()) {
                        customer_id_string = resultSet1.getString("customer_id");
                        customer_id = Integer.parseInt(customer_id_string);
                    } else {
                        System.out.println("There is no such customer!");
                        return;
                    }
                    Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String find_category_id_sql = "SELECT car_categories_id FROM car_categories WHERE car_size='" + category_size + "' AND car_price='"
                            + category_price + "' AND car_type='" + category_type + "'";
                    ResultSet resultSet2 = statement2.executeQuery(find_category_id_sql);
                    String category_id_string;
                    int category_id;
                    if (resultSet2.next()) {
                        category_id_string = resultSet2.getString("car_categories_id");
                        category_id = Integer.parseInt(category_id_string);
                    } else {
                        System.out.println("There is no such category!");
                        return;
                    }

                        Statement statementCheckCustomerSameRental = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String sqlCheckSamePeriodRental = "SELECT * FROM rentals " +
                            "WHERE car_id = " + car_id +
                            " AND (start_date <= '" + end_date_string + "' AND end_date >= '" + start_date_string + "')";
                        ResultSet resultSetCheck = statementCheckCustomerSameRental.executeQuery(sqlCheckSamePeriodRental);
                        if (resultSetCheck.next()) {
                            JOptionPane.showMessageDialog(null, "This car has already been rented for this period of time!");
                            return;
                        }
                        Statement statement4 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String sql = "INSERT INTO rentals(customer_id,car_id,days_of_rental,start_date,end_date) VALUES ('" + customer_id + "','" + car_id + "','" + rental_days + "','" + start_date + "','" + end_date + "')";
                        int rowsInserted = statement4.executeUpdate(sql);
                        if (rowsInserted > 0) {
                            String message = "Rental completed successfully for the following customer:\n" +
                                    "Customer with credentials:\n" + myCustomer.toString().replace("Customer(", "") + "\n" +
                                    "rented the following car with attributes:\n" + myCar.toString().replace("Category( ", "").replace(")", "")+" " +
                                    "for " + rental_days + " days";
                            JOptionPane.showMessageDialog(null, message);
                            System.out.println("Succesful insertion!");
                        }


                }catch(DateTimeParseException e3){
                    JOptionPane.showMessageDialog(null, "Invalid dates!");
                    return;
                }
                catch (SQLException e1) {
                    throw new RuntimeException(e1);
                }
            }
        });

    }

    public ArrayList<Car> getCarsAvailable() {
        return carsAvailable;
    }

    public void setCarsAvailable(ArrayList<Car> carsAvailable) {
        this.carsAvailable = carsAvailable;
    }

    public ArrayList<Customer> getCustomerRentals() {
        return customerRentals;
    }

    public void setCustomerRentals(ArrayList<Customer> customerRentals) {
        this.customerRentals = customerRentals;
    }

    public ArrayList<Rental> getThisCarRentals() {
        return thisCarRentals;
    }

    public void setThisCarRentals(ArrayList<Rental> thisCarRentals) {
        this.thisCarRentals = thisCarRentals;
    }

    public JButton getCarRental_btn() {
        return carRental_btn;
    }

    public void setCarRental_btn(JButton carRental_btn) {
        this.carRental_btn = carRental_btn;
    }

    public JTextField getTxtFl_rental_days() {
        return txtFl_rental_days;
    }

    public void setTxtFl_rental_days(JTextField txtFl_rental_days) {
        this.txtFl_rental_days = txtFl_rental_days;
    }

    public JLabel getCustomer() {
        return customer;
    }

    public void setCustomer(JLabel customer) {
        this.customer = customer;
    }

    public JLabel getCar() {
        return car;
    }

    public void setCar(JLabel car) {
        this.car = car;
    }

    public JLabel getRental_days() {
        return rental_days;
    }

    public void setRental_days(JLabel rental_days) {
        this.rental_days = rental_days;
    }

    public JPanel getMain_panel() {
        return this.rental_panel;
    }

    public void setMain_panel(JPanel main_panel) {
        this.rental_panel = main_panel;
    }

    @Override
    public void refreshData() {
        reloadCustomersAndCars();
    }
    public void reloadCustomersAndCars(){
        try {
            database = new ConnectionToDatabase();
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM customers";
            ResultSet resultSet = statement.executeQuery(sql);
            customer_dropdown.removeAllItems();
            customer_dropdown.addItem("Select customer");
            while (resultSet.next()) {
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                Customer customer = new Customer(first_name, last_name, sex, address, email, phone);
                String customerDescription = customer.toString();
                customer_dropdown.addItem(customerDescription);
//                customerRentals.add(customer);
            }

            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sqlCars = "SELECT * FROM car_categories " +
                    "INNER JOIN cars ON car_categories_id=category_id";
            ResultSet resultSet1 = statement1.executeQuery(sqlCars);
            car_dropdown.removeAllItems();
            car_dropdown.addItem("Select car");

            while (resultSet1.next()) {
                String car_size = resultSet1.getString("car_size");
                String car_price = resultSet1.getString("car_price");
                String car_type = resultSet1.getString("car_type");
//                String category_id = resultSet1.getString("category_id");
                int car_id = Integer.parseInt(resultSet1.getString("car_id"));
                String car_model = resultSet1.getString("car_model");
                String cost_per_day_string = resultSet1.getString("cost_per_day");
                String cubic_capacity_string = resultSet1.getString("cubic_capacity");
                String number_of_seats_string = resultSet1.getString("number_of_seats");

                double cost_per_day = Double.parseDouble(cost_per_day_string);
                int cubic_capacity = Integer.parseInt(cubic_capacity_string);
                int number_of_seats = Integer.parseInt(number_of_seats_string);

                Car_category car_category = new Car_category(car_size, car_price, car_type);
                Car car = new Car(car_id,car_category, car_model, cost_per_day, cubic_capacity, number_of_seats);
                car_dropdown.addItem(car);
//                carsAvailable.add(car);
            }

        }catch (SQLException e1){
            throw new RuntimeException(e1);
        }
}
}
