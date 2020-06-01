package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This program calculates and draws a fractal image of fractals derived from
 * Newton-Raphson iteration
 * @author Andrej
 *
 */
public class Newton {
	
	/**
	 * Method which starts the program
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
	
		List<Complex> list = new ArrayList<Complex>();
		getInput(list);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
//		list.add(new Complex(1, 0));
//		list.add(new Complex(-1, 0));
//		list.add(new Complex(0, 1));
//		list.add(new Complex(0, -1));
		
		Complex[] array = new Complex[list.size()];
		for(int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, array);
		
		MyFractalProducer producer = new MyFractalProducer(pol);
		
		FractalViewer.show(producer);
	}
	
	
	/**
	 * Job which one thread will do. Used for parallelization of the program
	 * @author Andrej
	 *
	 */
	public static class CalculateJob implements Callable<Void> {
		private final double CONVERGANCE_THRESHOLD = 1e-3;
		private final double ROOT_THRESHOLD = 0.002;
		
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial pol;
		ComplexPolynomial f;
		ComplexPolynomial fDer;
		
		public CalculateJob(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial pol) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.pol = pol;
			f = pol.toComplexPolynom();
			fDer = f.derive();
		}
		
		@Override
		public Void call() {
			
			calculate();
			
			return null;
		}
		
		/**
		 * This method calculates the fractals for every point on the screen
		 *  and places the indexes of the closest roots into an array
		 */
		private void calculate() {
			
			for(int x = 0; x < width; x++) {
				for(int y = yMin; y < yMax+1; y++) {
					if(cancel.get()) break;
					Complex zn = mapToComplex(x,y);
					Complex znold;
					int i = 0;
					do {
						znold = zn.copy();
						zn = zn.sub(f.apply(zn).divide(fDer.apply(zn)));
						i++;
					} while(zn.sub(znold).getModule() > CONVERGANCE_THRESHOLD && i < m);
					
					int index = pol.indexOfClosestRootFor(zn, ROOT_THRESHOLD);
					data[x + y*width] = (short) (index + 1);
				}
			}
			
			return;
		}
		
		/**
		 * This method calculates and returns the complex number for one point on
		 * the screen
		 * @param x x coordinate of the point
		 * @param y y coordinate of the point
		 * @return complex number mapped to that point
		 */
		private Complex mapToComplex(int x, int y) {
			double xPart = Math.abs((reMax - reMin) / (width));
			double yPart = Math.abs((imMax - imMin) / (height));
			
			return new Complex(reMin + xPart * x, imMax - yPart * y);
		}
	}
	
	/**
	 * Implementation of IFractalProducer for producing fractals derived from
	 * Newton-Raphson iteration
	 * @author Andrej
	 *
	 */
	public static class MyFractalProducer implements IFractalProducer {
		
		ComplexRootedPolynomial pol;
		
		public MyFractalProducer(ComplexRootedPolynomial pol) {
			this.pol = pol;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			int m = 16*16;
			short[] data = new short[width * height];
			final int lineNumber = 8 * Runtime.getRuntime().availableProcessors();
			int numberOfYinLine = height / lineNumber;

			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					(r) -> {
						Thread newThread = Executors.defaultThreadFactory().newThread(r);
						newThread.setDaemon(true);
						return newThread;
					}
					);
			List<Future<Void>> results = new ArrayList<>();
			
			for(int i = 0; i < lineNumber; i++) {
				int yMin = i*numberOfYinLine;
				int yMax = (i+1)*numberOfYinLine-1;
				if(i==lineNumber-1) {
					yMax = height-1;
				}
				CalculateJob job = new CalculateJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, pol);
				results.add(pool.submit(job));
			}
			for(Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			pool.shutdown();
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(pol.toComplexPolynom().order()+1), requestNo);
			
		}
		
	}
	
	/**
	 * This method gets the user input for the roots of the polynomial
	 * @param list list of the roots
	 */
	private static void getInput(List<Complex> list) {
		
		Scanner sc = new Scanner(System.in);
		int i = 1;
		
		while(true) {
			System.out.printf("Root %d> ", i);
			String input = sc.nextLine();
			
			if(input.equals("done")) break;
			
			Complex num = parseImput(input);
			list.add(num);
			
			i++;
		}
		
		sc.close();
		
	}
	
	/**
	 * This method parsed the input for the complex number
	 * @param input input of the user
	 * @return parsed complex number
	 */
	private static Complex parseImput(String input) {
		int index = 0;
		double real = 0;
		double imaginary = 0;
		
		char[] data = input.toCharArray();
		
		if(input.length() == 0) {
			System.out.println("No input given");
		}
		
		while(index < input.length()) {
			boolean isImaginary = false;
			
			StringBuilder sb = new StringBuilder();
	
			if(data[index] == '-' || data[index] == '+') {
				sb.append(data[index]);
				index++;
			}
			
			for(; index < data.length; index++) {
				
				if(data[index] == '-' || data[index] == '+') break;
				if(data[index] == ' ') continue;
				if(data[index] == 'i') {
					isImaginary = true;
					index++;
					if(index == data.length) {
						sb.append('1');
						break;
					}
				}
				
				sb.append(data[index]);
				
			}
			
			try {
				double number = Double.parseDouble(sb.toString());
				if(isImaginary) imaginary = number;
				else real = number;
			} catch(NumberFormatException e) {
				System.out.println("Could not parse the number");
			}
		}
		
		return new Complex(real, imaginary);
	}
}
