package com.pu.fansystem.netty.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgToByteEncoder extends MessageToByteEncoder<MsgProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MsgToByteEncoder 的 encode被调用！");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
