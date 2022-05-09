import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

    //默认端口号
    private String ip = null;
    private int port = 8888;


    public SocketClient(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    public boolean startClient() {

        try {

            Socket socket = new Socket(ip,port);
            InputStream isockets = socket.getInputStream();
            OutputStream osockets = socket.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(osockets));
            //向服务器端发送一条消息
            bw.write("test\n");
            bw.flush();

            //读取服务器返回的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(isockets));
            String mess = br.readLine();
            System.out.println("Sever Message："+mess);

            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        SocketClient sc = new SocketClient("127.0.0.1",1234);
        sc.startClient();

    }

}