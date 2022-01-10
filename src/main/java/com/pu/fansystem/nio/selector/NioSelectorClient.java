package com.pu.fansystem.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * selector实现客户端
 */
public class NioSelectorClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        if(!socketChannel.connect(new InetSocketAddress("localhost",6666))){

            while (!socketChannel.finishConnect()){
                System.out.println("暂未连接到服务端！等待~~~~");
                Thread.sleep(2000);
            }
        }

        ByteBuffer buffer = ByteBuffer.wrap(("客户端：现在时间" + new Date()).getBytes());

        socketChannel.write(buffer);
        System.in.read();
    }
}
