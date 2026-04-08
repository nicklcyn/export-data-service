package com.example.export.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new StringEncoder());
        // IdleStateHandler: 10秒沒讀到資料，5秒沒寫資料
        ch.pipeline().addLast(new IdleStateHandler(30, 5, 0, TimeUnit.SECONDS));
        ch.pipeline().addLast(new HeartbeatHandler());
    }
}