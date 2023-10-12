import controller.ServerThread;
import controller.ThreadPool;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;

public class Client {
        public static void main(String[] args) throws IOException {
            ThreadPool threadPool=ThreadPool.getThreadPool();

            Selector selector = null;
            ServerSocketChannel serverSocketChannel = null;
            StringBuffer result = null;
            try {
                selector = Selector.open();
                //net -> pc
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(8080));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                result = new StringBuffer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            threadPool.executor(new ServerThread(selector,serverSocketChannel,result));
    }
}
