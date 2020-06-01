package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program for that shows an image of 3D space by casting rays.
 * @author Andrej
 *
 */
public class RayCaster {
	 
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
		
	private static IRayTracerProducer getIRayTracerProducer() {
		
		
		return new IRayTracerProducer() {
			@SuppressWarnings("unused")
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D vuvNorm = viewUp.normalize();
				Point3D dist = view.sub(eye).normalize();
				
				Point3D zAxis = dist.scalarMultiply(-1);
				Point3D yAxis = vuvNorm.sub(dist.scalarMultiply(dist.scalarProduct(vuvNorm))).normalize();
				Point3D xAxis = dist.vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						if(cancel.get()) break;
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x/(width - 1))).sub(yAxis.scalarMultiply(vertical*y/(height-1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb, eye);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
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
