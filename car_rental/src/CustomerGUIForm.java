import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerGUIForm extends JFrame implements FormWithPanel{
    private JTextField txtFl_email;
    private JTextField txtFl_fname;
    private JTextField txtFl_lname;
//    private JTextField txtFl_sex;
    private JTextField txtFl_address;
    private JTextField txtFl_phone;
    private JButton createCustomerButton;
    private JLabel first_name;
    private JPanel customer_panel;
    private JLabel sex;
    private JLabel address;
    private JLabel email;
    private JLabel phone;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private ConnectionToDatabase database;
    private RefresherListener customListenerCustomerToRental;
    public CustomerGUIForm() {
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sex;
                if(maleRadioButton.isSelected()){
                    sex = "male";
                }else if(femaleRadioButton.isSelected()){
                    sex = "female";
                }else{
                    JOptionPane.showMessageDialog(null, "You must choose exactly one of the two sexes!");
                    return;
                }
                    String first_name = capitalizeFirstLetterAndLowercaseTheOtherLetters(txtFl_fname.getText());
                    String last_name = capitalizeFirstLetterAndLowercaseTheOtherLetters(txtFl_lname.getText());
                    String address = capitalizeFirstLetterAndLowercaseTheOtherLetters(txtFl_address.getText().toLowerCase());
                    String email = txtFl_email.getText().toLowerCase();
                    String phone = txtFl_phone.getText().toLowerCase();
                    ArrayList<String> myArrayList = new ArrayList<>();
                    myArrayList.add(first_name);
                    myArrayList.add(last_name);
                    myArrayList.add(address);
                    myArrayList.add(email);
                    myArrayList.add(phone);
                    myArrayList.add(sex);
                    if (areEmptyFields(myArrayList)) {
                        JOptionPane.showMessageDialog(null, "You must fill all fields!No empty spaces allowed!");
                        return;
                    }
                try {
                    database = new ConnectionToDatabase();
                    Connection connection = database.getConnection();
                    Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String sqlCheck = "SELECT * FROM customers WHERE first_name = '" + first_name +
                            "' AND last_name = '" + last_name + "' AND sex = '" + sex +
                            "' AND address = '" + address + "' AND email = '" + email +
                            "' AND phone = '" + phone + "'";
                    ResultSet resultSet = statement1.executeQuery(sqlCheck);
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Customer already exists!");
                        return;
                    }
                    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String sql = "INSERT INTO customers(first_name,last_name,sex,address,email,phone) VALUES ('" + first_name + "','" + last_name + "','" + sex + "','" + address + "','" + email + "','" + phone + "')";
                    int rowsInserted = statement.executeUpdate(sql);
                    if(rowsInserted>0){
                        System.out.println("Successful insertion!");
                        if(customListenerCustomerToRental != null){
                            customListenerCustomerToRental.refreshData();
                        }
                    }
                } catch (SQLException eχ) {
                    throw new RuntimeException(eχ);
                }
            }
        });
    }

    public RefresherListener getCustomListenerCustomerToRental() {
        return customListenerCustomerToRental;
    }

    public void setCustomListenerCustomerToRental(RefresherListener customListenerCustomerToRental) {
        this.customListenerCustomerToRental = customListenerCustomerToRental;
    }

    public JTextField getTxtFl_email() {
        return txtFl_email;
    }

    public void setTxtFl_email(JTextField txtFl_email) {
        this.txtFl_email = txtFl_email;
    }

    public JTextField getTxtFl_fname() {
        return txtFl_fname;
    }

    public void setTxtFl_fname(JTextField txtFl_fname) {
        this.txtFl_fname = txtFl_fname;
    }

    public JTextField getTxtFl_lname() {
        return txtFl_lname;
    }

    public void setTxtFl_lname(JTextField txtFl_lname) {
        this.txtFl_lname = txtFl_lname;
    }

    public JTextField getTxtFl_address() {
        return txtFl_address;
    }

    public void setTxtFl_address(JTextField txtFl_address) {
        this.txtFl_address = txtFl_address;
    }

    public JTextField getTxtFl_phone() {
        return txtFl_phone;
    }

    public void setTxtFl_phone(JTextField txtFl_phone) {
        this.txtFl_phone = txtFl_phone;
    }

    public JButton getCreateCustomerButton() {
        return createCustomerButton;
    }

    public void setCreateCustomerButton(JButton createCustomerButton) {
        this.createCustomerButton = createCustomerButton;
    }

    public JLabel getFirst_name() {
        return first_name;
    }

    public void setFirst_name(JLabel first_name) {
        this.first_name = first_name;
    }

    public JPanel getMain_panel() {
        return this.customer_panel;
    }

    public void setMain_panel(JPanel main_panel) {
        this.customer_panel = main_panel;
    }

    public JLabel getSex() {
        return sex;
    }

    public void setSex(JLabel sex) {
        this.sex = sex;
    }

    public JLabel getAddress() {
        return address;
    }

    public void setAddress(JLabel address) {
        this.address = address;
    }

    public JLabel getEmail() {
        return email;
    }

    public void setEmail(JLabel email) {
        this.email = email;
    }

    public JLabel getPhone() {
        return phone;
    }

    public void setPhone(JLabel phone) {
        this.phone = phone;
    }
    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    public boolean areEmptyFields(ArrayList<String> arrayChecked) {
        for (String checkedField : arrayChecked) {
            if (checkedField.trim() == null || checkedField.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public String capitalizeFirstLetterAndLowercaseTheOtherLetters(String input){
        if(input.trim().isEmpty() || input == null){
            return "";
        }
        return input.substring(0,1).toUpperCase()+input.substring(1).toLowerCase();
    }
}


