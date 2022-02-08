package com.pu.fansystem.netty.codec.handler.server;

import com.pu.fansystem.netty.codec.handler.client.LongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class HandlerServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 在pipeline中入站和出站的handler是分开的
        pipeline.addLast(new ByteToLongDecoder());
        pipeline.addLast(new LongToByteEncoder());
        pipeline.addLast(new HandlerServerHandler());
    }
}
