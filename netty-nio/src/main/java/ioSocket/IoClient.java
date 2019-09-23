package ioSocket;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;

/**
 * Created by Song on 2019/09/18.
 */
public class IoClient {

    public static void main(String[] args) {
        new Thread(()->{
            // 每次启动都新建一个线程, 链接8000端口
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                // 每2秒发送一个消息
                while (true) {
                    String content = Thread.currentThread().getName()
                            + ":" + Calendar.getInstance().getTime()
                            + ":Hello world!";
                    socket.getOutputStream().write(content.getBytes());
                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
