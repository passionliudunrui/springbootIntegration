package com.springbootNetty.client;

import com.sun.tracing.dtrace.Attributes;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private MyClientHandler myClientHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(myClientHandler);

    }
}
