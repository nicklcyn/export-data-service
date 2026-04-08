package com.example.export.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HeartbeatHandler extends SimpleChannelInboundHandler<String> {

    private static final Set<Channel> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        sessions.add(ctx.channel());
        log.info("New session: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        sessions.remove(ctx.channel());
        log.info("Session closed: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // 只記錄訊息，不做轉換
        log.info("Received from " + ctx.channel().remoteAddress() + ": " + msg);

        // 處理心跳訊息
        if ("PING".equals(msg)) {
            ctx.writeAndFlush("PONG");
        } else if ("PONG".equals(msg)) {
            log.info("Heartbeat OK from " + ctx.channel().remoteAddress());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            switch (event.state()) {
                case READER_IDLE:
                    log.info("No data received, closing: " + ctx.channel().remoteAddress());
                    ctx.close();
                    break;
                case WRITER_IDLE:
                    log.info("No data sent, sending heartbeat...");
                    ctx.writeAndFlush("PING");
                    break;
                default:
                    break;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

