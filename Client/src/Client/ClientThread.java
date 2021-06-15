package Client;

import BookManageSystem.beans.*;
import BookManageSystem.tools.SimpleTools;
import org.apache.log4j.Logger;


import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    public Socket socket;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public Object o;
    public int state;
    private static final Logger logger=Logger.getLogger(ClientThread.class);

    public SimpleTools simpleTools = new SimpleTools();

    public ClientThread(Socket socket, Object o, int state) {
        this.socket = socket;
        this.o = o;
        this.state = state;
    }

    public void run() {
        try {

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            if (state == 1) {
                out.writeInt(state);
                LendList lendList = (LendList) o;
                out.writeObject(lendList);
                out.flush();
                StateBean.isOk1 = in.readBoolean();


            } else if (state == 2) {
                out.writeInt(state);
                ReaderBean readerBean = (ReaderBean) o;
                out.writeObject(readerBean);
                out.flush();
                StateBean.isOk2 = in.readBoolean();
            } else if (state == 3) {
                out.writeInt(state);
                BookBean bookBean = (BookBean) o;
                out.writeObject(bookBean);
                out.flush();
                StateBean.isOk3 = in.readBoolean();

            } else if (state == 4) {
                out.writeInt(state);
                LendList lendList = (LendList) o;
                out.writeObject(lendList);
                out.flush();
                StateBean.isOk1 = in.readBoolean();

            } else if (state == 5) {
                out.writeInt(state);
                String sql = (String) o;
                out.writeObject(sql);
                out.flush();
                StateBean.isOk1 = in.readBoolean();
            } else if (state == 6) {
                out.writeInt(state);
                ReaderBean readerBean = (ReaderBean) o;
                out.writeObject(readerBean);
                out.flush();
                StateBean.isOk1 = in.readBoolean();
            } else if (state == 7) {
                out.writeInt(state);
                ReaderBean readerBean = (ReaderBean) o;
                out.writeObject(readerBean);
                out.flush();
                StateBean.isOk1 = in.readBoolean();
            } else if (state == 8) {
                out.writeInt(state);
                AdminBean adminBean = (AdminBean) o;
                out.writeObject(adminBean);
                out.flush();
                StateBean.isOk1 = in.readBoolean();
            }else if(state==9){
                out.writeInt(9);
                AdminBean adminBean=(AdminBean) o;
                out.writeObject(adminBean);
                out.flush();
                StateBean.isOk1=in.readBoolean();
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        } finally {
            logger.info("等待读者操作。。。。。。");
        }
    }
}
