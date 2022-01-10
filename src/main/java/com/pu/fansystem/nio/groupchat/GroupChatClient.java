package com.pu.fansystem.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {

    private final static String HOST = "localhost";
    private final static Integer PORT = 6667;
    private SocketChannel channel;
    private Selector selector;

    public GroupChatClient(){
        try {
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(HOST,PORT));
            channel.configureBlocking(false);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("登录成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatClient chatClient = new GroupChatClient();

        new Thread(() -> {
            try {
                while (true){
                    chatClient.readInfo();
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            chatClient.sendInfo(scanner.nextLine());
        }
    }

    public void sendInfo(String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(buffer);
    }

    public void readInfo() throws IOException {
        int readCount = selector.select();
        if(readCount > 0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isReadable()){
                    ByteBuffer info = ByteBuffer.allocate(1024);
                    SocketChannel channel = (SocketChannel)key.channel();
                    channel.read(info);
                    System.out.println(new String(info.array()));
                }
                iterator.remove();
            }

        }

    }
}
