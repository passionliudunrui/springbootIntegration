package com.springbootNetty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("myClient")
public class MyClient {

        @Autowired
        private MyClientHandler myClientHandler;

        EventLoopGroup group = new NioEventLoopGroup(1);


        public void start() throws InterruptedException {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(myClientHandler);//因为只有一个线程组 所以直接把业务代码加到这个线程组就可以了。
            ChannelFuture channelFuture = bootstrap.connect("localhost", 7000).sync();
            System.out.println("客户端绑定完成");
            channelFuture.channel().closeFuture().sync();


        }

        public void destory(){
            group.shutdownGracefully();

        }

        public void run(){
            try {

                Bootstrap bootstrap = new Bootstrap();

                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(myClientHandler);//因为只有一个线程组 所以直接把业务代码加到这个线程组就可以了。
                ChannelFuture channelFuture = bootstrap.connect("localhost", 7000).sync();
                System.out.println("客户端绑定完成");
                channelFuture.channel().closeFuture().sync();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }


}