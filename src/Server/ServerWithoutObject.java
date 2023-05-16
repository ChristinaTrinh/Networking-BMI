package Server;
 /*(BMI server) Write a server for a client. The client sends the weight and 
height for a person to the server (see Figure  31.18a). The server computes 
BMI (Body Mass Index) and sends back to the client a string that reports the 
BMI (see Figure 31.18b). See Section 3.8 for computing BMI. Name the client 
Exercise31_02Client and the server Exercise31_02Server.*/
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class ServerWithoutObject extends Application{
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
                Platform.runLater(()-> ta.appendText("Exercise#31.2 Server started at "+new Date()+"\n"));
                Socket socket = ss.accept();
                Platform.runLater(()-> ta.appendText("Connected to a client at "+new Date()));
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                double height = (double)input.readObject();
                double weight = (double)input.readObject();
                double bmi = Math.round(weight*kpp/Math.pow(height*mpi, 2)*100)/100.0;
                String report = "";
                if (bmi<18.5){
                    report = "Underweight";
                } else if (bmi<25){
                    report = "Normal";
                } else if (bmi<30){
                    report = "Overweight" ;
                } else {
                    report = "Obese";
                }
                output.writeObject(report);
                output.flush();
            }
        } catch (Exception e){}
    }
    public static void main(String[] args){
        launch(args);
        System.exit(0);
    }
}
