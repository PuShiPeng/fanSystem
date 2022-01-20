package com.pu.fansystem.netty.groupchat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class GroupChatHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE 是一个全局事件执行器，单例模式
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 连接建立成功时目的一个被执行的方法
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将当前channel上线推送给其他在线channel
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "上线了~\n");
        // 将当前channel加入channelGroup
        channelGroup.add(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }
}
