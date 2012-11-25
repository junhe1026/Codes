import network.*;
import tools.*;

class SSQSim extends Sim {
  // Declared here so you can reset the station's performance measures...
	final QueueingNode station;
  final double runTime;

  //---------------------------------------------------------------------
  // Modify this class only if you're attempting level 3...
  //
  public class TruncatedCauchy extends DistributionSampler {
    // This code allows the sampler to be parameterised by some 'm', 
    // which could be the mean of the distribution or the truncation
    // parameter (presumably chosen to yield a specified mean).  You can 
    // use 'm' as you wish, or change the parameter(s) to suit your needs.

    double m ;

    public TruncatedCauchy(double m) {
      this.m = m;
    }

    // Implements abstract (virtual) method next()...
    public double next() {
      // Replace the call to Math.random() so that it generates a 
      // sample from a truncated Cauchy distribution. Hint: Java's tan function
      // is also in the Math class, viz. Math.tan().
         
         return Math.random();
    }
  }

  //---------------------------------------------------------------------
  // The default code here simulates an M/M/n queue with specified
  // arrival rate and (per-server) service rate. t is the simulation run
  // time. You only need to modify this if you are attempting level 3.
  // The simulation is set up and executed within the SSQSim constructor...
  //
	public SSQSim(double lambda, double mu, int n, double t) {
		Network.initialise();

    runTime = t;

    // The service time distribution sampler. Alternatively, you can
    // build a truncated Cauchy sampler using your sampler above via:
		//   Delay serviceTime = new Delay(new TruncatedCauchy(...));
		Delay serviceTime = new Delay(new Exp(mu));

		Source source = new Source("Source", new Exp(lambda));
		station = new QueueingNode("Station", serviceTime, n);
		Sink sink = new Sink("Sink");

		source.setLink(new Link(station));
		station.setLink(new Link(sink));

		// Warm-up time is 0 here, but you might want to change it...
    // resetMeasures() (see below) will be called after the warm-up period 
    // has expired.
		simulate(0);

		// The code below logs *all* measures at the end of the run...

		Network.logResults();

	}

  // This is invoked when the warm-up expires...
	public void resetMeasures() {
		station.resetMeasures();
	}

  // The simulation stop condition. You may want to change this (it 
  // should be longer than the warm-up time!)
	public boolean stop() {
		return now() > runTime;
	}
}

//---------------------------------------------------------------------
// This performs r replications of SSQSim and reports point and interval
// estimates for each node in the network (source, station and sink)...
//
public class Coursework extends ReplicatedSim {
  static double lambda, mu, t;
  static int n, r;

  public Coursework(int n, double a) {
    super(n, a);
  }

  public void runSimulation() {
    new SSQSim(lambda, mu, n, t);
  }

  // Command line parameters: arrival rate, service rate, no. of servers,
  // run time, number of replications.  The 
	public static void main(String args[]) {
    lambda = Double.parseDouble(args[0]);
    mu     = Double.parseDouble(args[1]);
    n      = Integer.parseInt(args[2]);
    t      = Double.parseDouble(args[3]);
    r      = Integer.parseInt(args[4]);
		new Coursework(r, 0.05);
	}
}
