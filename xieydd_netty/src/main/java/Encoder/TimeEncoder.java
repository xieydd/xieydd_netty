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

* @Description: TODO(ʱ�������:����UnixTime��) 
ͨ��ChannelPromise����������������ȫ��д��ͨ����Netty��ͨ���������ǳɹ���ʧ��
����Ҫctx.flush()����Ϊ���������������һ������void flush(ChannelHandlerContext cxt),��Ȼ�Լ�ʵ������������Ը���
* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 ����1:51:07 

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
//��һ���޸�
public class TimeEncoder extends MessageToByteEncoder<UnixTime>{

	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out)
			throws Exception {
		// TODO Auto-generated method stub
		out.writeInt((int)msg.value());
	}
	
}