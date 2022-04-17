package com.springbootNetty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务端Handler的初始化
 * @Server交给spring管理  相当于xml文件中的bean
 */

@Service
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MyServerHandler myServerHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(myServerHandler);
    }


}
