package com.pu.fansystem.netty.codec.handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class HandlerClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 当write的类型是InboundHandler指定类型时，会调用编码器的encode方法，而指定类型不一致则跳过编码器的encode方法
         *      这是因为解码器继承MessageToByteEncoder中write方法判断了数据类型：
         *      当满足时到decoder方法，不满足时直接传给下一个handler
         */
//        ctx.writeAndFlush(12345L);
        ctx.writeAndFlush(Unpooled.copiedBuffer("adbsdbfajukcdfdd", CharsetUtil.UTF_8));
    }
}
