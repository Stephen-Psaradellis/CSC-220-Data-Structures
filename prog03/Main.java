package prog03;
import prog02.GUI;
import prog02.UserInterface;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibN;

  /** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n) in microseconds
  */
  public static double time (Fib fib, int n) {
    // Get the starting time in nanoseconds.
    long start = System.nanoTime();
    // Calculate the n'th Fibonacci number.
    // Store the result in fibN.
    fibN = fib.fib(n);

    // Get the ending time in nanoseconds.  Use long, as for start.

    long end = System.nanoTime();
    // Uncomment the following for a quick test.
     //System.out.println("start " + start + " end " + end);
    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
     return (end - start) / 1000.0;

  }

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, long ncalls) {
    double totalTime = 0;
    // Add up the total call time for calling time (above) ncalls times.
    // Use long instead int in your declaration of the counter variable.
	for(long i=0; i < ncalls; i++){
    	totalTime+=time(fib, n);
    }
    // Return the average time.
    return totalTime/ncalls;
  }
  /** Determine the time in microseconds it takes to to calculate
      the n'th Fibonacci number accurate to three significant figures.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time using the time method above.
	  double time = time(fib, n);
    // Since it is not very accurate, it might be zero.  If so set it to 0.1
	  if(time == 0)
		  time = .01;
    // Calculate the number of calls to average over that will give
    // three figures of accuracy.  You will need to "cast" it to long
    // by putting   (long)   in front of the formula.
	  long ncalls = (long)((1000 * 1000)/(time * time));
    // If ncalls is 0, increase it to 1.
	  if(ncalls == 0)
		  ncalls++;
    // Get the accurate time using averageTime.
	  return averageTime(fib,n,ncalls);
  }
  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ExponentialFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(efib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    long ncalls = (long)((1000 * 1000)/ (time1 * time1));
    time1 = averageTime(efib, n1, ncalls);
    System.out.println("ncalls: " + ncalls);
    System.out.println("n1: " + n1 + " time1: " + time1);
    System.out.println("n1 accurate time: " + accurateTime(efib, n1));
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.o(n1);
    System.out.println("c " + c);
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.o(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    System.out.println("n2 " + n2 + " actual time " + time2);
    System.out.println("n2 accurate time: " + accurateTime(efib, n2));
    ncalls = (long)((1000 * 1000)/ (time2 * time2));
    if (ncalls==0)
    	ncalls=1;
    System.out.println("ncalls: " + ncalls);
    int n3 = 100;
    double time3est = c * efib.o(n3);
    System.out.println("time3est: " + time3est);
  }
  private static UserInterface ui = new GUI();

  static void hwExperiments (Fib fib) {
    double c = -1;  // -1 indicates that no experiments have been run yet.
    int command = 1;
    String[] commands = {
			"Yes",
			"No"};
    while (true) {
      // Ask the user for an integer n.
      // Return if the user cancels.
      // Deal with bad inputs:  not positive, not an integer.
    	int n;
    	double estimatedTime = 0;
    	String k = ui.getInfo("Please enter a positive integer");
    	if(k==null)
    		return;
    	n=Integer.valueOf(k);
    	if(n<0 || n != (int)n) {
    		ui.sendMessage("Please make sure your entry is a positive integer");
    		n = Integer.valueOf(ui.getInfo("Please enter a positive integer"));
    		}
    	if(c!=-1.0) {
    		estimatedTime = c * fib.o(n);
    		ui.sendMessage("Estimated time for f(" + n + ") is " + estimatedTime + " microseconds");
    		if (estimatedTime>3.6*Math.pow(10, 9)){
    			ui.sendMessage("The estimated time is over 1 hour. Would you like to continue?");
    			command = ui.getCommand(commands);
    			if(command==1) continue;
    		}
    	}	
    		double accurateTimeSet = accurateTime(fib,n);
    		c = accurateTimeSet/fib.o(n);
    		ui.sendMessage("f(" + n + ") is " + fib.fib(n) + " in " + accurateTimeSet + " microseconds");
    		}
    	}
      // If this not the first experiment, estimate the running time for
      // that n using the value of the constant from the last time.
    		
      // Display the estimate.
      
      // ADD LATER: If it is greater than an hour, ask the user for
      // confirmation before running the experiment.
      // If not, the "continue;" statement will skip the experiment.
      
      // Now, calculate the (accurate) running time for that n.
      // Calculate the constant c.
      
      // Display fib(n) and the actual running time.
      
      // Ask the user before doing another experiment.  Otherwise return.
  static void hwExperiments () {
    // In a loop, ask the user which implementation of Fib or exit,
    // and call hwExperiments (above) with a new Fib of that type.
	  String[] commands = {
				"Constant Fib",
				"Log Fib",
				"Linear Fib",
				"Exponential Fib",
				"Exit"};
	  Fib fib;
	  while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				fib = new ConstantFib();
				hwExperiments(fib);
				break;
			case 1:
				fib = new LogFib();
				hwExperiments(fib);
				break;
			case 2:
				fib = new LinearFib();
				hwExperiments(fib);
				break;
			case 3:
				fib = new ExponentialFib();
				hwExperiments(fib);
				break;
			case 4:
				return;
			}
		}
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    //labExperiments();
    hwExperiments();
  }
}
