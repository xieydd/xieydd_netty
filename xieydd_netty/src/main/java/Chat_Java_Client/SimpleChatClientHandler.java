package Chat_Java_Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 

* @ClassName: SimpleChatClientHandler 

* @Description: TODO(����ͻ��˼������ݴ�ӡ����) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-29 ����3:42:12 

*
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String>{
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
}
