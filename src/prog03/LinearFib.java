package prog03;

public class LinearFib implements Fib {

	@Override
	public double fib(int n) {
		double a = 0, b = 1, a2 = 0;

		if (n < 1) {
			return 0;
		} else if (n < 3) {
			return 1;
		} else {
			for (int i = 0; i < n; i++) {
				a = b;
				b = a2+b;
				a2 = a;
			}
			return a;
		}
	}
	
	@Override
	public double o(int n) {
		return n;
	}

}
