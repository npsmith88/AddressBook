/*
 * AddressBook - represents the address book
 */
package addressbook;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Nic Smith
 */
public class AddressBook extends Application {
    
    AddressFile aFile;         // random access address file
    
    // Text fields
    private TextField tfName = new TextField();
    private TextField tfStreet = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfZip = new TextField();
    
    // Buttons
    private Button btAdd = new Button("Add");
    private Button btFirst = new Button("First");
    private Button btNext = new Button("Next");
    private Button btPrevious = new Button("Previous");
    private Button btLast = new Button("Last");
    private Button btUpdate = new Button("Update");
    
    public AddressBook()
    {
        // open or create address file when application instantiates
        try {
            aFile = new AddressFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        GridPane p1 = new GridPane();
        p1.setAlignment(Pos.CENTER);
        p1.setHgap(5);
        p1.setVgap(5);
        p1.add(new Label("Name"), 0, 0);
        p1.add(new Label("Street"), 0, 1);
        p1.add(new Label("City"), 0, 2);
        p1.add(new Label("State"), 0, 3);
        p1.add(new Label("Zip"), 0, 4);
        p1.add(tfName, 1, 0);
        p1.add(tfStreet, 1, 1);
        p1.add(tfCity, 1, 2);
        p1.add(tfState, 1, 3);
        p1.add(tfZip, 1, 4);
        // Add buttons to a pane
        HBox p3 = new HBox(5);
        p3.getChildren().addAll(btAdd, btFirst, btNext, btPrevious,
                btLast, btUpdate);
        p3.setAlignment(Pos.CENTER);

        // Add p1 and p3 to a border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(p1);
        borderPane.setBottom(p3);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 350, 220);
        primaryStage.setTitle("Address Book"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        
        // Display the first record if exists
        Address a = aFile.firstAddress();
        if(a != null) setDisplay(a);
        
/********* Action Events *************/
        
        btAdd.setOnAction(e -> {
            aFile.addAddress(getDisplay());
        });
        btFirst.setOnAction(e -> {
            Address item = aFile.firstAddress();
            if(item != null) setDisplay(item);
        });
        btNext.setOnAction(e -> {
            Address item = aFile.nextAddress();
            if (item != null) setDisplay(item);
        });
        btPrevious.setOnAction(e -> {
            Address item = aFile.previousAddress();
            if (item != null) setDisplay(item);
        });
        btLast.setOnAction(e -> {
            Address item = aFile.lastAddress();
            if (item != null) setDisplay(item);
        });
        btUpdate.setOnAction(e -> {
            Address item = getDisplay();
            aFile.updateAddress(item);
        });        
    }

    // utility method to put data into display
    private void setDisplay(Address a) {
        tfName.setText(a.getName());
        tfStreet.setText(a.getStreet());
        tfCity.setText(a.getCity());
        tfState.setText(a.getState());
        tfZip.setText(a.getZip());
    }
    
    // utility method to get data from display
    private Address getDisplay() {
        Address a = new Address();
        a.setName(tfName.getText());
        a.setStreet(tfStreet.getText());
        a.setCity(tfCity.getText());
        a.setState(tfState.getText());
        a.setZip(tfZip.getText());
        return a;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
