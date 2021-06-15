package Client;

import java.net.Socket;

public class Test {
    public static void main(String[] args) {
        MyClient myClient=new MyClient();
        Socket client=myClient.initClient();
        //new Thread(new ClientThread(client)).start();
    }
}
