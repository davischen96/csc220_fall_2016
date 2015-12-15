package prog03;

public class LinearFib implements Fib {

	@Override
	public double fib(int n) {
		double a = 0, b = 1;
		for (int i = 0; i < n; i++) {
			double a2 = b;
			b = a + b;
			a = a2;

		}
		return a;
	}

	@Override
	public double o(int n) {
		return n;
	}

}
