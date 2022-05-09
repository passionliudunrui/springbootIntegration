import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    //默认端口号
    private int port = 8888;

    public SocketServer(int port) {
        super();
        this.port = port;
    }

    @SuppressWarnings({ "static-access", "resource" })
    public boolean startServer() {

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started...");
            Socket socket = null;
            //等待客户端连接
            while((socket = server.accept())!= null) {
                System.out.println("Client IP: "+socket.getInetAddress().getLocalHost()+" is Connected");
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String message = null;
                //读取客户端消息
                while((message = br.readLine())!=null) {
                    System.out.println("Client Message : "+message);
                    //返回客户端信息
                    bw.write("Received \n");
                    bw.flush();
                }
                System.out.println("Client is Closed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void main(String[] args) {
        SocketServer ss = new SocketServer(1234);
        ss.startServer();
    }

}