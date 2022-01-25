package com.pu.fansystem.netty.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端发送的数据实际再这个方法里面
     * @param ctx 上下文对象，包含：pipeline,channel,地址等
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将 msg 转成一个byteBuf
        ByteBuf buf = (ByteBuf) msg;
        // 读取消息
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        System.out.println("收到客户端发送消息：" + buf.toString(CharsetUtil.UTF_8));

        /*************************************************************************************
         * 当要处理耗时较长的业务时，需要异步执行 ——>提交给当前channel对应的NIOEventLoop中的TaskQueue  *
         * TaskQueue（任务队列），多个任务时，在同一个线程，会等待任务顺序执行                         *
         *************************************************************************************/
        /**
         * 方法一
         * 用户程序自定义的普通任务,此任务提交到 TaskQueue
         * execute(Runnable)
         */
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(10000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        /**
         * 方法二
         * 用户自定义定时任务，此任务提交到 ScheduleTaskQueue
         * schedule(Runnable,long time,TimeUnit timeType)
         */
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(10000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        },5, TimeUnit.SECONDS);
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
