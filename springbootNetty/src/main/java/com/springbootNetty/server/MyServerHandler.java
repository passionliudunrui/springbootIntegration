package com.springbootNetty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 业务处理类 一般使用单例模式  所有的pipeline共享这个Handler 加入了@ChannelHandler.Sharable
 *
 */

@Service
@ChannelHandler.Sharable
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];

        msg.readBytes(buffer);

        //将buffer转成字符串
        String message = new String(buffer, CharsetUtil.UTF_8);

        System.out.println("服务器接收到数据是"+message);

        System.out.println("服务器接收到的数据量"+(++this.count));

        //服务器会送数据给客户端  回送一个随机id

        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));

        ctx.writeAndFlush(responseByteBuf);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
