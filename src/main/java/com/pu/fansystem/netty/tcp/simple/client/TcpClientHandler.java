package com.pu.fansystem.netty.tcp.simple.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.Buffer;
import java.nio.charset.Charset;

public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] message = new byte[msg.readableBytes()];
        msg.readBytes(message);
        String str = new String(message, Charset.forName("utf-8"));
        System.out.println("客户端收到消息：" + str);
        System.out.println("客户端收到消息次数：" + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf send = Unpooled.copiedBuffer("hello,server" + i, Charset.forName("utf-8"));
            ctx.writeAndFlush(send);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
