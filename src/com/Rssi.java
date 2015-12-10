package com;
import java.util.HashMap;
import java.util.Map;

import util.Matrix;

public class Rssi {

	public final static float A = 47.0f;
	public final static float n = 4;

	public static void main(String[] args) {
		// num表示有几个天线的RSSI值不为0
		int num = 0;

		// 以5根天线为例
		Map<Object, String> info1 = new HashMap<Object, String>();
		Map<Object, String> info2 = new HashMap<Object, String>();
		Map<Object, String> info3 = new HashMap<Object, String>();
		Map<Object, String> info4 = new HashMap<Object, String>();
		Map<Object, String> info5 = new HashMap<Object, String>();
		
		Map<String, Map<Object, String>> info = new HashMap<String, Map<Object, String>>();

		// 分别给天线的参数赋值（此处用于模拟）
		info.put("1", info1);
		info.put("2", info2);
		info.put("3", info3);
		info.put("4", info4);
		info.put("5", info5);

		info1.put("ID", "1");
		info1.put("t", "12");
		info1.put("EPC", "12");
		info1.put("RSSI", "-47");
		info1.put("x", "0");
		info1.put("y", "600");

		info2.put("ID", "2");
		info2.put("t", "12");
		info2.put("EPC", "12");
		info2.put("RSSI", "-60");
		info2.put("x", "0");
		info2.put("y", "2700");

		info3.put("ID", "3");
		info3.put("t", "12");
		info3.put("EPC", "12");
		info3.put("RSSI", "-0");
		info3.put("x", "7000");
		info3.put("y", "600");

		info4.put("ID", "4");
		info4.put("t", "12");
		info4.put("EPC", "12");
		info4.put("RSSI", "-30");
		info4.put("x", "7000");
		info4.put("y", "4800");

		info5.put("ID", "5");
		info5.put("t", "12");
		info5.put("EPC", "87");
		info5.put("RSSI", "-34");
		info5.put("x", "7000");
		info5.put("y", "2700");

		// 得到RSSI值的字符串数组
		String[] Rssi = new String[] { info1.get("RSSI"), info2.get("RSSI"), info3.get("RSSI"), info4.get("RSSI"), info5.get("RSSI") };
		// 用于存放RSSI值不为零的天线
		String[] jud = new String[Rssi.length];

		int i = 0;
		for (String s : Rssi) {
			i++;
			if (!s.equals("0")) {
				jud[num] = String.valueOf(i);// 得到RSSI不为0天线序号的数组
				num++;
			}
		}

		// 将字符串数组按照字符串中数字的大小排序
		int[] ia = new int[Rssi.length];
		for (int j = 0; j < Rssi.length; j++) {
			ia[j] = Integer.parseInt(Rssi[j]);
		}
		sort(ia);

		String[] Rssi1 = new String[ia.length];
		for (int j = 0; j < ia.length; j++) {
			Rssi1[j] = String.valueOf(ia[j]);
		}

		// 用于存放经排序之后的RSSI值
		String[] jud1 = new String[Rssi.length * info.size()];
		int n = 0;
		for (int m = 0; m < Rssi1.length; m++) {
			for (int j = 1; j <= Rssi1.length; j++) {
				if ((info.get(String.valueOf(j)).get("RSSI").equals(Rssi1[m]))
						&& !(info.get(String.valueOf(j)).get("RSSI")
								.equals("0"))) {
					jud1[n] = String.valueOf(j);
					n++;
				}
			}
		}

		switch (num) {
		case 0:
			System.out.println("船上未感应到任何物体");
			break;
		case 1:
			System.out.println("有一根天线采集到了数据");
			singlePoint(info.get(jud1[0]));
			break;
		case 2:
			System.out.println("有两根天线采集到了数据");
			twoPoints(info.get(jud1[0]), info.get((jud1[1])));
			break;
		default:
			System.out.println("三根及以上天线采集到了数据");
//			System.out.println(info.get(jud1[0]) + "\n" + info.get(jud1[1]) + "\n" + info.get(jud1[2]));
			threePoints(info.get(jud1[0]), info.get(jud1[1]), info.get(jud1[2]));
		}
	}
	
	/*
	 * 胡路瑶
	 * 功能：排序函数用于将所有的RSSI值进行从大到小排序
	 */
	private static void sort(int[] a) {
		 for (int i = 0; i < a.length-1; i++) {
			 for (int j = i+1; j < a.length; j++) {
				 if(a[j]>a[i]){
					 int t=a[j];
					 a[j]=a[i];
					 a[i]=t;
				 }
			 }
		 }
	}
	
	/*
	 * 胡路瑶
	 * 功能：实现单点定位 
	 * 接收参数：map(包含：t EPC ID RSSI x y )
	 * 返回参数：
	 */
	private static void singlePoint(Map map) {
		System.out.println("EPC为："+ map.get("EPC")+"的横坐标为"+map.get("x")+
				"，纵坐标为"+ map.get("y")+",感应到的时间为"+ map.get("t")); 
	}

	/*
	 * 胡路瑶
	 * 功能：实现两点定位 
	 * 接收参数：map1、map2(包含：t EPC ID RSSI x y )
	 * 返回参数：
	 */
	private static void twoPoints(Map map1,Map map2) {
		if(Integer.parseInt( (String) map1.get("RSSI"))>=2*Integer.parseInt((String) map2.get("RSSI"))){
			System.out.println("EPC为："+ map1.get("EPC")+"的横坐标为"+map1.get("x")+
					"，纵坐标为"+ map1.get("y")+",感应到的时间为"+ map1.get("t"));
		}
		else if(2*Integer.parseInt((String) map1.get("RSSI"))<=Integer.parseInt((String) map2.get("RSSI"))){
		System.out.println("EPC为："+ map2.get("EPC")+"的横坐标为"+map2.get("x")+
				"，纵坐标为"+ map2.get("y")+",感应到的时间为"+ map2.get("t")); 
		}
		else{ 
			System.out.println("EPC为："+ map2.get("EPC")+
			"的横坐标为"+(Integer.parseInt((String) map2.get("x"))+Integer.parseInt((String) map1.get("x")))/2+
			"，纵坐标为"+(Integer.parseInt((String) map2.get("y"))+Integer.parseInt((String) map1.get("y")))/2+
			",感应到的时间为"+ map2.get("t")); 
		}
	}
	
	/*
	 * 杜航
	 * 功能：利用公式 RSSI = -(A + 10*n*lg(d)) 求解 d
	 * 接收参数：RSSI(包含：t EPC ID RSSI x y )
	 * 返回参数：d
	 */
	public static float rssiFormula(float RSSI) {
		float d = (float) Math.pow(10,(-(RSSI + A) / (10 * n)));
		return d;
	}
	
	/*
	 * 杜航
	 * 功能：实现RSSI三点定位 
	 * 接收参数：receive1、receive2、receive3(包含：t EPC ID RSSI x y )
	 * 返回参数：result
	 */
	public static Map threePoints(Map receive1, Map receive2, Map receive3) {
		
		// 声明RSSI求解公式中涉及到的参数
		float RSSI1, RSSI2, RSSI3, x1, x2, x3, y1, y2, y3, d1, d2, d3, A1, A2, A3, A4, B1, B2;
		// 获取RSSI值
		RSSI1 = Float.parseFloat(receive1.get("RSSI").toString());
		RSSI2 = Float.parseFloat(receive2.get("RSSI").toString());
		RSSI3 = Float.parseFloat(receive3.get("RSSI").toString());
		// 获取天线横坐标值x
		x1 = Float.parseFloat(receive1.get("x").toString());
		x2 = Float.parseFloat(receive2.get("x").toString());
		x3 = Float.parseFloat(receive3.get("x").toString());
		// 获取天线纵坐标值y
		y1 = Float.parseFloat(receive1.get("y").toString());
		y2 = Float.parseFloat(receive2.get("y").toString());
		y3 = Float.parseFloat(receive3.get("y").toString());
		// 求解天线到标签的距离d
		d1 = rssiFormula(RSSI1);
		d2 = rssiFormula(RSSI2);
		d3 = rssiFormula(RSSI3);
		// 封装矩阵的元素
		A1 = 2 * (x1 - x3);
		A2 = 2 * (y1 - y3);
		A3 = 2 * (x2 - x3);
		A4 = 2 * (y2 - y3);
		B1 = (float) (Math.pow(x1,2) - Math.pow(x3,2) + Math.pow(y1,2) - Math.pow(y3,2) + Math.pow(d3,2) - Math.pow(d1,2));
		B2 = (float) (Math.pow(x2,2) - Math.pow(x3,2) + Math.pow(y2,2) - Math.pow(y3,2) + Math.pow(d3,2) - Math.pow(d2,2));
		// 矩阵求解公式中第一个矩阵
		float tempA[][] = { { A1, A2 }, { A3, A4 } };
		Matrix A = new Matrix(tempA);
		// 矩阵求解公式中第二个矩阵
		float tempB[][] = { { B1 }, { B2 } };
		Matrix B = new Matrix(tempB);
		// 声明求解的坐标(x,y)矩阵
		Matrix XY = new Matrix(1, 2);

		XY = A.reverse().multi(B);
		
//		System.out.println("XY:\n" + XY.toString());
		System.out.println("标签坐标(x,y)为:\t" + "(" + (int) XY.get(0, 0) + "," + (int) XY.get(1, 0) + ")");
		
		// 封装需要传给下一层的参数到result中
		Map<Object, String> result = new HashMap<Object, String>();
		result.put("ID", "5");
		result.put("t", "12");
		result.put("EPC", "12");
		result.put("RSSI", "34");
		result.put("x", "1111");
		result.put("y", "2700");
		
		return result;
	}

	
}
