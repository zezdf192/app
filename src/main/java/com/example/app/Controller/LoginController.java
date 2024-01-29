package com.example.app.Controller;

import com.example.app.ConnectDB.ConnectDB;
import com.example.app.Models.Model;
import com.example.app.Models.User.User;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public Button change;
    public Button login_btn;
    public PasswordField password_field;
    public TextField email_field;
   // public FontAwesomeIconView display_pass;
    public Label display_text;


//    private static User user; // Đặt làm biến toàn cục

//    public static User getUser() {
//        return user;
//    }

    private boolean isPasswordVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(actionEvent -> {
            try {
                onLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        change.setOnAction(actionEvent -> change_signup());
        //display_pass.setOnMouseClicked(mouseEvent -> display_pass());

    }


    //    private void onLogin() throws SQLException {
//
//        String email = email_field.getText();
//        String password = password_field.getText();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            showAlert("Lỗi", "Vui lòng nhập email và mật khẩu!", Alert.AlertType.ERROR);
//            return;
//        }
//        Connection connection = ConnectDB.getConnection();
//
//        try (connection) {
//            String sql = "SELECT * FROM user where email = ? and password = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setString(1, email);
//                preparedStatement.setString(2, password);
//                if (validateLogin(email, password, connection)) {
//                    // Successful login
//                    showAlert("Thông báo", "Đăng nhập thành công!", Alert.AlertType.INFORMATION);
//                    Stage stage = (Stage) login_btn.getScene().getWindow();
//                    Model.getInstance().getViewFactory().closeStage(stage);
////                    Model.getInstance().getViewFactory().showClientWindow();
////                Model.getInstance().getViewAdminFactory().showAdminWindow();
//                } else {
//                    showAlert("Lỗi", "Email hoặc mật khẩu không đúng!", Alert.AlertType.ERROR);
//                }
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    while (resultSet.next()) {
//                        String userName = resultSet.getString("nameUser");
//                        Integer userId = resultSet.getInt("userId");
//                        String userEmail = resultSet.getString("email");
//                        String userGender = resultSet.getString("gender");
//                        String passwordUser = resultSet.getString("password");
//
//                        String roleCode = resultSet.getString("role");
//
////                        if (!userEmail.equals(email)) {
////                            showAlert("Lỗi", "Email không tồn tại!", Alert.AlertType.ERROR);
////                        }
////                        if (!passwordUser.equals(password)) {
////                            showAlert("Lỗi", "Mật khẩu không đúng!", Alert.AlertType.ERROR);
////
////                        }
//                        if (roleCode.equals("R1")) {
//                            Model.getInstance().getViewAdminFactory().showAdminWindow();
//                        } else {
//                            Model.getInstance().getViewFactory().showClientWindow();
//                        }
//                        User user = new User(userName, userId, userEmail,userGender);
//                        Data.getDataGLobal.dataGlobal.setCurrentUser(user);
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace(); // Handle the exception appropriately
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void onLogin() {
        Model.getInstance().getViewAdminFactory().showAdminWindow();
        Stage stage = (Stage) login_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
//        try (Connection connection = ConnectDB.getConnection()) {
//            String email = email_field.getText();
//            String password = password_field.getText();
//
//            if (email.isEmpty() || password.isEmpty()) {
//                showAlert("Lỗi", "Vui lòng nhập email và mật khẩu!", Alert.AlertType.ERROR);
//                return;
//            }
//
//            User user = getUserByEmail(email, connection);
//
//            if (user != null) {
//                // Verify the entered password against the stored hashed password
//                if (verifyPassword(password, user.getPassword())) {
//                    // Password is correct
//                    showAlert("Thông báo", "Đăng nhập thành công!", Alert.AlertType.INFORMATION);
//                    Stage stage = (Stage) login_btn.getScene().getWindow();
//                    Model.getInstance().getViewFactory().closeStage(stage);
//
//                    // Determine user role and show the appropriate window
////                    if (user.getRole().equals("R1")) {
////                        Model.getInstance().getViewAdminFactory().showAdminWindow();
////                    } else {
//                        Model.getInstance().getViewFactory().showClientWindow();
//                    //}
//
//                    Data.getDataGLobal.dataGlobal.setCurrentUser(user);
//                } else {
//                    showAlert("Lỗi", "Email hoặc mật khẩu không đúng!", Alert.AlertType.ERROR);
//                }
//            } else {
//                showAlert("Lỗi", "Không tìm thấy người dùng với địa chỉ email này", Alert.AlertType.ERROR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert("Lỗi", "Lỗi không xác định!", Alert.AlertType.ERROR);
//        }
    }

    private  void change_signup () {
        Stage stage = (Stage) change.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showSignupWindow();
    }

    private boolean validateLogin(String email, String password, Connection connection) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If there is at least one row, login is successful
        }
    }
    private User getUserByEmail(String email, Connection connection) {
        try {
            String sql = "SELECT * FROM user WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        //Integer , String , String , String , String phoneNumber, String address, String role
                        User user = new User(null, null, null ,null, null, null, null);
                        user.setUserId(resultSet.getInt("userId"));
                        user.setEmail(resultSet.getString("email"));
                        user.setUserName(resultSet.getString("nameUser"));
                        user.setPhoneNumber(resultSet.getString("phoneNumber"));
                        user.setAddress(resultSet.getString("address"));
                        user.setPassword(resultSet.getString("password")); // Get the hashed password from the database
                        user.setRole(resultSet.getString("role"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }





    private boolean verifyPassword(String enteredPassword, String hashedPassword) {
        // Use BCrypt to verify the entered password against the stored hashed password
//        BCrypt.Result result = BCrypt.verifyer().verify(enteredPassword.toCharArray(), hashedPassword);
//        return result.verified;
        return true;
    }


//    public void display_pass() {
//        if (isPasswordVisible) {
//            display_pass.setGlyphName("EYE_SLASH");
//            display_text.setText("");
//        } else {
//            display_text.setText("Mật khẩu của bạn là: " + password_field.getText());
//            display_pass.setGlyphName("EYE");
//        }
//        isPasswordVisible = !isPasswordVisible;
//    }
}
