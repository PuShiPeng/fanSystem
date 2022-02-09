package com.pu.fansystem.netty.codec.handler.client;

import com.pu.fansystem.netty.codec.handler.ByteToLongDecoder;
import com.pu.fansystem.netty.codec.handler.LongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class HandlerClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LongToByteEncoder());
        pipeline.addLast(new ByteToLongDecoder());
        pipeline.addLast(new HandlerClientHandler());
    }
}
