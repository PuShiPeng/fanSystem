package com.pu.fansystem.netty.groupchat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGroupChatHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE 是一个全局事件执行器，单例模式
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 连接建立成功时第一个被执行的方法
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将当前channel上线推送给其他在线channel
        channelGroup.writeAndFlush(sdf.format(new Date()) + ":[客户端]" + channel.remoteAddress() + "加入聊天~\n");
        // 将当前channel加入channelGroup
        channelGroup.add(channel);
    }
    /**
     * 连接断开时被执行的方法
     * channelGroup会直接移除掉当前的channel
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date()) + ":[客户端]" + channel.remoteAddress() + "退出聊天~\n");
        System.out.println("当前group的大小："+channelGroup.size());
    }

    /**
     * channel激活时调用，活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了~\n");
    }

    /**
     * channel失活时调用，非活动状态
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了~\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        // 转发信息
        channelGroup.forEach(ch -> {
            if(ch == channel) return;
            ch.writeAndFlush(sdf.format(new Date()) + ":[客户]" + channel.remoteAddress() + "说：" + msg + "\n");
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
