package Chat_Java_Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 

* @ClassName: SimpleChatClientHandler 

* @Description: TODO(聊天客户端即将数据打印出来) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-29 下午3:42:12 

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
