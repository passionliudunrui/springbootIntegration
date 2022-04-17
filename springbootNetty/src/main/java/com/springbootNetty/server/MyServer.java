package com.springbootNetty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MyServer {

        @Autowired
        private MyServerInitializer myServerInitializer;

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        public void start() throws InterruptedException {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(myServerInitializer);
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            System.out.println("绑定端口完成");

            channelFuture.channel().closeFuture().sync();

        }

        public void destory(){
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }

        public void run(){
            try{

                ServerBootstrap serverBootstrap = new ServerBootstrap();

                serverBootstrap.group(bossGroup,workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(myServerInitializer);
                ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
                System.out.println("绑定端口完成");

                channelFuture.channel().closeFuture().sync();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();

            }
        }


}
