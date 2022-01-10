package com.pu.fansystem.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 多人聊天服务端
 */
public class GroupChatServer {

    private ServerSocketChannel serverChannel;
    private Selector selector;
    private static final int PORT = 6667;

    /**
     * 初始化
     */
    public GroupChatServer(){
        try {

            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器已启动！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }

    /**
     * 监听
     */
    public void listen(){
        try {
            while (true){
                selector.select();
//                int count = selector.select(2000);
//                if(count == 0){
//                    System.out.println("服务器监听中~~~~");
//                    continue;
//                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();

                while (keyIterator.hasNext()){
                    SelectionKey key = keyIterator.next();
                    // 连接事件
                    if(key.isAcceptable()){
                        SocketChannel channel = serverChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector,SelectionKey.OP_READ);
                        System.out.println(channel.getRemoteAddress() + " 上线啦~");
                    }

                    // 读取事件
                    if(key.isReadable()){
                        // 读取业务处理
                        readData(key);
                    }

                    // 删除当前事件key
                    keyIterator.remove();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取业务
     * @param key
     */
    public void readData(SelectionKey key){
        SocketChannel channel = null;
        try {
            // 得到通道
            channel = (SocketChannel)key.channel();
            // 输出消息
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if(count > 0){
                String msg = new String(buffer.array());
                msg = channel.getRemoteAddress() + "发送消息：" + msg;
                System.out.println(msg);
                // 转发消息
                sendData(msg,channel);
            }
        }catch (IOException e){
            // 客户端异常，关闭通道和移除selector监听
            try {
                System.out.println(channel.getRemoteAddress() + "离线了~~~~");
                channel.close();
                key.cancel();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 转发消息到其他客户端
     * @param msg
     * @param self
     */
    public void sendData(String msg,SocketChannel self) throws IOException{
        // 获取当前注册的所有通道
        Set<SelectionKey> keys = selector.keys();
        Iterator<SelectionKey> keyIterator = keys.iterator();
        int count = 0;
        while (keyIterator.hasNext()){
            SelectionKey key = keyIterator.next();
            Channel channel = key.channel();
            if(channel instanceof SocketChannel && channel != self){
                ByteBuffer send = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel)channel).write(send);
                ++count;
            }
        }
        System.out.println("转发了" + count + "条");
    }
}
