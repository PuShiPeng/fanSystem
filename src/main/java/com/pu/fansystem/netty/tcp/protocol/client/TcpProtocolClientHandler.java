package com.pu.fansystem.netty.tcp.protocol.client;

import com.pu.fansystem.netty.tcp.protocol.MsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class TcpProtocolClientHandler extends SimpleChannelInboundHandler<MsgProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgProtocol msg) throws Exception {
        System.out.println("客户端收到数据的长度：" + msg.getLen());
        System.out.println("客户端收到数据的消息：" + new String(msg.getContent(), Charset.forName("utf-8")));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            byte[] content = ("美好的第" + (i + 1) + "天").getBytes(Charset.forName("utf-8"));
            MsgProtocol msgProtocol = new MsgProtocol();
            msgProtocol.setLen(content.length);
            msgProtocol.setContent(content);
            ctx.writeAndFlush(msgProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
