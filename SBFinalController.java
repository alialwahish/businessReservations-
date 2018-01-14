package application;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SBFinalController  {
	
	//Global Variables to be accessed from all methods
	LinkedList<String> timeArr= new LinkedList<String>();
	LinkedList<Appointments> apArr= new LinkedList<Appointments>();
	
	//The Table for the Shop Queue
	TableView<Appointments> sched= new TableView<Appointments>();
	
	// The Object that holds the Appointments and show them in the Queue
	ObservableList<Appointments> data =FXCollections.observableArrayList();
	
	
	// FXML Scene Builder Generated Variables 
	@FXML
	private ImageView brbrImg;
	@FXML
	private ComboBox<String> brbrCombxSelct;
	@FXML
	private TextField custName;
	@FXML
	private TextField custPhonNum;
	@FXML
	private ComboBox<String> timeCombSelct;
	@FXML
	private Label brbrLblName;
	@FXML
	private Button bookTime;
	@FXML
	private Label status;
	@FXML
	private Button nextAva;
	
	// Using Thread to Initial the ComboBox for the TimeComboBox
	Thread init = new Thread(new Runnable(){

		@Override
		public void run() {
			brbrCombxSelct.getItems().add("Fabio");
			brbrCombxSelct.getItems().add("Franco");
			brbrCombxSelct.getItems().add("Vince");
							
			int h=6;
			int m=0;
			String dt=" Am";
			
			 for(int i=0;i<48;i++)
			 {
				 if(m==0)
				 { 	
					 if(h<10)
						 timeArr.add("0"+h+":"+m+"0"+dt);
					 else
						 timeArr.add(""+h+":"+m+"0"+dt);
				 }
				 else if(h<10)
					 timeArr.add("0"+h+":"+m+""+dt);
				 else
					 timeArr.add(""+h+":"+m+""+dt);
				 m+=15;
				 if(m>=60)
				 {
					 h++;
					 m=0;
				 }
				 if(i>22)
				{
					dt=" Pm";
				}
				 if(h>=13)
				 {
					 h=1;
				 }
			 }
			 
			 for(String s:timeArr)
			 {
				 timeCombSelct.getItems().add(s);
			 }
			
			
		}
		
	});
	
	
	// Initialize Method that sets up all the start up values in the GUI
	public void initialize()
	{
		 // Calling The Thread to start Running 
		 init.start();
		 // Creating another Window to present the Screen Queue for the Shop
		 Pane pane = new Pane();
		 Stage stage = new Stage();
		 stage.setTitle("Today Scheduale");
		 stage.setScene(new Scene(pane,400,600));	  															
		 sched.setItems(data);
		 Label lblSchd = new Label("Today's Scheduale");
		 lblSchd.setFont(new Font(20));
		 lblSchd.setLayoutX(75);
		 Button doneCut= new Button("Finished Cut");
		 doneCut.setLayoutX(315);
		 doneCut.setLayoutY(4);
		 
		 //Lambda Expretion used on this 
		 //Button that takes off the finished appointment
		 doneCut.setOnAction(e->{data.remove(0);sched.refresh();});
		 
		 pane.getChildren().addAll(sched,lblSchd,doneCut);
		 sched.setPrefSize(pane.getWidth(), pane.getHeight());
		 sched.setLayoutY(30);
		 
		 // the Columns in the table for the Shop Queue
		 TableColumn<Appointments,String> nameCol =
				 new TableColumn<Appointments,String>("Customer Name");
		 nameCol.setPrefWidth(110);
		 nameCol.setCellValueFactory(
	    		    new PropertyValueFactory<Appointments,String>("clName")
	    		);
	     TableColumn<Appointments,String> brbrNameCol =
	    		 new TableColumn<Appointments,String>("Barber Name");
	     brbrNameCol.setPrefWidth(100);
	     brbrNameCol.setCellValueFactory(
	    		    new PropertyValueFactory<Appointments,String>("brbrName")
	    		);
	     TableColumn<Appointments,String> phoneNum = 
	    		 new TableColumn<Appointments,String>("Phone Number");
	     phoneNum.setPrefWidth(100);
	     phoneNum.setCellValueFactory(
	    		    new PropertyValueFactory<Appointments,String>("phone")
	    		);
	     TableColumn<Appointments,String> appTime =
	    		 new TableColumn<Appointments,String>("Time");
	     appTime.setPrefWidth(90);
	     appTime.setCellValueFactory(
	    		    new PropertyValueFactory<Appointments,String>("time")
	    		);
	     	     
	     sched.getColumns().addAll(brbrNameCol,nameCol,appTime,phoneNum);
	     stage.setY(78);
		 stage.setX(130);
		 stage.show();
		 
	}
	
	
	@FXML
	public void brbrCombClikd(ActionEvent event) 
	{	
		String pathForImg="";
	
		if(brbrCombxSelct.getValue().equals("Fabio"))
		{
			brbrLblName.setText("Fabio");
			pathForImg="br1.jpeg";
		}
		else if(brbrCombxSelct.getValue().equals("Franco"))
		{
			brbrLblName.setText("Franco");
			pathForImg="br2.jpeg";
		}
		else if(brbrCombxSelct.getValue().equals("Vince"))
		{
			brbrLblName.setText("Vince");
			pathForImg="br3.jpeg";
		}
		 File file = new File(pathForImg);
	        Image image = new Image(file.toURI().toString());
	         brbrImg.setImage(image);
	         
	}
	// Event Listener on ComboBox[#timeCombSelct].onAction 
	
	
	@FXML
	public void timeCombClikd(ActionEvent event) 
	{
		
		
		if(validateAppointment(brbrLblName.getText(),timeCombSelct.getValue()))
		{
			
		 status.setTextFill(Color.BLACK);	
		 status.setText("Available");	
		}
		else
		{
		 status.setTextFill(Color.RED);	
	     status.setText("Unavailable");		
		}
		if(!validatePhone(custPhonNum.getText()))
		{
			
			errMsg("Phone Entry","Please Check Phone Number !\n "
					+ "Phone format ###-###-####");
			
			status.setText("Phone # Format!");
			status.setTextFill(Color.RED);
			custPhonNum.setText("");
									
		}
		
		
		
	}
	
		
	// Event Listener on Button[#bookTime].onAction
	@FXML
	public void clkdBookTime(ActionEvent event) 
	{
		if(brbrLblName.getText().equals(""))
			{
			errMsg("Barber Name Entry", "Please Choose Barber Name !");
			brbrLblName.setText("Barber Name");
			brbrLblName.setTextFill(Color.RED);
			}
		else
		{
		 Appointments apt = new Appointments(custName.getText(),
					timeCombSelct.getValue(),
					brbrLblName.getText(),
					custPhonNum.getText());
		 			
		 
		 if(validateAppointment(brbrLblName.getText(),
				 				timeCombSelct.getValue())&&
				 				(!custPhonNum.getText().equals("")))
		 	{
		
			 	Alert alert = new Alert(AlertType.CONFIRMATION);
			 	alert.setTitle("Booking Confirmation");
			 	alert.setContentText("Please confirm booking?");
			 	Optional<ButtonType> result = alert.showAndWait();
			 	if (result.get() == ButtonType.OK)
				{
					apArr.add(apt);
					data.add(apt);
					data.sort(null);
					clearFeilds();
				} 
		 	}
		 else
		 	{ 		 
			 errMsg("Schedualling Message", "Barber is not Available"
			 	  + " at this Time  !\n Please choose another time ");
		 	}
		 
		}
	 
	}	
	public void errMsg(String title,String content)
	{
		Alert alt = new Alert(AlertType.INFORMATION);
		 alt.setTitle(title);
		 alt.setContentText(content);
		  alt.showAndWait();
	}
	public boolean validateAppointment(String brbrName, String time) 
	{
		if(time.substring(0, 2).matches("^[0-9]*$"))
			{	
			int hh=Integer.parseInt(time.substring(0, 2));
			String dt=time.substring(6, 8);
			
			
			if(hh>6&&dt.equals("Pm")||hh<6&&dt.equals("Am"))
				{				
				return false;
				}
					
			}
		
		
		for(int i=0;i<apArr.size();i++)
		{
			if(apArr.get(i).getBrbrName().equals(brbrName)&&
				apArr.get(i).getTime().equals(time))
			{
			return false;
			}
		}
		
		return true;
	}

	@FXML
	public void NextAvaAction(ActionEvent event)
	{
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 String time =sdf.format(cal.getTime());
		 int mm=Integer.parseInt(time.substring(3, 5));
		 String stmm="";
		 int hh=Integer.parseInt(time.substring(0, 2));
		 //flipping the time Pm to Am***************************************
		// hh-=12;
		 
		 
		 
		 if(mm<15)
			 stmm="15";
		 else if(mm<30)
			 stmm="30";
		 else if(mm<45)
			 stmm="45";
		 else if(mm>45)
			 {
			 stmm="00";
			 hh++;
			 }
			 
		time=hh+":"+stmm;
		
		 if(hh<12)
		 	{
			 time=time+" Am";
			}
		 else if(hh>21)
			{
			 time=(hh-12)+time.substring(2, 5)+" Pm";
		    }
		 else 
			{
			 time="0"+(hh-12)+time.substring(2, 5)+" Pm";
			 }
				
				 
		for(int i=0 ;i<timeArr.size();i++)
			 {
				if(validateAppointment(brbrLblName.getText(),time))
					{
					timeCombSelct.setValue(time);
					return;
					}
				else
					{
					time=timeArr.get(timeArr.indexOf(time)+1); 
					}
			 
			 }
		 	
	 	
	}
	
	public void clearFeilds()
	{
		 custName.setText("");
		 timeCombSelct.setValue("Select Time");
		 brbrLblName.setText("");
		 custPhonNum.setText("");
		 brbrLblName.setText("");
		 brbrCombxSelct.setValue("Choose Barber");
		 status.setText("");
	}
	
	
	public boolean validatePhone(String phone)
	{
		
		if(phone.matches("^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$"))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
		
	
}
