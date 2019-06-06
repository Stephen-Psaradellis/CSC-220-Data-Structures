package prog03;

public class LinearFib implements Fib{

	@Override
	public double fib(int n) {
		int a = 0;
		int b = 1;
		int c = 0;
		for(int i = 0; i < n; i++){
			c = b;
			b+=a;
			a=c;
		}
		return b;
	}

	@Override
	public double o(int n) {
		return n;
	}

}
