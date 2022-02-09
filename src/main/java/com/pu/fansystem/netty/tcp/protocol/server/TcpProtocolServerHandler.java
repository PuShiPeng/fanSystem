package com.pu.fansystem.netty.tcp.protocol.server;

import com.pu.fansystem.netty.tcp.protocol.MsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class TcpProtocolServerHandler extends SimpleChannelInboundHandler<MsgProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgProtocol msg) throws Exception {
        System.out.println("服务端收到数据的长度：" + msg.getLen());
        System.out.println("服务端收到数据的消息：" + new String(msg.getContent(), Charset.forName("utf-8")));

        String response = UUID.randomUUID().toString();
        byte[] content = response.getBytes(StandardCharsets.UTF_8);
        MsgProtocol msgProtocol = new MsgProtocol();
        msgProtocol.setLen(content.length);
        msgProtocol.setContent(content);
        ctx.writeAndFlush(msgProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
