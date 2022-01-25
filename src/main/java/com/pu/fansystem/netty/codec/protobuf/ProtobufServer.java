package com.pu.fansystem.netty.codec.protobuf;

import com.pu.fansystem.netty.simple.SimpleNettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ProtobufServer {
    public static void main(String[] args) throws Exception {
        // 1.创建bossGroup和workerGroup(类型：EventLoopGroup)
        /**
         * 1.bossGroup只处理连接请求；workerGroup处理客户端的业务处理
         * 2.两个都是轮询（无线循环）
         * 3.bossGroup和workerGroup含有的子线程（ NioEventLoop）的个数是cpu的核数 * 2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            // 2.创建服务器端启动对象
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
                            ch.pipeline().addLast(new SimpleNettyServerHandler());
                        }
                    });
            // 4.绑定端口并且同步
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            // Future机制调用监听器处理逻辑
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("绑定成功");
                    }else{
                        System.out.println("绑定失败");
                    }
                }
            });
            // 5.监听通道关闭时处理
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭group
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
