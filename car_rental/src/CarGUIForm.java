import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CarGUIForm extends JFrame implements FormWithPanel,RefresherListener {
    private JTextField txtFl_model;
    private JTextField txtFl_cost_per_day;
    private JTextField txtFl_cubic_capacity;
    private JTextField txtFl_number_of_seats;
    private JButton car_btn;
    private JLabel model;
    private JLabel cost_per_day;
    private JLabel cubic_capacity;
    private JLabel seats;
    private JPanel car_panel;
    private JLabel category;
    private JComboBox dropdown_category;
    private Connection connection;
    private RefresherListener customListenerCarToRental;
    private ArrayList<String> category_ids = new ArrayList<String>();
    public CarGUIForm() {
        refreshData();
        car_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    String model = txtFl_model.getText().trim();
                    String cost_per_day_text = txtFl_cost_per_day.getText().replace(",", ".").trim();
                    String cubic_capacity_string = txtFl_cubic_capacity.getText().trim();
                    String number_of_seats_string = txtFl_number_of_seats.getText().trim();
                    String dropdown_item = (String) dropdown_category.getSelectedItem();

                if(dropdown_item == null || dropdown_item.equals("Choose existing category")){
                    JOptionPane.showMessageDialog(null,"You must choose a car category first in order for you to proceed!");
                    return;
                }
                if(model.trim().isEmpty() || cubic_capacity_string.trim().isEmpty() || number_of_seats_string.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }
                String[] characteristics = new String[3];
                characteristics = dropdown_item.split(", ");

                String size = characteristics[0].replace("Car size: ", "");
                String price = characteristics[1].replace("Car price: ", "");
                String type = characteristics[2].replace("Car type: ", "");

                try {
                    Double cost_per_day = Double.parseDouble(cost_per_day_text);
                    int cubic_capacity = Integer.parseInt(cubic_capacity_string);
                    int number_of_seats = Integer.parseInt(number_of_seats_string);
                    Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    String sql_id_finder = "SELECT car_categories_id FROM car_categories WHERE car_size='" + size + "'AND car_price='" + price + "'AND car_type='" + type + "'";
                    ResultSet resultSet = statement1.executeQuery(sql_id_finder);
                    if (resultSet.next()) {
                        String selectedId = resultSet.getString("car_categories_id");
                        int real_id = Integer.parseInt(selectedId);

                        Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String car = "('" + real_id + "', '" + model + "', '" + cost_per_day + "','" + cubic_capacity + "','" + number_of_seats + "')";

                        int rowsInserted = statement2.executeUpdate("INSERT INTO cars(category_id,car_model,cost_per_day,cubic_capacity,number_of_seats) VALUES " + car);
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(null, "Successful insertion of car data!");
                            System.out.println("Successful insertion of data!");
                            if(customListenerCarToRental != null){
                                customListenerCarToRental.refreshData();
                            }
                        }
                    }

                }catch(NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "The cost per day field must be a real number with two decimals,\n" +
                            "the cubic capacity must be an integer and \n" +
                            "the number of seats must be also an integer!");
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    throw new RuntimeException(ex);
                }
                BigDecimal cost = new BigDecimal(cost_per_day_text.trim());
                if (cost.scale() > 2) {
                    JOptionPane.showMessageDialog(null, "The cost per day field must have maximum 2 decimals!");
                    return;
                }

            }
        });
    }
    @Override
    public void refreshData(){
        reloadCategories();
    }
    public void reloadCategories(){
        try {
            ConnectionToDatabase database = new ConnectionToDatabase();
            connection = database.getConnection();
            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM car_categories");
            dropdown_category.removeAllItems();
            dropdown_category.addItem("Choose existing category");
            while (resultSet.next()) {
                String car_size = resultSet.getString("car_size");
                String car_price = resultSet.getString("car_price");
                String car_type = resultSet.getString("car_type");
                String category_id = resultSet.getString("car_categories_id");
                category_ids.add(category_id);
                dropdown_category.addItem("Car size: " + car_size + ", Car price: " + car_price + ", Car type: " + car_type);
            }
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
    }

    public JTextField getTxtFl_model() {
        return txtFl_model;
    }

    public void setTxtFl_model(JTextField txtFl_model) {
        this.txtFl_model = txtFl_model;
    }

    public RefresherListener getCustomListenerCarToRental() {
        return customListenerCarToRental;
    }

    public void setCustomListenerCarToRental(RefresherListener customListenerCarToRental) {
        this.customListenerCarToRental = customListenerCarToRental;
    }

    public JTextField getTxtFl_cost_per_day() {
        return txtFl_cost_per_day;
    }

    public void setTxtFl_cost_per_day(JTextField txtFl_cost_per_day) {
        this.txtFl_cost_per_day = txtFl_cost_per_day;
    }

    public JTextField getTxtFl_cubic_capacity() {
        return txtFl_cubic_capacity;
    }

    public void setTxtFl_cubic_capacity(JTextField txtFl_cubic_capacity) {
        this.txtFl_cubic_capacity = txtFl_cubic_capacity;
    }

    public JTextField getTxtFl_number_of_seats() {
        return txtFl_number_of_seats;
    }

    public void setTxtFl_number_of_seats(JTextField txtFl_number_of_seats) {
        this.txtFl_number_of_seats = txtFl_number_of_seats;
    }

    public JButton getCar_btn() {
        return car_btn;
    }

    public void setCar_btn(JButton car_btn) {
        this.car_btn = car_btn;
    }


    public JLabel getModel() {
        return model;
    }

    public void setModel(JLabel model) {
        this.model = model;
    }

    public JLabel getCost_per_day() {
        return cost_per_day;
    }

    public void setCost_per_day(JLabel cost_per_day) {
        this.cost_per_day = cost_per_day;
    }

    public JLabel getCubic_capacity() {
        return cubic_capacity;
    }

    public void setCubic_capacity(JLabel cubic_capacity) {
        this.cubic_capacity = cubic_capacity;
    }

    public JLabel getSeats() {
        return seats;
    }

    public void setSeats(JLabel seats) {
        this.seats = seats;
    }

    public JPanel getMain_panel() {
        return this.car_panel;
    }

    public void setMain_panel(JPanel main_panel) {
        this.car_panel = main_panel;
    }

    public JPanel getCar_panel() {
        return car_panel;
    }

    public void setCar_panel(JPanel car_panel) {
        this.car_panel = car_panel;
    }
}


