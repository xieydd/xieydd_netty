package Decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Pojo.UnixTime;

/**
 * 

* @ClassName: TimeDecoder 

* @Description: TODO(ʱ�����������TimeClient��ch.pipeline().addLast(new TimeDecoder(),new TimeClientHandler());����TimeClientHandlerc���) 
���пɱ䳤�ȵ��ֶ���ɸ��Ӹ��ӵ�Э�飬��һ�ַ�������ʮ���鷳
	 * ������channelHandler���ΪTimeDecoder�������ݲ�� ���� ;TimeClientHandlerԭʼ�汾ʵ��
	 * Netty�ṩ����չ�����TimeDecoder
	 * ByteToMessageDecoder �� ChannelInboundHandler ��һ��ʵ���࣬�������ڴ������ݲ�ֵ������ϱ�úܼ�
	 * �������ݽ��ܵ�ʱ������decode�����������ݽ��д����ڲ����ۼƻ���;���ۼƻ�����û���㹻�����ݵ�ʱ�������out�����з���������ݣ����и�������ݱ����ܵ�ʱ���������������������decode
	 * �����decode�������out������ζ�Ž���������ɹ��������������ۼƻ����ж�ȡ��������
* @author xieydd xieydd@gmail.com  

* @date 2017-9-22 ����5:24:37 

*
 */
public class TimeDecoder extends ByteToMessageDecoder {
	

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if (in.readableBytes() < 4) {
			return;
		}
		out.add(new UnixTime(in.readUnsignedInt()));
	}
}
