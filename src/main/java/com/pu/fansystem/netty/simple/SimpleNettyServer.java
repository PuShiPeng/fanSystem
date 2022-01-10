package com.pu.fansystem.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyServer {
    public static void main(String[] args) throws Exception {
        // 1.创建bossGroup和workerGroup(类型：EventLoopGroup)
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 2.创建服务器端启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 3.链式编程配置参数
        bootstrap.group(bossGroup,workerGroup)  // 设置线程组
                .channel(NioServerSocketChannel.class)  // 使用NioServerSocketChannel作为服务端的通道实现
                .option(ChannelOption.SO_BACKLOG,128)   // 设置线程队列得到连接个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)   // 设置保持活动连接个数
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 给workerGroup的EventLoop所对应的管道设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);
                    }
                });
        // 4.绑定端口并且同步
        ChannelFuture channelFuture = bootstrap.bind(6668).sync();
        // 5.监听通道关闭时处理
        channelFuture.channel().closeFuture().sync();
    }
}
