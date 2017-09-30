package Chat_Java_Server_WS;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 

* @ClassName: TextWebSocketFrameHandler 

* @Description: TODO(显示处理 TextWebSocketFrame) 
WebSocket在帧里面发送数据，Request of Commments定义六种不同的Frame  Netty 给他们每个都提供了一个 POJO实现 ，而我们的程序只需要使用下面4个帧类型：
• CloseWebSocketFrame
• PingWebSocketFrame
• PongWebSocketFrame
• TextWebSocketFrame
当WebSocket 与新客户端已成功握手完成，通过写入信息到 ChannelGroup 中的 Channel 来通知所有连接的客户端，然后添加新 Channel 到 ChannelGroup
如果接收到 TextWebSocketFrame，调用 retain() ，并将其写、刷新到 ChannelGroup，使所有连接的
WebSocket Channel 都能接收到它。和以前一样，retain() 是必需的，因为当 channelRead0（）返回
时，TextWebSocketFrame 的引用计数将递减。由于所有操作都是异步的，writeAndFlush() 可能会在以
后完成，我们不希望它来访问无效的引用


* @author xieydd xieydd@gmail.com  

* @date 2017-9-30 下午2:18:41 

*
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			TextWebSocketFrame msg) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		
		for (Channel channel : channels) {
			if(channel != incoming) {
				channel.writeAndFlush(new TextWebSocketFrame("["+incoming.remoteAddress()+"]"+msg.text()));
			}else {
				channel.writeAndFlush(new TextWebSocketFrame("[you]"+msg.text()));
			}
		}			
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			ctx.writeAndFlush(new TextWebSocketFrame("[Server]"+incoming.remoteAddress()+"加入"));
		}
		channels.add(incoming);
		System.out.println("Client:"+incoming.remoteAddress() +"加入");
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			ctx.writeAndFlush(new TextWebSocketFrame("[Server]"+incoming.remoteAddress()+"离开"));
		}
		System.out.println("Client:"+incoming.remoteAddress() +"离开");
		channels.remove(ctx.channel());
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"掉线");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
