import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MainGUI extends JFrame implements FormWithPanel {
    private JLabel my_dashboard;
    private JButton chooseCarCategoryButton;
    private JButton chooseCarAttributesButton;
    private JButton chooseRentalOptionsButton;
    private JButton fillYourCredentialsButton;
    private JButton rentCarButton;
    private JPanel main_panel;
    private JTextField searchCarTextField;
    private JButton searchCarButton;
    private JButton searchCustomerButton;
    private ArrayList<Car> carsSearchList = new ArrayList<>();
    private ArrayList<Customer> customersSearchList = new ArrayList<>();
    private JComboBox selectCustomerDropdown;
    private JComboBox selectCarDropdown;
    private JButton seeAvailableCarsButton;
    private JTextField searchCustomerTextField;
    private JButton[] buttonList = new JButton[4];
    private FormWithPanel[] GUIList = new FormWithPanel[4];
    private JFrame[] forms = new JFrame[4];
    public MainGUI() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Κέντρο οθόνης

        main_panel = new JPanel(new GridBagLayout());
        main_panel.setBackground(new Color(43, 45, 48)); // ίδιο με GUI σου
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding γύρω από κάθε στοιχείο

        // Row 0 - Title Label (προαιρετικό)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        main_panel.add(new JLabel("Dashboard"), gbc);

        // Row 1 - Search bar και κουμπί (στην ίδια γραμμή)
        gbc.gridy = 1;
        gbc.gridwidth = 1; // reset σε 1
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Row 1 - Διπλή Αναζήτηση (Αυτοκίνητο + Πελάτης)
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Πεδίο Αναζήτησης Αυτοκινήτου - Στήλη 0
        gbc.gridx = 0;
        searchCarTextField = new JTextField(15);
        main_panel.add(searchCarTextField, gbc);

// Κουμπί Αναζήτησης Αυτοκινήτου - Στήλη 1
        gbc.gridx = 1;
        searchCarButton = new JButton("Search Car");
        main_panel.add(searchCarButton, gbc);

// Πεδίο Αναζήτησης Πελάτη - Στήλη 2
        gbc.gridx = 2;
        searchCustomerTextField = new JTextField(15);
        main_panel.add(searchCustomerTextField, gbc);

// Κουμπί Αναζήτησης Πελάτη - Στήλη 3
        gbc.gridx = 3;
        searchCustomerButton = new JButton("Search Customer");
        main_panel.add(searchCustomerButton, gbc);

        // Row 2 - Dropdowns & κουμπιά ενοικίασης
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        selectCustomerDropdown = new JComboBox<>();
        selectCustomerDropdown.setRenderer(new TooltipComboBoxRenderer());
        selectCustomerDropdown.setPreferredSize(new Dimension(200, 30));
//        selectCustomerDropdown.setToolTipText("Select customer to see their car rentals");
        selectCustomerDropdown.addItem("Select customer to see their car rentals");
        main_panel.add(selectCustomerDropdown, gbc);

        gbc.gridx = 1;
        seeAvailableCarsButton = new JButton("See available cars");
        main_panel.add(seeAvailableCarsButton, gbc);

        gbc.gridx = 2;
        selectCarDropdown = new JComboBox<>();
        selectCarDropdown.setRenderer(new TooltipComboBoxRenderer());
        selectCarDropdown.setPreferredSize(new Dimension(200, 30));
//        selectCarDropdown.setToolTipText("Select car to see its rentals by customers");
        selectCarDropdown.addItem("Select car to see its rentals by customers");
        main_panel.add(selectCarDropdown, gbc);

        // Row 3 - Κατηγορία, Αυτοκίνητο, Credentials, Ενοικίαση
        gbc.gridy = 3;
        gbc.gridx = 0;
        chooseCarCategoryButton = new JButton("Create a category");
        buttonList[0] = chooseCarCategoryButton;
        main_panel.add(chooseCarCategoryButton, gbc);

        gbc.gridx = 1;
        chooseCarAttributesButton = new JButton("Create a car");
        buttonList[1] = chooseCarAttributesButton;
        main_panel.add(chooseCarAttributesButton, gbc);

        gbc.gridx = 2;
        fillYourCredentialsButton = new JButton("Fill your credentials");
        buttonList[3] = fillYourCredentialsButton;
        main_panel.add(fillYourCredentialsButton, gbc);

        gbc.gridx = 3;
        chooseRentalOptionsButton = new JButton("Rent a car");
        buttonList[2] = chooseRentalOptionsButton;
        main_panel.add(chooseRentalOptionsButton, gbc);

        add(main_panel);
        pack();
        setVisible(true);
//        ArrayList<Integer> customersIdArray = new ArrayList<>();
        ArrayList<Integer> carsIdArray =new ArrayList<>();
        GUIList[0] = new CarCategoryGUIForm();
        GUIList[1] = new CarGUIForm();
        GUIList[2] = new RentalGUIForm();
        GUIList[3] = new CustomerGUIForm();
        forms[0] = (CarCategoryGUIForm) GUIList[0];
        forms[1] = (CarGUIForm) GUIList[1];
        forms[2] = (RentalGUIForm) GUIList[2];
        forms[3] = (CustomerGUIForm) GUIList[3];
        ((CarCategoryGUIForm) GUIList[0]).setCustomListenerCategoryToCar((CarGUIForm) GUIList[1]);
        ((CarGUIForm) GUIList[1]).setCustomListenerCarToRental((RentalGUIForm) GUIList[2]);
        ((CustomerGUIForm) GUIList[3]).setCustomListenerCustomerToRental((RentalGUIForm) GUIList[2]);

        try{
            ConnectionToDatabase database = new ConnectionToDatabase();
            Connection connection = database.getConnection();

            String sqlGetCarsAvailable = "SELECT * " +
                    "FROM car_categories INNER JOIN cars ON cars.category_id=car_categories_id " +
                    "LEFT JOIN rentals ON cars.car_id=rentals.car_id " +
                    "LEFT JOIN customers ON rentals.customer_id=customers.customer_id " +
                    "ORDER BY cars.car_id,rentals.start_date";

            String sqlGetCustomers = "SELECT * FROM customers";
            String sqlGetAllCars = "SELECT * FROM cars INNER JOIN car_categories " +
                    "ON cars.category_id=car_categories.car_categories_id";

            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet1 = statement1.executeQuery(sqlGetCustomers);
            Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet2 = statement2.executeQuery(sqlGetAllCars);
            if(!resultSet1.next()){
                JOptionPane.showMessageDialog(null, "There are no customers created yet!");
            }
            resultSet1.previous();
            while(resultSet1.next()){
//                int customer_id = Integer.parseInt(resultSet1.getString("customer_id"));
                String fname = resultSet1.getString("first_name");
                String lname = resultSet1.getString("last_name");
                String sex = resultSet1.getString("sex");
                String address = resultSet1.getString("address");
                String email = resultSet1.getString("email");
                String phone = resultSet1.getString("phone");
                Customer customer = new Customer(fname,lname,sex,address,email,phone);
                selectCustomerDropdown.addItem(customer.toString());
//                customersIdArray.add(customer_id);
                customersSearchList.add(customer);
            }
            if(!resultSet2.next()){
                JOptionPane.showMessageDialog(null, "There are no cars created yet!");
            }
            resultSet2.previous();
            while(resultSet2.next()){
                int car_id = resultSet2.getInt("car_id");
                String size = resultSet2.getString("car_size");
                String price = resultSet2.getString("car_price");
                String type = resultSet2.getString("car_type");
                String model = resultSet2.getString("car_model");
                String cost_per_day_text = resultSet2.getString("cost_per_day");
                Double cost_per_day = Double.parseDouble(cost_per_day_text);
                String cubic_capacity_string = resultSet2.getString("cubic_capacity");
                int cubic_capacity = Integer.parseInt(cubic_capacity_string);
                String number_of_seats_string = resultSet2.getString("number_of_seats");
                int number_of_seats = Integer.parseInt(number_of_seats_string);
                Car_category category = new Car_category(size,price,type);
                Car car = new Car(car_id,category,model, cost_per_day, cubic_capacity, number_of_seats);
                selectCarDropdown.addItem(car.toString());
                carsSearchList.add(car);
            }
            StringBuilder searchCarsBuilder = new StringBuilder();
            searchCarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchEntry = searchCarTextField.getText().toLowerCase();
                    if(searchEntry.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please insert some text in the car search field!");
                        return;
                    }
                    for(Car car : carsSearchList){
                        String category = String.valueOf(car.getCategory());
//                        System.out.println(category);
                        if(car.getModel().toLowerCase().contains(searchEntry.toLowerCase()) || String.valueOf(car.getSeats()).contains(searchEntry.toLowerCase()) ||
                                category.toLowerCase().contains(searchEntry.toLowerCase()) || String.valueOf(car.getCubic_capacity()).contains(searchEntry.toLowerCase()) ||
                                String.valueOf(car.getCost_per_day()).contains(searchEntry.toLowerCase())){
                            searchCarsBuilder.append(car+"\n");
                        }
                    }JTextArea textArea = new JTextArea(searchCarsBuilder.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    if(searchCarsBuilder.isEmpty()){
                        JOptionPane.showMessageDialog(null, "None cars found!");
                        searchCarsBuilder.setLength(0);
                    }else {
                        JOptionPane.showMessageDialog(null, scrollPane, "Cars found", JOptionPane.INFORMATION_MESSAGE);
                        searchCarsBuilder.setLength(0);
                    }
                }
            });
            StringBuilder searchCustomersBuilder = new StringBuilder();
            searchCustomerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchEntry = searchCustomerTextField.getText().toLowerCase();
                    if(searchEntry.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please insert some text in the customer search field!");
                        return;
                    }
                    for(Customer customer : customersSearchList){
                        if(customer.getFirst_name().toLowerCase().contains(searchEntry.toLowerCase()) || customer.getLast_name().toLowerCase().contains(searchEntry.toLowerCase()) ||
                                customer.getSex().toLowerCase().contains(searchEntry.toLowerCase()) || customer.getAddress().toLowerCase().contains(searchEntry) ||
                                customer.getEmail().toLowerCase().contains(searchEntry.toLowerCase()) || customer.getPhone().toLowerCase().contains(searchEntry)){
                            searchCustomersBuilder.append(customer+"\n");
                        }
                    }JTextArea textArea = new JTextArea(searchCustomersBuilder.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    if(searchCustomersBuilder.isEmpty()){
                        JOptionPane.showMessageDialog(null, "None customers found!");
                        searchCustomersBuilder.setLength(0);
                    }else {
                        JOptionPane.showMessageDialog(null, scrollPane, "Customers found", JOptionPane.INFORMATION_MESSAGE);
                        searchCustomersBuilder.setLength(0);
                    }
                }
            });
            selectCustomerDropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCustomer = (String) selectCustomerDropdown.getSelectedItem();
                    if(selectedCustomer == null || selectedCustomer.equals("Select customer to see their car rentals")) {
                        JOptionPane.showMessageDialog(null, "You must select a customer!");
                    }
                    else{
                        String[] splittedCustomerInformation = selectedCustomer.split(", ");
                        String fname = splittedCustomerInformation[0].replace("Customer(First name: ", "");
                        String lname = splittedCustomerInformation[1].replace("Last name: ", "");
                        String sex = splittedCustomerInformation[2].replace("Sex: ", "");
                        String address = splittedCustomerInformation[3].replace("Address: ", "");
                        String email = splittedCustomerInformation[4].replace("Email: ", "");
                        String phone = splittedCustomerInformation[5].replace("Phone: ", "").replace(")", "");
                        try {
                            String sqlFindCustomerRentals = "SELECT * FROM customers INNER JOIN rentals " +
                                    "ON rentals.customer_id=customers.customer_id " +
                                    "INNER JOIN cars ON rentals.car_id=cars.car_id " +
                                    "INNER JOIN car_categories ON cars.category_id=car_categories.car_categories_id " +
                                    "WHERE first_name = '" + fname +
                                    "' AND last_name = '" + lname + "' AND sex = '" + sex +
                                    "' AND address = '" + address + "' AND email = '" + email +
                                    "' AND phone = '" + phone + "'";
//                            System.out.println(sqlFindCustomerRentals);


                            Statement statement3 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet resultSet3 = statement3.executeQuery(sqlFindCustomerRentals);
                            Customer customer = new Customer(fname,lname,sex,address,email,phone);
                            StringBuilder customerRentalsList = new StringBuilder();
                            while(resultSet3.next()){
                                int car_id = Integer.parseInt(resultSet3.getString("car_id"));
                                String size = resultSet3.getString("car_size");
                                String price = resultSet3.getString("car_price");
                                String type = resultSet3.getString("car_type");
                                Car_category category = new Car_category(size, price, type);
                                String model = resultSet3.getString("car_model");
                                String cost_per_day_text = resultSet3.getString("cost_per_day");
                                Double cost_per_day = Double.parseDouble(cost_per_day_text);
                                String cubic_capacity_string = resultSet3.getString("cubic_capacity");
                                int cubic_capacity = Integer.parseInt(cubic_capacity_string);
                                String number_of_seats_string = resultSet3.getString("number_of_seats");
                                int number_of_seats = Integer.parseInt(number_of_seats_string);
                                LocalDate start_date = LocalDate.parse(resultSet3.getString("start_date"));
                                LocalDate end_date = LocalDate.parse(resultSet3.getString("end_date"));
                                long rental_days = ChronoUnit.DAYS.between(start_date, end_date);
                                Car car = new Car(car_id,category,model,cost_per_day, cubic_capacity, number_of_seats);
                                String customerRentalsDescription = car.toString()+" from "+ start_date +" to " +end_date +"(" + rental_days+" days)";
                                customerRentalsList.append(customerRentalsDescription+"\n");
                            }
                            JTextArea textArea = new JTextArea(customerRentalsList.toString());
                            textArea.setEditable(false);
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                            JOptionPane.showMessageDialog(null, scrollPane, "Customer's Rentals", JOptionPane.INFORMATION_MESSAGE);
                            customerRentalsList.setLength(0);
                        }catch(SQLException e1){
                            throw new RuntimeException(e1);
                        }
                    }
                }
            });
            selectCarDropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCar = (String)selectCarDropdown.getSelectedItem();
                    if(selectedCar == null || selectedCar.equals("Select car to see its rentals by customers")) {
                        JOptionPane.showMessageDialog(null, "You must select a car!");
                    }
                    else{
                        String[] splittedCarInformation = selectedCar.split(", ");
                        String category_size = splittedCarInformation[0].replace("Category( Size: ", "");
                        String category_price = splittedCarInformation[1].replace("Price: ", "");
                        String category_type = splittedCarInformation[2].replace("Type: ", "").replace(")", "");;
                        String model = splittedCarInformation[3].replace("Model: ", "");
                        String cost_per_day_string = splittedCarInformation[4].replace("Cost per day: ", "");
                        double cost_per_day = Double.parseDouble(cost_per_day_string);
                        String cubic_capacity_string = splittedCarInformation[5].replace("Cubic capacity: ", "");
                        int cubic_capacity = Integer.parseInt(cubic_capacity_string);
                        String seats_string = splittedCarInformation[6].replace("Seats: ", "");
                        int seats = Integer.parseInt(seats_string);

                        try {
                            String sqlFindCarRentals = "SELECT * FROM car_categories " +
                                    "INNER JOIN cars ON car_categories.car_categories_id=cars.category_id "+
                                    "INNER JOIN rentals ON rentals.car_id=cars.car_id " +
                                    "INNER JOIN customers ON rentals.customer_id=customers.customer_id " +
                                    "WHERE car_size = '" + category_size+
                                    "' AND car_price = '" + category_price+ "' AND car_type = '" + category_type +
                                    "' AND car_model = '" + model + "' AND cost_per_day = " +cost_per_day  +
                                    " AND cubic_capacity = " + cubic_capacity +
                                    " AND number_of_seats= "+ seats;
//                            System.out.println(sqlFindCarRentals);
                            StringBuilder carAndCustomerRentalsList = new StringBuilder();
                            Statement statement4 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet resultSet4 = statement4.executeQuery(sqlFindCarRentals);
                            while(resultSet4.next()){
                                int car_id = Integer.parseInt(resultSet4.getString("car_id"));
                                String size = resultSet4.getString("car_size");
                                String price = resultSet4.getString("car_price");
                                String type = resultSet4.getString("car_type");
//                                Car_category category = new Car_category(size, price, type);
//                                Car car = new Car(category,model, cost_per_day, cubic_capacity, seats);
                                String rental_days = resultSet4.getString("days_of_rental");
                                String start_date = resultSet4.getString("start_date");
                                String end_date = resultSet4.getString("end_date");
                                String fname = resultSet4.getString("first_name");
                                String lname = resultSet4.getString("last_name");
                                String sex = resultSet4.getString("sex");
                                String address = resultSet4.getString("address");
                                String email = resultSet4.getString("email");
                                String phone = resultSet4.getString("phone");
                                Customer customer = new Customer(fname,lname,sex,address, email, phone);
                                String customerAndCarRentalsDescription = customer.toString()+" rented this car from "+start_date+" to "+end_date+",i.e. for "+rental_days+" days ";
                                carAndCustomerRentalsList.append(customerAndCarRentalsDescription+"\n");
                            }
                            JTextArea textArea = new JTextArea(carAndCustomerRentalsList.toString());
                            textArea.setEditable(false);
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                            JOptionPane.showMessageDialog(null, scrollPane, "Car's Rentals", JOptionPane.INFORMATION_MESSAGE);
                            carAndCustomerRentalsList.setLength(0);
                        }catch(SQLException e1){
                            throw new RuntimeException(e1);
                        }
                    }
                }
            });

        seeAvailableCarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder carsList = new StringBuilder();
                try {
                    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = statement.executeQuery(sqlGetCarsAvailable);
                    while (resultSet.next()) {
                        ArrayList<Rental> availableCarRentals = new ArrayList<>();
                        String size = resultSet.getString("car_categories.car_size");
                        String price = resultSet.getString("car_categories.car_price");
                        String type = resultSet.getString("car_categories.car_type");
                        int car_id = resultSet.getInt("cars.car_id");
                        String category_id = resultSet.getString("cars.category_id");
                        String car_model = resultSet.getString("cars.car_model");
                        String cost_per_day_string = resultSet.getString("cars.cost_per_day");
                        String cubic_capacity_string = resultSet.getString("cars.cubic_capacity");
                        String number_of_seats_string = resultSet.getString("cars.number_of_seats");
                        String first_name = resultSet.getString("first_name");
                        String last_name = resultSet.getString("last_name");
                        String sex = resultSet.getString("sex");
                        String address = resultSet.getString("address");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone");
                        double cost_per_day = Double.parseDouble(cost_per_day_string);
                        int cubic_capacity = Integer.parseInt(cubic_capacity_string);
                        int number_of_seats = Integer.parseInt(number_of_seats_string);
                        Car_category availableCarCategory = new Car_category(size, price, type);
                        Car availableCar = new Car(car_id, availableCarCategory, car_model, cost_per_day, cubic_capacity, number_of_seats);
                        carsList.append(availableCar);
                        do {
                            int tempCarId = resultSet.getInt("cars.car_id");
                            if(tempCarId != car_id) {
                                resultSet.previous();
                                break;
                            }
                            String rental_id = resultSet.getString("rental_id");
                             if(rental_id !=null){
                                    String start_date_string = resultSet.getString("start_date");
                                    String end_date_string = resultSet.getString("end_date");
                                    LocalDate start_date = LocalDate.parse(start_date_string);
                                    LocalDate end_date = LocalDate.parse(end_date_string);
                                    Customer customer = new Customer(first_name, last_name, sex, address, email, phone);
                                    Rental rental = new Rental(customer, availableCar, start_date, end_date);
                                    availableCarRentals.add(rental);
                                }

                        }while(resultSet.next());
                        carsList.append(getAvailableCarRentalsDates(availableCarRentals)).append("\n");
                        if(availableCarRentals.isEmpty()){
                            carsList.append("This car has not been rented yet!\n");
                        }
                    }
                    JTextArea textArea = new JTextArea(carsList.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                    if (carsList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no available cars!", "Available Cars", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, scrollPane, "Available Cars", JOptionPane.INFORMATION_MESSAGE);
                    carsList.setLength(0);
                } catch (SQLException exe) {
                    throw new RuntimeException(exe);
                }
            }
        });
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        for (int i = 0; i < 4; i++) {
            final int index = i;
            buttonList[index].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forms[index].setContentPane(GUIList[index].getMain_panel());
                    forms[index].setSize(500, 500);
                    forms[index].setVisible(true);
                    forms[index].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            });

        }
    }
    public String getAvailableCarRentalsDates(ArrayList<Rental> rentals){
        StringBuilder resultString = new StringBuilder();
        LocalDate now = LocalDate.now();
            LocalDate current = now;
            for (Rental rental : rentals) {
                LocalDate start_date = rental.getStart_date();
                LocalDate end_date = rental.getEnd_date();
                if (current.isBefore(start_date)) {
                    resultString.append("\nfrom " + current + " to " + start_date.minusDays(1));
                }
                if (current.isBefore(end_date.plusDays(1))) {
                    current = end_date.plusDays(1);
                }
            }
        resultString.append("\nfrom " +current+" and after!");
        return resultString.toString();
    }
    public JLabel getMy_dashboard() {
        return my_dashboard;
    }

    public JFrame[] getForms() {
        return forms;
    }

    public void setForms(JFrame[] forms) {
        this.forms = forms;
    }

    public void setMy_dashboard(JLabel my_dashboard) {
        this.my_dashboard = my_dashboard;
    }

    public JButton getChooseCarCategoryButton() {
        return chooseCarCategoryButton;
    }

    public void setChooseCarCategoryButton(JButton chooseCarCategoryButton) {
        this.chooseCarCategoryButton = chooseCarCategoryButton;
    }

    public JButton getChooseCarAttributesButton() {
        return chooseCarAttributesButton;
    }

    public void setChooseCarAttributesButton(JButton chooseCarAttributesButton) {
        this.chooseCarAttributesButton = chooseCarAttributesButton;
    }

    public JButton getChooseRentalOptionsButton() {
        return chooseRentalOptionsButton;
    }

    public void setChooseRentalOptionsButton(JButton chooseRentalOptionsButton) {
        this.chooseRentalOptionsButton = chooseRentalOptionsButton;
    }

    public JButton getFillYourCredentialsButton() {
        return fillYourCredentialsButton;
    }

    public void setFillYourCredentialsButton(JButton fillYourCredentialsButton) {
        this.fillYourCredentialsButton = fillYourCredentialsButton;
    }

    public JButton getRentCarButton() {
        return rentCarButton;
    }

    public void setRentCarButton(JButton rentCarButton) {
        this.rentCarButton = rentCarButton;
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public void setMain_panel(JPanel main_panel) {
        this.main_panel = main_panel;
    }

    public JButton[] getButtonList() {
        return buttonList;
    }

    public void setButtonList(JButton[] buttonList) {
        this.buttonList = buttonList;
    }

    public FormWithPanel[] getGUIList() {
        return GUIList;
    }

    public void setGUIList(FormWithPanel[] GUIList) {
        this.GUIList = GUIList;
    }

}

