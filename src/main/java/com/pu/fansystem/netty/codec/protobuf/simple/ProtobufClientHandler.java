package com.pu.fansystem.netty.codec.protobuf;

import com.pu.fansystem.netty.codec.protobuf.proto.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(9527).setName("尘民").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 读取事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将 msg 转成一个byteBuf
        ByteBuf buf = (ByteBuf) msg;
        // 读取消息
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
        System.out.println("收到客户端发送消息：" + buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
