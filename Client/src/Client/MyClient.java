package Client;

import java.io.*;
import java.net.Socket;

public class MyClient {
    Socket client;
    public Socket initClient(){
        try{
            client=new Socket("localhost",8080);
        }catch (IOException e){
            e.printStackTrace();
        }
        return client;
    }
}
