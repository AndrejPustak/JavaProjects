package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere in 3D space.
 * @author Andrej
 *
 */
public class Sphere extends GraphicalObject{
	
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	
	
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		double a = 1.0;
		double b = ray.direction.scalarMultiply(2).scalarProduct(ray.start.sub(center));
		double c = ray.start.sub(center).scalarProduct(ray.start.sub(center)) - radius*radius;
		
		double determinant = b*b - 4*a*c;
		
		if(determinant < 0) return null;
		
		double l1 = (-b + Math.sqrt(determinant)) / (2*a);
		double l2 = (-b - Math.sqrt(determinant)) / (2*a);
		
		Point3D point1 = ray.start.add(ray.direction.scalarMultiply(l1));
		Point3D point2 = ray.start.add(ray.direction.scalarMultiply(l2));
		
		if(l1 <= l2 && l1 > 0) {
			return new SphereRayIntersection(point1, point1.sub(ray.start).norm(), true);
		}
		else if(l2 < l1 && l2 > 0) return new SphereRayIntersection(point2, point2.sub(ray.start).norm(), true);
		else return null;
		
	}
	
	/**
	 * This method represents the intersection of the sphere and a ray
	 * @author Andrej
	 *
	 */
	public class SphereRayIntersection extends RayIntersection{
		
		/**
		 * COnstructor for the SphereRayIntersection
		 * @param point intersection point
		 * @param distance distance from the point to the source of the ray
		 * @param outer true if the point is an outer point false otherwise
		 */
		public SphereRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}
		
		@Override
		public Point3D getNormal() {
			return this.getPoint().sub(center).normalize();
		}
		
		@Override
		public double getKdr() {
			return kdr;
		}
		
		@Override
		public double getKdg() {
			return kdg;
		}
		
		@Override
		public double getKdb() {
			return kdb;
		}
		
		@Override
		public double getKrr() {
			return krr;
		}
		
		@Override
		public double getKrg() {
			return krg;
		}
		
		@Override
		public double getKrb() {
			return krb;
		}
		
		@Override
		public double getKrn() {
			return krn;
		}
		
	}
	
}
