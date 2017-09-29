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

* @Description: TODO(����˴�����:������ܵ����ӣ��Ͽ����ӣ���ȡ���ӣ������ͻ��˻�Ͳ���Լ�IO�쳣�׳�) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 ����5:10:30 

*
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String>{

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	//������ handlerAdded()�¼���������ÿ���ӷ�����յ��µĿͻ�������ʱ���ͻ��˵� Channel ����ChannelGroup�б��У���֪ͨ�б��е������ͻ��� Channel
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[Server]-"+incoming.remoteAddress()+"����\n");
		}
		channels.add(ctx.channel());
	}
	
	//.������ handlerRemoved() �¼���������ÿ���ӷ�����յ��ͻ��˶Ͽ�ʱ���ͻ��˵� Channel �Ƴ� ChannelGroup �б��У���֪ͨ�б��е������ͻ��� Channel
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[Server]-"+channel.remoteAddress()+"�뿪\n");
		}
		channels.remove(ctx.channel());
	}
	
	//������ channelRead0() �¼���������ÿ���ӷ���˶����ͻ���д����Ϣʱ������Ϣת���������ͻ��˵� Channel�����������ʹ�õ��� Netty 5.x �汾ʱ����Ҫ�� channelRead0() ������ΪmessageReceived()
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
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"����");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient"+incoming.remoteAddress()+"����");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"�쳣");
		
		//�������쳣��ʱ��ر�����
		cause.printStackTrace();
		ctx.close();
	}
}
