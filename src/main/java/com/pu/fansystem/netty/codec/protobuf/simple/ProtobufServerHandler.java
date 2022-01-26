package com.pu.fansystem.netty.codec.protobuf.simple;

import com.pu.fansystem.netty.codec.protobuf.proto.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端发送的数据实际再这个方法里面
     * @param ctx 上下文对象，包含：pipeline,channel,地址等
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StudentPOJO.Student student = (StudentPOJO.Student)msg;
        System.out.println("收到对象的Id: " + student.getId() + "; name: " + student.getName());
    }

    /**
     * 数据读取完毕后调用的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 回复客户端消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("收到信息了~",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常的方法，一般需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
