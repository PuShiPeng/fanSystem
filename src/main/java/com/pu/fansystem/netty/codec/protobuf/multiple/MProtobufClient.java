package com.pu.fansystem.netty.codec.protobuf.multiple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class MProtobufClient {

    public static void main(String[] args) throws Exception {
        // 1.客户端只需要一个NioEventLoopGroup
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        try{
            // 2.创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            // 3.链式编程配置参数
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder",new ProtobufEncoder());
                            pipeline.addLast(new MProtobufClientHandler());
                        }
                    });
            // 4.连接服务器
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();
            // 5.监听通道关闭时处理
            channelFuture.channel().closeFuture().sync();
        } finally {
            clientGroup.shutdownGracefully();
        }

    }
}
