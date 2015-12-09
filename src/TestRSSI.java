import java.util.HashMap;
import java.util.Map;

import util.Matrix;

public class TestRSSI {

	public final static float A = 47.0f;
	public final static float n = 4;

	public static void main(String[] args) {
		// num��ʾ�м������ߵ�RSSIֵ��Ϊ0
		int num = 0;

		// ��5������Ϊ��
		Map<Object, String> info1 = new HashMap<Object, String>();
		Map<Object, String> info2 = new HashMap<Object, String>();
		Map<Object, String> info3 = new HashMap<Object, String>();
		Map<Object, String> info4 = new HashMap<Object, String>();
		Map<Object, String> info5 = new HashMap<Object, String>();
		
		Map<String, Map<Object, String>> info = new HashMap<String, Map<Object, String>>();

		// �ֱ�����ߵĲ�����ֵ
		info.put("1", info1);
		info.put("2", info2);
		info.put("3", info3);
		info.put("4", info4);
		info.put("5", info5);

		info1.put("ID", "1");
		info1.put("t", "12");
		info1.put("EPC", "12");
		info1.put("RSSI", "10");
		info1.put("x", "0");
		info1.put("y", "600");

		info2.put("ID", "2");
		info2.put("t", "12");
		info2.put("EPC", "12");
		info2.put("RSSI", "10");
		info2.put("x", "0");
		info2.put("y", "2700");

		info3.put("ID", "3");
		info3.put("t", "12");
		info3.put("EPC", "12");
		info3.put("RSSI", "0");
		info3.put("x", "7000");
		info3.put("y", "600");

		info4.put("ID", "4");
		info4.put("t", "12");
		info4.put("EPC", "12");
		info4.put("RSSI", "0");
		info4.put("x", "7000");
		info4.put("y", "4800");

		info5.put("ID", "5");
		info5.put("t", "12");
		info5.put("EPC", "12");
		info5.put("RSSI", "1000");
		info5.put("x", "7000");
		info5.put("y", "2700");

		// �õ�RSSIֵ���ַ�������
		String[] Rssi = new String[] { info1.get("RSSI"), info2.get("RSSI"),
				info3.get("RSSI"), info4.get("RSSI"), info5.get("RSSI") };

		String[] jud = new String[Rssi.length];

		int i = 0;
		for (String s : Rssi) {
			i++;
			if (!s.equals("0")) {
				jud[num] = String.valueOf(i);// �õ�RSSI��Ϊ0������ŵ�����
				num++;
			}
		}

		// ���ַ������鰴���ַ��������ֵĴ�С����
		int[] ia = new int[Rssi.length];
		for (int j = 0; j < Rssi.length; j++) {
			ia[j] = Integer.parseInt(Rssi[j]);
		}
		sort(ia);

		String[] Rssi1 = new String[ia.length];
		for (int j = 0; j < ia.length; j++) {
			Rssi1[j] = String.valueOf(ia[j]);
		}

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
			System.out.println("����δ��Ӧ���κ�����");
			break;
		case 1:
			System.out.println("��һ�����߲ɼ���������");
			singlePoint(info.get(jud1[0]));
			break;
		case 2:
			System.out.println("���������߲ɼ���������");
			twoPoints(info.get(jud1[0]), info.get((jud1[1])));
			break;
		default:
			System.out.println("�������������߲ɼ���������");
			System.out.println(info.get(jud1[0]) + "\n" + info.get(jud1[1])
					+ "\n" + info.get(jud1[2]));
			threePoints(info.get(jud1[0]), info.get(jud1[1]), info.get(jud1[2]));
		}
	}
	
	/*
	 * ��·��
	 * ���ܣ�������
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
	 * ��·��
	 * ���ܣ�ʵ�ֵ��㶨λ 
	 * ���ղ�����map(������t EPC ID RSSI x y )
	 * ���ز�����
	 */
	private static void singlePoint(Map map) {
		System.out.println("EPCΪ��"+ map.get("EPC")+"�ĺ�����Ϊ"+map.get("x")+
				"��������Ϊ"+ map.get("y")+",��Ӧ����ʱ��Ϊ"+ map.get("t")); 
	}

	/*
	 * ��·��
	 * ���ܣ�ʵ�����㶨λ 
	 * ���ղ�����map1��map2(������t EPC ID RSSI x y )
	 * ���ز�����
	 */
	private static void twoPoints(Map map1,Map map2) {
		if(Integer.parseInt( (String) map1.get("RSSI"))>=2*Integer.parseInt((String) map2.get("RSSI"))){
			System.out.println("EPCΪ��"+ map1.get("EPC")+"�ĺ�����Ϊ"+map1.get("x")+
					"��������Ϊ"+ map1.get("y")+",��Ӧ����ʱ��Ϊ"+ map1.get("t"));
		}
		else if(2*Integer.parseInt((String) map1.get("RSSI"))<=Integer.parseInt((String) map2.get("RSSI"))){
		System.out.println("EPCΪ��"+ map2.get("EPC")+"�ĺ�����Ϊ"+map2.get("x")+
				"��������Ϊ"+ map2.get("y")+",��Ӧ����ʱ��Ϊ"+ map2.get("t")); 
		}
		else{ 
			System.out.println("EPCΪ��"+ map2.get("EPC")+
			"�ĺ�����Ϊ"+(Integer.parseInt((String) map2.get("x"))+Integer.parseInt((String) map1.get("x")))/2+
			"��������Ϊ"+(Integer.parseInt((String) map2.get("y"))+Integer.parseInt((String) map1.get("y")))/2+
			",��Ӧ����ʱ��Ϊ"+ map2.get("t")); 
		}
	}
	
	/*
	 * �ź�
	 * ���ܣ����ù�ʽ RSSI = -(A + 10*n*lg(d)) ��� d
	 * ���ղ�����RSSI(������t EPC ID RSSI x y )
	 * ���ز�����d
	 */
	public static float rssiFormula(float RSSI) {

		float d = (float) Math.exp(-(RSSI + A) / (10 * n));
		return d;
	}
	
	/*
	 * �ź�
	 * ���ܣ�ʵ��RSSI���㶨λ 
	 * ���ղ�����receive1��receive2��receive3(������t EPC ID RSSI x y )
	 * ���ز�����result
	 */
	public static Map threePoints(Map receive1, Map receive2, Map receive3) {
		
		Map<Object, Object> result = new HashMap<Object, Object>();//���ز�������result��
		
		float RSSI1, RSSI2, RSSI3, x1, x2, x3, y1, y2, y3, d1, d2, d3, A1, A2, A3, A4, B1, B2;
		
		RSSI1 = Float.parseFloat(receive1.get("RSSI").toString());
		RSSI2 = Float.parseFloat(receive2.get("RSSI").toString());
		RSSI3 = Float.parseFloat(receive3.get("RSSI").toString());
		
		x1 = Float.parseFloat(receive1.get("x").toString());
		x2 = Float.parseFloat(receive2.get("x").toString());
		x3 = Float.parseFloat(receive3.get("x").toString());
		
		y1 = Float.parseFloat(receive1.get("y").toString());
		y2 = Float.parseFloat(receive2.get("y").toString());
		y3 = Float.parseFloat(receive3.get("y").toString());
		
		d1 = rssiFormula(RSSI1);
		d2 = rssiFormula(RSSI2);
		d3 = rssiFormula(RSSI3);
		
		//��װ�����Ԫ��
		A1 = 2 * (x1 - x3);
		A2 = 2 * (y1 - y3);
		A3 = 2 * (x2 - x3);
		A4 = 2 * (y2 - y3);
		
		B1 = (float) (Math.pow(x1,2) - Math.pow(x3,2) + Math.pow(y1,2) - Math.pow(y3,2) + Math.pow(d3,2) - Math.pow(d1,2));
		B2 = (float) (Math.pow(x2,2) - Math.pow(x3,2) + Math.pow(y2,2) - Math.pow(y3,2) + Math.pow(d3,2) - Math.pow(d2,2));
		
		float tempA[][] = { { A1, A2 }, { A3, A4 } };
		Matrix A = new Matrix(tempA);
		
		float tempB[][] = { { B1 }, { B2 } };
		Matrix B = new Matrix(tempB);
		
		System.out.println("A:\n" + A.toString());
		System.out.println("B:\n" + B.toString());
		
		Matrix XY = new Matrix(1, 2);//���������x,y����
		
		System.out.println("A�������:\n" + A.reverse().toString());

		XY = A.reverse().multi(B);
		
		
		System.out.println("XY:\n" + XY.toString());
		
		
		return result;
	}

	
}
