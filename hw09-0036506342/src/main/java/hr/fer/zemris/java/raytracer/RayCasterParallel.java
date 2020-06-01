package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program for that shows an image of 3D space by casting rays.
 * This program uses parallelization
 * @author Andrej
 *
 */
public class RayCasterParallel {
	
	/**
	 * Method which starts the program
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
		}
	
	/**
	 * This class represents a job which one thread will have to finish.
	 * @author Andrej
	 *
	 */
	public static class CalculateJob extends RecursiveAction{
		private static final long serialVersionUID = 1L;
		
		Scene scene;
		Point3D eye;
		Point3D view;
		Point3D viewUp;
		double horizontal;
		double vertical;
		int width;
		int height;
		AtomicBoolean cancel;
		int yMin;
		int yMax;
		short[] red;
		short[] green;
		short[] blue;
		
		private short treshold;
		
		

		public CalculateJob(Scene scene, Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
				int height, AtomicBoolean cancel, int yMin, int yMax, short[] red, short[] green, short[] blue) {
			super();
			this.scene = scene;
			this.eye = eye;
			this.view = view;
			this.viewUp = viewUp;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.cancel = cancel;
			this.yMin = yMin;
			this.yMax = yMax;
			this.red = red;
			this.green = green;
			this.blue = blue;
			
			treshold = (short) (width/(Runtime.getRuntime().availableProcessors()*8));
		}
		
		/**
		 * This method does all the important calculations in the job
		 */
		@SuppressWarnings("unused")
		protected void computeDirect() {
			
			Point3D vuvNorm = viewUp.normalize();
			Point3D dist = view.sub(eye).normalize();
			
			Point3D zAxis = dist.scalarMultiply(-1);
			Point3D yAxis = vuvNorm.sub(dist.scalarMultiply(dist.scalarProduct(vuvNorm))).normalize();
			Point3D xAxis = dist.vectorProduct(yAxis).normalize();
			
			Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
			
			short[] rgb = new short[3];
			for(int y = yMin; y < yMax + 1; y++) {
				for(int x = 0; x < width; x++) {
					if(cancel.get()) break;
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x/(width - 1))).sub(yAxis.scalarMultiply(vertical*y/(height-1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
					tracer(scene, ray, rgb, eye);
					
					red[x+y*width] = rgb[0] > 255 ? 255 : rgb[0];
					green[x+y*width] = rgb[1] > 255 ? 255 : rgb[1];
					blue[x+y*width] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
			
		}
		
		/**
		 * This method determines and splits the job into smaller job 
		 */
		public void compute() {
			if(yMax-yMin+1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new CalculateJob(scene, eye, view, viewUp, horizontal, vertical, width, height, cancel, yMin, yMin+(yMax-yMin)/2, red, green, blue),
					new CalculateJob(scene, eye, view, viewUp, horizontal, vertical, width, height, cancel, yMin+(yMax-yMin)/2+1, yMax, red, green, blue)
			);
		}
		
		
		
	}
		
	private static IRayTracerProducer getIRayTracerProducer() {
		
		
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculateJob(scene, eye, view, viewUp, horizontal, vertical, width, height, cancel, 0, height-1, red, green, blue));
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
			
			
			
		};
	}
	
	/**
	 * This method traces a specific ray and calculates the appropriate color intensites 
	 * for the pixel on the screen
	 * @param scene scene you wish to show the image of 
	 * @param ray ray you wish to trace
	 * @param rgb rgb values of the pixel
	 * @param eye position of the eye
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if(closest==null) {
		return;
		}
		
		determineColorFor(scene, closest, rgb, eye);
	}
	
	/**
	 * This method determines the color for the specific pixel through which the casted
	 * ray has an intersection with an object in the scene
	 * @param scene
	 * @param intersection
	 * @param rgb
	 * @param eye
	 */
	private static void determineColorFor(Scene scene, RayIntersection intersection, short[] rgb, Point3D eye) {
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		for(LightSource source : scene.getLights()) {
			Ray ray = Ray.fromPoints(source.getPoint(), intersection.getPoint());
			RayIntersection closest = findClosestIntersection(scene, ray);
			if(closest == null) continue;
			if(Math.abs(closest.getDistance()-intersection.getPoint().sub(source.getPoint()).norm()) > 1e-6) {
				continue;
			}
			else {
				Point3D l = source.getPoint().sub(closest.getPoint()).normalize();
				
				double fact = Math.max(l.scalarProduct(closest.getNormal()), 0.0);
				rgb[0] += closest.getKdr() * source.getR() * fact;
				rgb[1] += closest.getKdg() * source.getG() * fact;
				rgb[2] += closest.getKdb() * source.getB() * fact;
				
				Point3D r = l.scalarMultiply(-1).sub(closest.getNormal().scalarMultiply(l.scalarMultiply(-1).scalarProduct(closest.getNormal())*2));
				Point3D v = eye.sub(closest.getPoint()).normalize();
				fact = Math.pow(r.scalarProduct(v), closest.getKrn());
				rgb[0] += closest.getKrr() * source.getR() * fact;
				rgb[1] += closest.getKrg() * source.getG() * fact;
				rgb[2] += closest.getKrb() * source.getB() * fact;
			
			}
			
		}
		
	}
	
	/**
	 * THis method finds the closes intersection of the ray and an object in the scene
	 * @param scene scene you wish to find intersection in
	 * @param ray ray for which you wish to find the intersection for
	 * @return RayIntersection if it exists, false otherwise
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		if (scene.getObjects().isEmpty()) return null;
		
		RayIntersection closestIntersection = scene.getObjects().get(0).findClosestRayIntersection(ray);
		
		for(GraphicalObject object : scene.getObjects()) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			
			if(intersection == null) continue;
			
			if(closestIntersection == null) closestIntersection = intersection;
			
			if(intersection.getDistance() < closestIntersection.getDistance()) closestIntersection = intersection;
		}
		
		return closestIntersection;
		
	}

	
}
