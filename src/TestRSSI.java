import java.util.HashMap;
import java.util.Map;


public class TestRSSI {
	
	public final float A = 47.0f;
	public final float n = 4;

	
	public static void main(String[] args) {
		
		Map<Object, Object> info = new HashMap<Object, Object>();
		info.put("t", 12);
		info.put("EPC", 12);
		info.put("ID", 1);
		info.put("RSSI", 60);
		
		
	}
	
	
	
	/*
	 * 杜航 
	 * 功能：实现RSSI单点检测
	 * 接收参数：receive包含：t EPC ID RSSI
	 * 返回参数：
	 */
	public void singlePoint(Map receive){
		
	}
	
}
