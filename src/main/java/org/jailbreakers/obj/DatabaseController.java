package org.jailbreakers.obj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseController {

    private static DatabaseController instance;

    private Connection connection;
    private ConnectionEvent event;

    private DatabaseController() {
    }

    public static DatabaseController getInstance() {
        if (instance == null)
            instance = new DatabaseController();
        return instance;
    }

    public void connect(){
        Thread th = new Thread(()->{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://db80.websupport.sk:3314/ufih4ysx?serverTimezone=CET","ufih4ysx","Gi6|-?#26q");
            }catch(Exception e){
                if (event != null)
                    event.onConnectionError(e.getMessage());
            }
        });
        th.setDaemon(true);
        th.start();
    }
}
