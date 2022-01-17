package com.pu.fansystem.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // HttpServerCodec  codec => coder decoder
        // netty 提供处理 http 的 编码解码器
        ch.pipeline().addLast("MyHttpServerCodec",new HttpServerCodec());
        ch.pipeline().addLast("MyHttpServerHandler",new TestHttpServerHandler());
    }
}
