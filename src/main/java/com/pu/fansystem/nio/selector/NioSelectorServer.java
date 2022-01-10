package com.pu.fansystem.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * selector实现服务端
 */
public class NioSelectorServer {

    public static void main(String[] args) throws Exception{

        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 创建Selector
        Selector selector = Selector.open();
        // 将ServerSocketChannel注册到Selector中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            // 监听是否有通道时间发生
            if(selector.select(2000) == 0){
                System.out.println("等待客户端连接~~");
                continue;
            }
            // 获取有时间发生的SelectionKey
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()){

                SelectionKey key = keyIterator.next();
                // 处理连接事件
                if(key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功");
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }
                // 处理读取事件
                if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();

                    channel.read(byteBuffer);
                    System.out.println("数据：" + new String(byteBuffer.array()));
                }
                // 处理完毕移除当前SelectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }

}
