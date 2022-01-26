package com.pu.fansystem.netty.codec.protobuf.multiple;

import com.pu.fansystem.netty.codec.protobuf.proto.MultipleData;
import com.pu.fansystem.netty.codec.protobuf.proto.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class MProtobufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MultipleData.MsgInfo msgInfo = null;
        int random = new Random().nextInt(3);
        if(random == 0){
            msgInfo = MultipleData.MsgInfo.newBuilder()
                    .setMsgType(MultipleData.MsgInfo.MsgType.userType)
                    .setUser(MultipleData.User.newBuilder().setId(6668).setName("尘民").build()).build();
        }else{
            msgInfo = MultipleData.MsgInfo.newBuilder()
                    .setMsgType(MultipleData.MsgInfo.MsgType.workerType)
                    .setWorker(MultipleData.Worker.newBuilder().setAge(23).setType("修理工").build()).build();
        }
        ctx.writeAndFlush(msgInfo);
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
