package com.pu.fansystem.netty.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ByteToMsgDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        MsgProtocol msgProtocol = new MsgProtocol();
        msgProtocol.setLen(len);
        msgProtocol.setContent(content);
        out.add(msgProtocol);
    }
}
