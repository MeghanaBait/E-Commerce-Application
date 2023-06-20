package com.example.ecommerce;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button signinButton;
    Label welcomeLabel;
    VBox body;
    Customer loggedinCustomer;
    ProductList productList = new ProductList();
    VBox productPage;
    public BorderPane createUI(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
        //root.getChildren().add(loginPage); // method to add nodes as children to pan
        root.setTop(headerBar);
        //root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);

        root.setBottom(footerBar);

        return root;
    }

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createLoginPage(){
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("meghana@gmail.com");
        userName.setPromptText("Type your username here");
        PasswordField password= new PasswordField();
        password.setText("abc123");
        password.setPromptText("Type your password here");

        Label messageLabel = new Label(" ");

        Button loginButton = new Button("Login");

        loginPage = new GridPane();
        //loginPage.setStyle("-fx-background-color : grey;");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0 );
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(loginButton, 1,2);
        loginPage.add(messageLabel, 1, 3);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedinCustomer = login.customerLogin(name, pass);
                if(loggedinCustomer != null){
                    messageLabel.setText("Welcome! " + loggedinCustomer.getName());
                    welcomeLabel.setText("Welcome! " + loggedinCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }else{
                    messageLabel.setText("Login Failed!!");
                }
            }
        });
    }


    private void createHeaderBar(){
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(280);

        Button searchButton = new Button("Search");

        signinButton = new Button("Sign In");
        welcomeLabel = new Label();

        headerBar = new HBox();
        headerBar.setPadding(new Insets(10));
        //headerBar.setStyle("-fx-background-color : BLACK;");
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(searchBar, searchButton, signinButton);

        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signinButton);

            }
        });
    }

    private void createFooterBar(){
        Button buyNowButton = new Button("Buy Now");

        footerBar = new HBox();
        footerBar.setPadding(new Insets(10));
        //headerBar.setStyle("-fx-background-color : BLACK;");
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    //please select a product first to place order
                    showDialog("Please select a product first to place order");
                    return;
                }

                if(loggedinCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }

                boolean status = Order.placeOrder(loggedinCustomer,product);
                if(status == true){
                    showDialog("Order Placed Successfully!!");
                }
                else{
                    showDialog("order failed!");
                }

            }
        });
    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Alert");
        alert.showAndWait();

    }
}

