package Server;

/*(BMI server) Write a server for a client. The client sends the weight and 
height for a person to the server (see Figure  31.18a). The server computes 
BMI (Body Mass Index) and sends back to the client a string that reports the 
BMI (see Figure 31.18b). See Section 3.8 for computing BMI. Name the client 
Exercise31_02Client and the server Exercise31_02Server.*/
import java.io.*;
import java.net.*;
import java.util.*;

import Client.SerializedBMI;
//import Server.SerializedBMI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class ServerWithObject extends Application{
    private TextArea ta = new TextArea();
    public static final double kpp = 0.45359237;
    public static final double mpi = 0.0254;
    public void start(Stage primaryStage){
        ta.setWrapText(true);
        Scene scene = new Scene(new ScrollPane(ta), 200, 200);
        primaryStage.setTitle("Exercise#31.2 Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        new Thread(()->
        {
            try{
                 connectToClient();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }).start();
    }
    public void connectToClient() throws IOException {
        try{
            while (true){
                ServerSocket ss = new ServerSocket(8000);
                Platform.runLater(()-> ta.appendText("Exercise#31.2 Server started at "+new Date()));
                Socket socket = ss.accept();
                Platform.runLater(()-> ta.appendText("Connected to a client at "+new Date()));
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                SerializedBMI bmi = (SerializedBMI)input.readObject();
                bmi.setReport(bmi.getStatus());
                output.writeObject(bmi);
                output.flush();
            }
        } catch (Exception e){}
    }
    public static void main(String[] args){
        launch(args);
        System.exit(0);
    }
}
