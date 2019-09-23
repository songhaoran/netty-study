package ioSocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Song on 2019/09/18.
 */
public class IoServer {

    public static void main(String[] args) throws Exception{
        // 创建一个server socket监听8000端口
        ServerSocket serverSocket = new ServerSocket(8000);

        while (true) {
            // 阻塞方法获取链接
            Socket socket = serverSocket.accept();

            // 获取到新的链接以后, 创建一个线程
            new Thread(() -> {
                // 从链接中不断获取信息
                try {
                    int len;
                    byte[] data = new byte[1024];
                    InputStream inputStream = socket.getInputStream();

                    while ((len = inputStream.read(data)) != -1) {
                        System.out.println(Thread.currentThread().getName() + ":" + new String(data, 0, len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
