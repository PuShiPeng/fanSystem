package com.pu.fansystem.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 子类
 * HttpObject 客户端和服务器端互相通讯的数据封装
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        /**
         * http协议是一个短链接，当浏览器请求刷新时，都会新生成一个 pipeline
         **/
        // 判断是否是HttpObject
        if(msg instanceof HttpRequest){
            System.out.println("TestHttpServerHandler的hashcode："+this.hashCode()+";pipeline的hashcode："+ctx.pipeline().hashCode());
            System.out.println("消息的类型：" + msg.getClass());
            System.out.println("客户端地址："+ ctx.channel().remoteAddress());
            // 收到的msg判断header
            HttpRequest request = (HttpRequest) msg;
            // 获取uri，过滤掉/favicon.ico资源请求
            URI uri = new URI(request.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("/favicon.ico请求不做处理！");
                return;
            }
            // 回应内容
            ByteBuf buf = Unpooled.copiedBuffer("hello,我是服务器!", CharsetUtil.UTF_8);
            // 构建HTTP Response -> DefaultFullHttpResponse(http协议版本，http状态码，内容)
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            // 请求头设置
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
            // 响应消息
            ctx.channel().writeAndFlush(response);
        }
    }
}
