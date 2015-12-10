package util;

/**
 * 作者：杜航 功能：Matrix类实现矩阵的基本操作
 */
public class Matrix {

	private float value[][]; // 存储矩阵元素的二维数组（选用float类型）

	/*
	 * 杜航 功能： 构造m行n列的空矩阵
	 */
	public Matrix(int m, int n) {
		this.value = new float[m][n];
	}

	/*
	 * 杜航 功能： 构造n行n列的空矩阵
	 */
	public Matrix(int n) {
		this(n, n);
	}

	/*
	 * 杜航 功能： 无参构造函数默认构造2行2列的空矩阵
	 */
	public Matrix() {
		this(2, 2);
	}

	/*
	 * 杜航 功能： 构造矩阵，由数组mat提供矩阵元素
	 */
	public Matrix(float mat[][]) {
		this(mat.length, mat[0].length);
		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[i].length; j++)
				this.value[i][j] = mat[i][j];
	}

	/*
	 * 杜航 功能： 获得矩阵第i行第j列的元素
	 */
	public float get(int i, int j) {
		return value[i][j];
	}

	/*
	 * 杜航 功能： 设置矩阵第i行第j列的元素
	 */
	public void set(int i, int j, int k) {
		value[i][j] = k;
	}

	/*
	 * 杜航 功能： 行主序遍历，访问矩阵全部元素
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
	 * 杜航 功能： this和b两个矩阵相加，改变当前矩阵
	 */
	public void add(Matrix b) {
		for (int i = 0; i < this.value.length; i++)
			for (int j = 0; j < this.value[i].length; j++)
				this.value[i][j] += b.value[i][j];
	}

	/*
	 * 杜航 功能：this和b两个矩阵相减，改变当前矩阵
	 */
	public void minus(Matrix b) {
		for (int i = 0; i < this.value.length; i++)
			for (int j = 0; j < this.value[i].length; j++)
				this.value[i][j] -= b.value[i][j];
	}

	/*
	 * 杜航 功能： 矩阵相乘：当前矩阵=当前矩阵*b
	 */
	public Matrix multi(Matrix b) {
		if (this.value[0].length != b.value.length) {
			System.out.println("这两个矩阵不能相乘");
			return null;
		}
		Matrix temp = new Matrix(value.length, b.value[0].length);
		for (int i = 0; i < value.length; i++)
			for (int j = 0; j < b.value[0].length; j++) {
				temp.value[i][j] = 0;
				for (int k = 0; k < value[0].length; k++)
					temp.value[i][j] += value[i][k] * b.value[k][j];
			}
		this.value = temp.value;

		return this;
	}

	/*
	 * 杜航 功能： 返回转置矩阵
	 */
	public Matrix transpose() {
		Matrix temp = new Matrix(value[0].length, value.length);
		for (int i = 0; i < this.value.length; i++)
			for (int j = 0; j < this.value[i].length; j++)
				temp.value[j][i] = this.value[i][j];
		return temp;
	}

	/*
	 * 杜航 功能： 返回伴随矩阵
	 */
	private Matrix ajoint() {
		int m = this.value.length;
		int n = this.value[0].length;

		Matrix temp = new Matrix(m, n);

		if (m > 2) {
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
		} else {
			temp.value[0][0] = this.value[1][1];
			temp.value[0][1] = -this.value[0][1];
			temp.value[1][0] = -this.value[1][0];
			temp.value[1][1] = this.value[0][0];

			return temp;
		}
	}

	/*
	 * 杜航 功能： 返回行列式的代数余子式
	 */
	private Matrix detCofactor(int i, int j) {

		int x = this.value.length; // 输入矩阵的行数列数
		int y = this.value[0].length;

		Matrix temp = new Matrix(x - 1, y - 1);// b为所求的余子式

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
	 * 杜航 功能：求解矩阵（行列式）的模值
	 */
	public float detValue() {
		float val = 0;
		/* 若为2*2的矩阵可直接求值并返回 */
		if (this.value[0].length == 2) {
			val = this.value[0][0] * this.value[1][1] - this.value[0][1]
					* this.value[1][0];
		} else {
			for (int i = 0; i < this.value[0].length; i++) {// 若矩阵不为2*2那么需求出矩阵第一行代数余子式的和
				Matrix temp = this.detCofactor(0, i);
				if (i % 2 == 0) { // 递归
					val = val + this.value[0][i] * temp.detValue();
				} else {
					val = val - this.value[0][i] * temp.detValue();
				}
			}
		}

		return val;
	}

	/*
	 * 杜航 功能：求n*n矩阵的逆矩阵
	 */
	public Matrix reverse() {
		int m = this.value.length;
		int n = this.value[0].length;

		Matrix temp = new Matrix(m, n);

		float value = this.detValue(); // 模值
		if (value != 0) {
			temp = this.ajoint(); // 伴随矩阵
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					temp.value[i][j] = temp.value[i][j] / value;
				}
			}
			return temp;
		} else {
			System.out.println("行列式的模值为0！不存在逆矩阵！！！");
			return null;
		}
	}

	/*
	 * 杜航 功能：main()测试矩阵的基本操作函数
	 */
	public static void main(String args[]) {

		float m1[][] = { { 1, 2, 3 }, { 4, 5, 6 } };
		Matrix a = new Matrix(m1);
		float m2[][] = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
		Matrix b = new Matrix(m2);
		System.out.print("Matrix a:\n" + a.toString());
		System.out.print("Matrix b:\n" + b.toString());
		a.multi(b);
		System.out.print("Matrix a*b:\n" + a.toString());

		System.out.println("a的转置矩阵：\n" + a.transpose().toString());

		float m3[][] = { { 1, 2, -3 }, { 0, 1, 2 }, { 0, 0, 1 } };
		Matrix c = new Matrix(m3);
		System.out.println("Value of det:\n" + c.detCofactor(3, 3).toString());

		float m4[][] = { { 1, -2 }, { -4, 3 } };
		Matrix d = new Matrix(m4);
		System.out.println("Value of det:\n" + d.detValue());

		float m5[][] = { { 1, 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 } };
		Matrix e = new Matrix(m5);
		System.out.println("伴随矩阵:\n" + e.ajoint().toString());

		float m51[][] = { { 1, 2 }, { 3, 4 } };
		Matrix e1 = new Matrix(m51);
		System.out.println("伴随矩阵:\n" + e1.ajoint().toString());

		float m6[][] = { { 1, 2, -3 }, { 0, 1, 2 }, { 0, 0, 1 } };
		Matrix f = new Matrix(m6);
		System.out.println("逆矩阵:\n" + f.reverse().toString());

		float m7[][] = { { 0, 1 }, { -1, 0 } };
		Matrix g = new Matrix(m7);
		System.out.println("逆矩阵:\n" + g.reverse().toString());

		// 验证 “矩阵伴随的转置等于矩阵转置的伴随”
		float m8[][] = { { 1, 2, -3 }, { 0, 1, 2 }, { 0, 0, 1 } };
		Matrix h = new Matrix(m8);
		System.out.println("矩阵伴随的转置:\n" + h.ajoint().transpose().toString());
		System.out.println("矩阵转置的伴随:\n" + h.transpose().ajoint().toString());

	}
}
