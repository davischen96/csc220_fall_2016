package prog03;

import prog02.GUI;
import prog02.UserInterface;


/**
 *
 * @author dc
 */
public class Main {
	/** Use this variable to store the result of each call to fib. */
	public static double fibn;

	/**
	 * Determine the time in microseconds it takes to calculate the n'th
	 * Fibonacci number.
	 * 
	 * @param fib
	 *            an object that implements the Fib interface
	 * @param n
	 *            the index of the Fibonacci number to calculate
	 * @return the time for the call to fib(n) in microseconds
	 */
	public static double time(Fib fib, int n) {
		// Get the current time in nanoseconds.
		long start = System.nanoTime();

		// Calculate the n'th Fibonacci number.
		// Store the result in fibn.
		fibn = fib.fib(n);

		// Get the current time in nanoseconds. Using long, like start.
		long end = System.nanoTime();

		// Uncomment the following for a quick test.
		// System.out.println("start " + start + " end " + end);

		// Return the difference between the end time and the
		// start time divided by 1000.0 to convert to microseconds.
		double time = ((end - start) / 1000.0);
		return time;

	}

	/**
	 * Determine the average time in microseconds it takes to calculate the n'th
	 * Fibonacci number.
	 * 
	 * @param fib
	 *            an object that implements the Fib interface
	 * @param n
	 *            the index of the Fibonacci number to calculate
	 * @param ncalls
	 *            the number of calls to average over
	 * @return the average time per call
	 */
	public static double averageTime(Fib fib, int n, long ncalls) {
		double totalTime = 0;

		// Add up the total call time for ncalls calls to time (above).
		// Use long instead int in your declaration of the counter variable.

		for (long c = 0; c < ncalls; c++) {
			totalTime += time(fib, n);
		}

		// Return the average time.
		return totalTime / ncalls;

	}

	/**
	 * Determine the time in microseconds it takes to to calculate the n'th
	 * Fibonacci number ACCURATE TO THREE SIGNIFICANT FIGURES.
	 * 
	 * @param fib
	 *            an object that implements the Fib interface
	 * @param n
	 *            the index of the Fibonacci number to calculate
	 * @return the time it it takes to compute the n'th Fibonacci number
	 */
	public static double accurateTime(Fib fib, int n) {
		// Get the time using the time method above.

		double time = time(fib, n);

		// Since it is not very accurate, it might be zero. If so set it to 0.1

		if (time == 0) {
			time = 0.1;
		}

		// Calculate the number of calls to average over that will give
		// three figures of accuracy. You will need to "cast" it to int
		// by putting (int) in front of the formula.
		int ncalls = (int) (1000000 * Math.pow(time, -2));

		// If ncalls is 0, increase it to 1.

		if (ncalls == 0) {
			ncalls = 1;
		}

		// Get the accurate time using averageTime.

		return averageTime(fib, n, ncalls);
	}

	static void labExperiments() {
		// Create (Exponential time) Fib object and test it.
		Fib efib = new ExponentialFib();
		// Fib efib = new ConstantFib();
		System.out.println(efib);
		for (int i = 0; i < 11; i++) {
			System.out.println(i + " " + efib.fib(i));
		}
		// Determine running time for n1 = 20 and print it out.
		int n1 = 20;
		double time1 = averageTime(efib, n1, 1000);
		System.out.println("n1 " + n1 + " time1 " + time1);

		double ncalls = (1000000 / Math.pow(time1, 2));
		System.out.println("ncalls " + ncalls);

		// Calculate constant: time = constant times O(n).
		double c = time1 / efib.o(n1);
		System.out.println("c " + c);

		// Estimate running time for n2=30.
		int n2 = 30;
		double time2est = c * efib.o(n2);
		System.out.println("n2 " + n2 + " estimated time " + time2est);

		// Calculate actual running time for n2=30.
		double time2 = averageTime(efib, n2, 100);
		System.out.println("n2 " + n2 + " actual time " + time2);

		double ncalls2 = (1000000 * Math.pow(time2, -2));
		System.out.println("ncalls " + ncalls2);

		// double time20 = c * efib.o(n2);
		// System.out.println("n2 " + n2 + " time 2 " + time20);

		// double time21 = c * efib.o(n2);
		// System.out.println("n2 " + n2 + " time 2 " + time21);

		int n3 = 100;
		double time3est = c * efib.o(n3);
		System.out.println("n3 " + n3 + " estimated time " + time3est);
		double years = time3est / (1000000) / (31536000);
		System.out.println("which is " + years + " years");
	}

	private static UserInterface ui = new GUI();

	static void hwExperiments(Fib fib) {
		double c = -1; // -1 indicates that no experiments have been run yet.

		while (true) {
			
			String n = null;
			  int nTwo = 0;
			  double constant = 0, time, est;
			  final String YN[] = {"Yes!", "No!"};
			  while (true) {
				  try {
					  		// Ask the user for an integer n.
					  n = ui.getInfo("Enter n");
					  nTwo = Integer.parseInt(n);
							// Return if the user cancels. 
					  if (nTwo < 0) throw new NumberFormatException("n cannot be less than 0");

							// If this not the first experiment, estimate the running time for
							// that n using the value of the constant from the last time.
					  if (constant > 0) {
						  double fibO = fib.o(nTwo);
						  if (fibO <= 0) {
							  fibO = 1;
						  }
						  est = constant * fibO;
						  ui.sendMessage("Estimated time for fib(" + nTwo + ") is " + est + " ms.");
							// ADD LATER: If it is greater than an hour, ask the user for
							// confirmation before running the experiment.
							// If not, the "continue;" statement will skip the experiment.
						  boolean tooLong = est > 3600000;
						  boolean goodTime = true;
							// Display the estimate.
						  if (tooLong) {
							  ui.sendMessage("Estimated time is more than an hour. \n I am going to ask if you want to do it.");
							  goodTime = ui.getCommand(YN) == 0;
					 }
						  if (goodTime) {
							  time = accurateTime(fib, nTwo);
							  ui.sendMessage("fib(" + nTwo + ") =  " + fibn  + " in " + time + " ms.");
							  fibO = fib.o(nTwo);
							  if (fibO <= 0) {
								  fibO = 1;
							  }
							  constant = time/fibO;
						  }
							// Now, calculate the (accurate) running time for that n.
							// Calculate the constant c.
							// Display fib(n) and the actual running time.
					  } else {
						  time = accurateTime(fib, nTwo);
						  double fibO = fib.o(nTwo);
						  if (fibO <= 0) {
							  fibO = 1;
						  }
						  constant = time/fibO;
						  
						  ui.sendMessage("fib(" + nTwo + ") = " + fibn + " in " + time + " ms.");
					  }
						// Deal with bad inputs: not positive, not an integer.
				  } catch (NumberFormatException error) {
					  System.out.println(error.getMessage());
					  if (n == null) {
						  return;
					  }
					  if (nTwo < 0) {
						  ui.sendMessage(n + " is not a positive integer. Try again.");
					  	  continue;
					  }
					  if (n != null) {
						  ui.sendMessage("You can't find fib(n) for \"" + n + "\"!");
						  continue;
					  }

				  }
			  }

				// Ask the user before doing another experiment. Otherwise return.


		}
	}

	static void hwExperiments() {
		// In a loop, ask the user which implementation of Fib or exit,
		// and call hwExperiments (above) with a new Fib of that type.
		 Fib[] fibs = {new ExponentialFib(), new LinearFib(), new LogFib(), new ConstantFib()};
		  String[] commands = { "ExponentialFib", "LinearFib", "LogFib", "ConstantFib", "Exit" };
		  
		  while (true) {
			  int c = ui.getCommand(commands);
			  if (c == 4) {
				  return;
			  } else {
				  hwExperiments(fibs[c]);
			  }
		  }
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		labExperiments();
		hwExperiments();

	}
}
