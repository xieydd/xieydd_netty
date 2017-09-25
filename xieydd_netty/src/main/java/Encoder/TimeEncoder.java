package Encoder;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import Pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 

* @ClassName: TimeEncoder 

* @Description: TODO(时间编码器:对于UnixTime类) 
通过ChannelPromise，当编码后的数据完全被写到通道上Netty会通过振哥对象标记成功和失败
不需要ctx.flush()，因为处理器单独分离出一个方法void flush(ChannelHandlerContext cxt),当然自己实现这个方法可以覆盖
* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 下午1:51:07 

*
 */
/*public class TimeEncoder extends ChannelOutboundHandlerAdapter{
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		UnixTime m = new UnixTime();
		ByteBuf encoded = ctx.alloc().buffer(4);
		encoded.writeInt((int)m.value());
		ctx.write(encoded, promise);
	}
	
}*/
//进一步修改
public class TimeEncoder extends MessageToByteEncoder<UnixTime>{

	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out)
			throws Exception {
		// TODO Auto-generated method stub
		out.writeInt((int)msg.value());
	}
	
}