package com.pu.fansystem.netty.tcp.protocol.client;

import com.pu.fansystem.netty.tcp.protocol.ByteToMsgDecoder;
import com.pu.fansystem.netty.tcp.protocol.MsgToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class TcpProtocolClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MsgToByteEncoder());
        pipeline.addLast(new ByteToMsgDecoder());
        pipeline.addLast(new TcpProtocolClientHandler());
    }
}
