package org.jailbreakers.obj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {

    private static DatabaseController instance;

    private Connection connection;
    private Thread connectingThread;

    private DatabaseController() {
    }

    public static DatabaseController getInstance() {
        if (instance == null)
            instance = new DatabaseController();
        return instance;
    }

    public void connect(ConnectionEvent event){
        connectingThread = new Thread(()->{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://db80.websupport.sk:3314/ufih4ysx?serverTimezone=CET","ufih4ysx","Gi6|-?#26q");
                if (event != null)
                    event.onConnect();
            }catch(Exception e){
                if (event != null)
                    event.onConnectionError(e.getMessage());
            }
        });
        connectingThread.setDaemon(true);
        connectingThread.start();
    }
    public void login() {


    }
    public void register(String email, String pass, String confirmPass){
        if(pass.equals(confirmPass)){
            try {
                Statement stm = connection.createStatement();
//                String sql = "insert into users "
//                        + "(email, pass) "
//                        + "values ("
//                        + email + ", " + pass
//                        + ")";
//                stm.executeUpdate(sql);
            } catch (SQLException e ) {
                e.printStackTrace();
            }
        }
    }
    public void abortConnection(){
        if (connectingThread.isAlive())
            connectingThread.stop();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {}
        }
    }
}
