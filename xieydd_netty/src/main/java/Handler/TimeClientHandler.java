package Handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 

* @ClassName: TimeClientHandler 

* @Description: TODO(TimeClient�Ĵ����࣬��32λʱ����Ϣת����ʱ���ʽ) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-22 ����3:17:30 

*
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	/*@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf b = (ByteBuf)msg;
		try{
			Long time =  (b.readUnsignedInt()-2208988800L)*1000L;
			System.out.print(new Date(time));
			ctx.close();
		}finally{
			b.release();
		}
	}*/
	//��һ���޸�
	/**
	 * ԭ��:������TCP/TP�н��ܵ��������Socket��BUf���еģ����������䲢���ǰ����ж����ֽڶ��У����ܳ������������ abc def igh�����뵫���ڻ����ֽڶ����н�������� abcd ef igh����
	 * ���������32λ���ݿ��ܻᱻ���
	 */
	//�������1:����һ���ڲ��ɻ��������ȵ�4���ֽڵ�������ȫ����
	/*private ByteBuf buf;
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		buf.release();
		buf = null;
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		buf = ctx.alloc().buffer(4);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf b = (ByteBuf)msg;
		buf.writeBytes(b);
		b.release();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(buf.readableBytes()>=4) {
			long time = (buf.readUnsignedInt()- 2208988800L)*1000L;
			
			System.out.println(sdf.format(new Date(time)));
			ctx.close();
		}
	}*/
	
	//����2
	/**
	 * ���пɱ䳤�ȵ��ֶ���ɸ��Ӹ��ӵ�Э�飬��һ�ַ�������ʮ���鷳
	 * ������channelHandler���ΪTimeDecoder�������ݲ�� ���� ;TimeClientHandlerԭʼ�汾ʵ��
	 * Netty�ṩ����չ�����TimeDecoder
	 * ByteToMessageDecoder �� ChannelInboundHandler ��һ��ʵ���࣬�������ڴ������ݲ�ֵ������ϱ�úܼ�
	 * �������ݽ��ܵ�ʱ������decode�����������ݽ��д����ڲ����ۼƻ���;���ۼƻ�����û���㹻�����ݵ�ʱ�������out�����з���������ݣ����и�������ݱ����ܵ�ʱ���������������������decode
	 * public class TimeDecoder extends ByteToMessageDecoder { // (1)
			@Override
			protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
				if (in.readableBytes() < 4) {
					return;
				}
				out.add(in.readBytes(4)); 
			}
		}
		
		
	 */
	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
}
