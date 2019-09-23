package NioSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Song on 2019/09/18.
 */
public class NioServer {
    public static void main(String[] args) throws Exception{
        // 轮询器, 轮询是否有新的链接
        Selector serverSelector = Selector.open();
        // 轮询器, 轮询是否有数据可读
        Selector clientSelector = Selector.open();

        new Thread(() -> {
            try {
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                listenerChannel.socket().bind(new InetSocketAddress(8000));
                listenerChannel.configureBlocking(false);
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true) {
                    // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            if (key.isAcceptable()) {
                                try {
                                    // 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    keyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(()->{
            while (true) {
                try {
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeys = clientSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            try {
                                if (key.isReadable()) {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    clientChannel.read(byteBuffer);

                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
                                }
                            } finally {
                                keyIterator.remove();
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
