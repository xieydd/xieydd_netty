package Chat_Java_Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 

* @ClassName: SimpleChatServerHandler 

* @Description: TODO(服务端处理器:处理接受到连接，断开连接，读取连接，监听客户端活动和不活动以及IO异常抛出) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 下午5:10:30 

*
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String>{

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	//覆盖了 handlerAdded()事件处理方法。每当从服务端收到新的客户端连接时，客户端的 Channel 存入ChannelGroup列表中，并通知列表中的其他客户端 Channel
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[Server]-"+incoming.remoteAddress()+"加入\n");
		}
		channels.add(ctx.channel());
	}
	
	//.覆盖了 handlerRemoved() 事件处理方法。每当从服务端收到客户端断开时，客户端的 Channel 移除 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[Server]-"+channel.remoteAddress()+"离开\n");
		}
		channels.remove(ctx.channel());
	}
	
	//覆盖了 channelRead0() 事件处理方法。每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel。其中如果你使用的是 Netty 5.x 版本时，需要把 channelRead0() 重命名为messageReceived()
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// TODO Auto-generated method stub
		Channel channel = ctx.channel();
		for (Channel incoming : channels) {
			if(channel != incoming) {
				channel.writeAndFlush("["+incoming.remoteAddress()+"]"+msg+"\n");
			}else{
				channel.writeAndFlush("[you]"+msg+"\n");
			}
		}
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient"+incoming.remoteAddress()+"掉线");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
		
		//当出现异常的时候关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
