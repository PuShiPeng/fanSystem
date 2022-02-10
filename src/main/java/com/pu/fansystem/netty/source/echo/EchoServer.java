/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.pu.fansystem.netty.source.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Echoes back any received data from a client.
 */
public final class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        /**
         * 1.bossGroup、workerGroup是整个netty的核心对象，整个netty运作都依赖他们，bossGroup用于接收Tcp请求，会将请求交给workerGroup
         * 而workerGroup获取真正的连接，然后和连接进行通信
         * 2.EventLoopGroup是事件循环组（线程组），含有多个EventLoop，可以注册Channel，用于在事件循环中进行选择（和选择器相关）
         * 3.new NioEventLoopGroup(1)表示事件组有一个线程可以指定，如果没有参数，默认为CPU核数*2
         *      源码：
         *      DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
         *                 "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
         * 4.children 初始化 children = new EventExecutor[nThreads]后是EventExecutor接口类型，当赋值线程组后是NioEventLoop类型
         * 5.new NioEventLoopGroup()会调用下面这个方法
         *          /**
         *          *
         *          * @param nThreads          使用的线程数，默认为core*2
         *          * @param executor          执行器：如果传入null，则才用默认的线程工厂和默认的执行器ThreadPerTaskExecutor
         *          * @param chooserFactory    单例的 new DefaultEventExecutorChooserFactory()
         *          * @param args              在创建执行器时传入的固定参数
         *          protected MultithreadEventExecutorGroup(int nThreads, Executor executor,
         *                                             EventExecutorChooserFactory chooserFactory, Object... args)
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }
                     //p.addLast(new LoggingHandler(LogLevel.INFO));
                     p.addLast(new EchoServerHandler());
                 }
             });

            // Start the server.
            /**
             * bind方法创建了一个端口对象，并做了一些空判断
             * 和新方法：initAndRegister()、doBind0()
             * initAndRegister():
             *      channel = channelFactory.newChannel() 通过ServerBootstrap的通道工厂反射创建一个NioServerSocketChannel
             *      1.通过NIO的SelectorProvider的openServerSocketChannel方法得到JDK的channel，目的是让Netty包装JDK的channel
             *      2.创建了一个唯一的ChannelId，创建了一个NioMessageUnsafe，用于操作消息，创建了一个DefaultChannelPipeline管道，
             *          是一个双向链表结构，用于过滤所有的进出消息
             *      3.创建了一个NioServerSocketChannelConfig对象，用于对外展示一些配置
             *      所以，initAndRegister()初始化NioServerSocketChannel通道并注册各个handler，返回一个future
             * init(channel):
             *      1.这是个抽象方法，由ServerBootstrap实现 // setChannelOptions(channel, options0().entrySet().toArray(newOptionArray(0)), logger);
             *      2.设置了NioServerSocketChannel的TCP属性
             *      3.由于LinkedHashMap是非线程安全的，所以使用同步处理
             *      4.对NioServerSocketChannel的ChannelPipeline添加ChannelInitializer处理器
             *      所以，init()方法的核心作用是和ChannelPipeline关联，所以pipeline是一个双向链表，初始化了head和tail，调用它的
             *          addLast方法，姐就是将整个handler插入到tail的前面，tail永远在后面，做一些系统固定工作
             * doBind()执行完后，最后一步是safeSetSuccess(promise)，告诉promise任务成功了，可以开始执行监听方法，整个启动结束，
             *  就到run()方法循环执行事件监听
             */
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
