package prog03;

public class ConstantFib extends PowerFib {

	@Override
	public double o(int n) {
		return 1.0;
	}

	@Override
	protected double pow(double x, int n) {
		return Math.pow(x, n);
	}

}
