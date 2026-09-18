import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CarCategoryGUIForm extends JFrame implements FormWithPanel{
    private JTextField txtFl_size;
    private JTextField txtFl_price;
    private JTextField txtFl_type;
    private JButton category_btn;
    private JLabel size;
    private JLabel price;
    private JLabel type;
    private JPanel category_panel;
    private RefresherListener customListenerCategoryToCar;
    public CarCategoryGUIForm(){
        category_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String size = txtFl_size.getText().toLowerCase();
                String price = txtFl_price.getText().toLowerCase();
                String type = txtFl_type.getText().toLowerCase();
                if(size.trim().isEmpty() || price.trim().isEmpty() || type.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                    return;
                }
                try {
                    ConnectionToDatabase database = new ConnectionToDatabase();
                    Connection connection = database.getConnection();
                    Statement statement1 = connection.createStatement();
                    String sql = "SELECT * FROM car_categories WHERE car_size='"+size+"'AND car_price='"+price+"'AND car_type='"+type+"'";
                    ResultSet resultSet = statement1.executeQuery(sql);

                    HashMap<String,String> db_records = new HashMap<>();
                    String db_rows_size;
                    String db_rows_price;
                    String db_rows_type;

                    while(resultSet.next()){
                        db_rows_size = resultSet.getString("car_size");
                        db_rows_price = resultSet.getString("car_price");
                        db_rows_type = resultSet.getString("car_type");
                        db_records.put("db_rows_size",db_rows_size);
                        db_records.put("db_rows_price",db_rows_price);
                        db_records.put("db_rows_type",db_rows_type);
                    }
                    int matching_index = 0;
                    for (Map.Entry<String, String> entry : db_records.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if(key.equals("db_rows_size") && value.equals(size) ){
                            matching_index++;
                        }if (key.equals("db_rows_price") && value.equals(price) ) {
                            matching_index++;
                        }if(key.equals("db_rows_type") && value.equals(type) ){
                            matching_index++;
                        }
                    }
                    if(matching_index == 3){
                        System.out.println("There is already this category,please create a new one!");
                        return;
                    }
                    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    int rowsInserted = statement.executeUpdate("INSERT INTO car_categories(car_size,car_price,car_type) VALUES('" + size.trim() + "', '" + price.trim() + "', '" + type.trim() + "')");
                    if(rowsInserted>0) {
                        JOptionPane.showMessageDialog(null, "Successful insertion of car category data!");
                        System.out.println("Successful insertion of data!");
                        if(customListenerCategoryToCar != null){
                            customListenerCategoryToCar.refreshData();
                        }
                    }
                }catch(SQLException ex){
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public RefresherListener getCustomListenerCategoryToCar() {
        return customListenerCategoryToCar;
    }

    public void setCustomListenerCategoryToCar(RefresherListener customListenerCategoryToCar) {
        this.customListenerCategoryToCar = customListenerCategoryToCar;
    }

    public JTextField getTxtFl_size() {
        return txtFl_size;
    }

    public JPanel getCategory_panel() {
        return category_panel;
    }

    public void setCategory_panel(JPanel category_panel) {
        this.category_panel = category_panel;
    }

    public void setTxtFl_size(JTextField txtFl_size) {
        this.txtFl_size = txtFl_size;
    }

    public JTextField getTxtFl_price() {
        return txtFl_price;
    }

    public void setTxtFl_price(JTextField txtFl_price) {
        this.txtFl_price = txtFl_price;
    }

    public JTextField getTxtFl_type() {
        return txtFl_type;
    }

    public void setTxtFl_type(JTextField txtFl_type) {
        this.txtFl_type = txtFl_type;
    }

    public JButton getCategory_btn() {
        return category_btn;
    }

    public void setCategory_btn(JButton category_btn) {
        this.category_btn = category_btn;
    }

    public JLabel getCarSize() {
        return size;
    }

    public void setSize(JLabel size) {
        this.size = size;
    }

    public JLabel getPrice() {
        return price;
    }

    public void setPrice(JLabel price) {
        this.price = price;
    }

    public JLabel getCarType() {
        return type;
    }

    public void setType(JLabel type) {
        this.type = type;
    }

    public JPanel getMain_panel() {
        return this.category_panel;
    }

    public void setMain_panel(JPanel main_panel) {
        this.category_panel = main_panel;
    }
}
