package util;

/**
 * ���ߣ��ź� 
 * ���ܣ�Matrix��ʵ�־���Ļ�������
 */
public class Matrix {

	private float value[][]; // �洢����Ԫ�صĶ�ά���飨ѡ��float���ͣ�

	/*
	 * �ź� 
	 * ���ܣ� ����m��n�еĿվ���
	 */
	public Matrix(int m, int n) {
		this.value = new float[m][n];
	}

	/*
	 * �ź� 
	 * ���ܣ� ����n��n�еĿվ���
	 */
	public Matrix(int n) {
		this(n, n);
	}

	/*
	 * �ź� 
	 * ���ܣ� �޲ι��캯��Ĭ�Ϲ���2��2�еĿվ���
	 */
	public Matrix() {
		this(2, 2);
	}

	/*
	 * �ź� 
	 * ���ܣ� �������������mat�ṩ����Ԫ��
	 */
	public Matrix(int mat[][]) {
		this(mat.length, mat[0].length);
		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[i].length; j++)
				this.value[i][j] = mat[i][j];
	}

	/*
	 * �ź� 
	 * ���ܣ� ��þ����i�е�j�е�Ԫ��
	 */
	public float get(int i, int j) {
		return value[i][j];
	}

	/*
	 * �ź� 
	 * ���ܣ� ���þ����i�е�j�е�Ԫ��
	 */
	public void set(int i, int j, int k) {
		value[i][j] = k;
	}

	/*
	 * �ź�
	 * ���ܣ� ��������������ʾ���ȫ��Ԫ��
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < value.length; i++) {
			for (int j = 0; j < value[i].length; j++)
				str += "  " + value[i][j];
			str += "\n";
		}
		return str;
	}

	/*
	 * �ź� 
	 * ���ܣ� this��b����������ӣ��ı䵱ǰ����
	 */
	public void add(Matrix b) {
		for (int i = 0; i < this.value.length; i++)
			for (int j = 0; j < this.value[i].length; j++)
				this.value[i][j] += b.value[i][j];
	}

	/*
	 * �ź� 
	 * ���ܣ�this��b��������������ı䵱ǰ����
	 */
	public void minus(Matrix b) {
		for (int i = 0; i < this.value.length; i++)
			for (int j = 0; j < this.value[i].length; j++)
				this.value[i][j] -= b.value[i][j];
	}

	/*
	 * �ź� 
	 * ���ܣ� ������ˣ���ǰ����=��ǰ����*b
	 */
	public void multi(Matrix b) {
		if (this.value[0].length != b.value.length) {
			System.out.println("���������������");
			return;
		}
		Matrix temp = new Matrix(value.length, b.value[0].length);
		for (int i = 0; i < value.length; i++)
			for (int j = 0; j < b.value[0].length; j++) {
				temp.value[i][j] = 0;
				for (int k = 0; k < value[0].length; k++)
					temp.value[i][j] += value[i][k] * b.value[k][j];
			}
		this.value = temp.value;
	}

	/*
	 * �ź� 
	 * ���ܣ� ����ת�þ���
	 */
	public Matrix transpose() {
		Matrix temp = new Matrix(value[0].length, value.length);
		for (int i = 0; i < this.value.length; i++)
			for (int j = 0; j < this.value[i].length; j++)
				temp.value[j][i] = this.value[i][j];
		return temp;
	}

	/*
	 * �ź� 
	 * ���ܣ� ���ذ������
	 */
	private Matrix ajoint() {
		int m = this.value.length;
		int n = this.value[0].length;

		Matrix temp = new Matrix(m, n);

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if ((i + j) % 2 == 0) {
					temp.value[i][j] = this.detCofactor(i, j).detValue();
				} else {
					temp.value[i][j] = -this.detCofactor(i, j).detValue();
				}
			}
		}
		return temp.transpose();
	}

	/*
	 * �ź� 
	 * ���ܣ� ��������ʽ�Ĵ�������ʽ
	 */
	private Matrix detCofactor(int i, int j) {

		int x = this.value.length; // ����������������
		int y = this.value[0].length;

		Matrix temp = new Matrix(this.value[0].length - 1,
				this.value.length - 1);// bΪ���������ʽ

		for (int m = 0; m < x - 1; m++) {
			if (m < i) {
				for (int n = 0; n < y - 1; n++) {
					if (n < j) {
						temp.value[m][n] = this.value[m][n];
					} else {
						temp.value[m][n] = this.value[m][n + 1];
					}
				}

			} else {
				for (int n = 0; n < y - 1; n++) {
					if (n < j) {
						temp.value[m][n] = this.value[m + 1][n];
					} else {
						temp.value[m][n] = this.value[m + 1][n + 1];
					}
				}
			}
		}
		return temp;

	}

	/*
	 * �ź� 
	 * ���ܣ�����������ʽ����ģֵ
	 */
	public float detValue() {
		float val = 0;
		/* ��Ϊ2*2�ľ����ֱ����ֵ������ */
		if (this.value[0].length == 2) {
			val = this.value[0][0] * this.value[1][1] - this.value[0][1]
					* this.value[1][0];
		} else {
			for (int i = 0; i < this.value[0].length; i++) {
				/* ������Ϊ2*2��ô����������һ�д�������ʽ�ĺ� */
				Matrix temp = this.detCofactor(0, i);
				if (i % 2 == 0) {
					/* �ݹ� */
					val = val + this.value[0][i] * temp.detValue();
				} else {
					val = val - this.value[0][i] * temp.detValue();
				}
			}
		}
		return val;
	}

	/*
	 * �ź� 
	 * ���ܣ���n*n����������
	 */
	public Matrix reverse() {
		int m = this.value.length;
		int n = this.value[0].length;

		Matrix temp = new Matrix(m, n);

		temp = this.ajoint();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				temp.value[i][j] = temp.value[i][j] / this.detValue();
			}
		}

		return temp;
	}

	/*
	 * �ź� 
	 * ���ܣ�main()���Ծ���Ļ�����������
	 */
	public static void main(String args[]) {
		// int m1[][] = { { 1, 2, 3 }, { 4, 5, 6 } };
		// Matrix a = new Matrix(m1);
		// int m2[][] = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
		// Matrix b = new Matrix(m2);
		// System.out.print("Matrix a:\n" + a.toString());
		// System.out.print("Matrix b:\n" + b.toString());
		// a.add(b);
		// a.multi(b);
		// System.out.print("Matrix a*b:\n" + a.toString());
		// System.out.println("a��ת�þ���\n"+a.transpose().toString());

		// int m3[][] = { { 1, 2, -3 }, { 0, 1, 2 }, { 0, 0, 1 } };
		// Matrix c = new Matrix(m3);
		// System.out.println("Value of det:\n" +
		// c.detCofactor(3,3).toString());

		// int m4[][] = { { 1, -2 }, { -4, 3} };
		// Matrix d = new Matrix(m4);
		// System.out.println("Value of det:\n" + d.detValue());

		// int m5[][] = { { 1, 1, 1 }, { 0, 1, 2 }, { 0, 1, -3 } };
		// Matrix e = new Matrix(m5);
		// System.out.println("�������:\n" + e.ajoint().toString());

		int m6[][] = { { 1, 2, -3 }, { 0, 1, 2 }, { 0, 0, 1 } };
		Matrix f = new Matrix(m6);
		System.out.println("�����:\n" + f.ajoint().toString());

	}
}
