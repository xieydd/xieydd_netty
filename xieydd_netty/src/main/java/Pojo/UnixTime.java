package Pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 

* @ClassName: UnixTime 

* @Description: TODO(ʹ��POJO(������UnixTIme)����ByteBufʹChannelHandler��ÿ�ά���Ϳ�����) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 ����11:42:07 

*
 */
public class UnixTime {
	private final long value;
	
	public UnixTime() {
		this(System.currentTimeMillis()/1000L+2208988800L);
	}
	
	public UnixTime(long value) {
		this.value = value;
	}
	
	public long value() {
		return value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(new Date((value()-2208988800L)*1000L));
	}
}
