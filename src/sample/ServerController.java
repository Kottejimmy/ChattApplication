package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.*;

public class ServerController {
    @FXML
    private Button send;
    @FXML
    private TextField sendField;
    @FXML
    private TextArea chattBox;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ServerSocket server;
    private Socket connection;


    public void startRunning (){
        try{
            server = new ServerSocket(6789,100);
            while(true){
                try{
                    waitForConnection();
                    setupStreams();
                    whilechatting();

                }catch (EOFException eofException){
                    showMessage("\n Server ended the connection! ");
                }
                finally {
                    closeCrap();
                }
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

    }
    private void waitForConnection()throws IOException {
        showMessage("Waiting for some1 to connect \n");
        connection = server.accept();
        showMessage("Now Connected to "+connection.getInetAddress().getHostName());
    }
    private void setupStreams()throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage ("\n Streams are now setup \n");

    }
    private void whilechatting ()throws IOException {
      String message = "Your are now connected";
      sendMessage(message);
      ableToType = (true);
      do{
          try{
              message = (String) input.readObject();
              showMessage("\n "+message);
          }catch (ClassNotFoundException classNotFoundException ){
              showMessage("\n IDK WTF was sent to me");
          }
      }while (!message.equals("CLIENT - END"));

    }

}
