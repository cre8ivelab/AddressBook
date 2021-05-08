package ca.senecacollege.AddressBook;


import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddressBook extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane pane = new GridPane();

		HBox hboxcity = new HBox();	//an HBox for city, province and postal code
		hboxcity.setSpacing(10);	//10px space between nodes
		HBox hboxbutton = new HBox();	//an HBox for all buttons
		
		pane.setPadding(new Insets(20, 20, 20, 20));	//20px padding from all sides of pane
		pane.setHgap(1);	//1px horizontal gap between nodes
		pane.setVgap(15);	//15px vertical gap between nodes
		pane.setStyle("-fx-background-color: #FAF0EF");	//background color of pane
		
		//add the labels to the pane
		//first name
		TextField firstName = new TextField();
		firstName.setMinWidth(370);
		firstName.setPrefWidth(370);
		firstName.setMaxWidth(350);
		pane.add(new Label("First Name: "), 0, 0);
		pane.add(firstName, 1, 0);
		
		//last name
		TextField lastName = new TextField();
		lastName.setMinWidth(370);
		lastName.setPrefWidth(370);
		lastName.setMaxWidth(350);
		pane.add(new Label("Last Name: "), 0, 1);
		pane.add(lastName, 1, 1);
		
		//city
		TextField cityText = new TextField();
		cityText.setMinWidth(70);
		cityText.setPrefWidth(70);
		cityText.setMaxWidth(70);
		
		//postal code textField
		TextField postalText = new TextField();
		postalText.setMinWidth(70);
		postalText.setPrefWidth(70);
		postalText.setMaxWidth(70);
		
		//HBoxCity labels
		Label city = new Label("City: ");
		Label province = new Label("Province: ");
		Label postal = new Label("Postal Code: ");
		ObservableList<String> options = 
				FXCollections.observableArrayList("Ontario", "Quebec", "Alberta");
		ComboBox<String> combobox = new ComboBox(options);
		
		//HBoxbuttons buttons
		Button btn[] = new Button[6];
		String str[] = {"Add", "First", "Next", "Previous", "Last", "Update"};
		for(int i = 0; i < 6; i++) {
			btn[i] = new Button(str[i]);
			btn[i].setMinWidth(75);
			btn[i].setPrefWidth(75);
			btn[i].setMaxWidth(75);
		}
		
		
		//set the event on the buttons
		long position[] = {0};
		
		//add the record to the address book
		btn[0].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				//store the userInput
				String rowdata = firstName.getText() + " " + lastName.getText() + " " + cityText.getText() + " " + (String) combobox.getValue() + " " + postalText.getText() + "\n";
				String data = String.format("%50s", rowdata);
				try {
					RandomAccessFile raf = new RandomAccessFile("addressBook.txt", "rw");
					raf.seek(raf.length());
					raf.write(data.getBytes());
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				System.out.println("Added to the AddressBook...");
			}
			
		});
		
		//first record from the address book
		btn[1].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				//store the record
				position[0] = 0;
				String data = "";
				//get the data on first position
				try {
					RandomAccessFile raf = new RandomAccessFile("addressBook.txt", "r");
					raf.seek(position[0]);
					data = raf.readLine();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				
				//display the data
				String str = data.trim();
				String [] strArr = str.split(" ", 6);
				firstName.setText(strArr[0]);
				lastName.setText(strArr[1]);
				cityText.setText(strArr[2]);
				combobox.setValue(strArr[3]);
				postalText.setText(strArr[4]);
				System.out.println(str);
			}
			
		});
		
		//get the next record
		btn[2].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				String data = "";
				position[0] += 50;
				
				//get the next data
				try {
					RandomAccessFile raf = new RandomAccessFile("addressBook.txt", "r");
					raf.seek(position[0]);
					data = raf.readLine();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				if(data != null) {
					//display the data
					String str = data.trim();
					String [] strArr = str.split(" ", 6);
					firstName.setText(strArr[0]);
					lastName.setText(strArr[1]);
					cityText.setText(strArr[2]);
					combobox.setValue(strArr[3]);
					postalText.setText(strArr[4]);
					System.out.println(str);
				}
				else {
					System.out.print("End of the file!!!");
					position[0] -= 50;
				}
			}
		});
		
		//get the previous record
		btn[3].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				String data = "";
				if(position[0] != 0) {
					position[0] -= 50;
					
					//get the next data
					try {
						RandomAccessFile raf = new RandomAccessFile("addressBook.txt", "r");
						raf.seek(position[0]);
						data = raf.readLine();
						System.out.println(data);
					}
					catch(IOException e) {
						e.printStackTrace();
					}
					//display the data
					String str = data.trim();
					String [] strArr = str.split(" ", 6);
					firstName.setText(strArr[0]);
					lastName.setText(strArr[1]);
					cityText.setText(strArr[2]);
					combobox.setValue(strArr[3]);
					postalText.setText(strArr[4]);
				}
				else
					System.out.println("Oops!!! You're on the first record");
			}
		});
		
		//get the last record
		btn[4].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				//store the record
				String data = "";
				
				//find last record
				try {
					RandomAccessFile raf = new RandomAccessFile("addressBook.txt", "r");
					position[0] = raf.length() - 50;
					raf.seek(position[0]);
					data = raf.readLine();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				//display the data
				String str = data.trim();
				String [] strArr = str.split(" ", 6);
				firstName.setText(strArr[0]);
				lastName.setText(strArr[1]);
				cityText.setText(strArr[2]);
				combobox.setValue(strArr[3]);
				postalText.setText(strArr[4]);
				System.out.println(str);
			}
			
		});
		
		//update the record
		btn[5].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				//get the current data
				String rowdata = firstName.getText() + " " + lastName.getText() + " " + cityText.getText() + " " + (String) combobox.getValue() + " " + postalText.getText() + "\n";
				String data = String.format("%50s", rowdata);
				
				
				//get the next data
				try {
					RandomAccessFile raf = new RandomAccessFile("addressBook.txt", "rw");
					raf.seek(position[0]);
					raf.write(data.getBytes());
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		hboxcity.getChildren().addAll(city, cityText, province, combobox, postal, postalText);	//add nodes to the hboxCity
		pane.add(hboxcity, 0, 3, 3, 3);		//add hboxcity to the pane		
		hboxbutton.getChildren().addAll(btn[0], btn[1], btn[2], btn[3], btn[4], btn[5]);	//add buttons to the hboxbuttons
		pane.add(hboxbutton, 0, 6, 4, 4);	//add hboxbutton to the pane
		
		//primaryStage.setFill(Color.BLUE);
		Scene scene = new Scene(pane, 505, 195);
		primaryStage.setTitle("Address Book");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//main function
	public static void main(String[] args) {
		launch(args);
	}
	
}
