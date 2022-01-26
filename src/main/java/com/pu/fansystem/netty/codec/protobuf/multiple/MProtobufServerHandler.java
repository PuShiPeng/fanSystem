package com.pu.fansystem.netty.codec.protobuf.multiple;

import com.pu.fansystem.netty.codec.protobuf.proto.MultipleData;
import com.pu.fansystem.netty.codec.protobuf.proto.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MProtobufServerHandler extends SimpleChannelInboundHandler<MultipleData.MsgInfo> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MultipleData.MsgInfo msg) throws Exception {
        MultipleData.MsgInfo.MsgType msgType = msg.getMsgType();
        switch (msgType){
            case userType:
                MultipleData.User user = msg.getUser();
                System.out.println("用户id：" + user.getId() + "；用户名称：" + user.getName());
                break;
            case workerType:
                MultipleData.Worker worker = msg.getWorker();
                System.out.println("工作类别：" + worker.getType() + "；工作年龄：" + worker.getAge());
                break;
            default:
                System.out.println("暂无处理");
                break;
        }
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
