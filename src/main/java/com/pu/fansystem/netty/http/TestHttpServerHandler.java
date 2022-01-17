package com.pu.fansystem.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 子类
 * HttpObject 客户端和服务器端互相通讯的数据封装
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("客户端："+ ctx.channel().remoteAddress());
        // 判断是否是HttpObject
        if(msg instanceof HttpObject){
            // 回应内容
            ByteBuf buf = Unpooled.copiedBuffer("hello,my is server!", CharsetUtil.UTF_8);
            // 构建HTTP Response -> DefaultFullHttpResponse(http协议版本，http状态码，内容)
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            // 请求头设置
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
            // 响应消息
            ctx.channel().writeAndFlush(response);
        }
    }
}
