package geometry;

public class Geometries {
	public static final Vector zero = vector(0, 0);
	public static final Vector unitX = vector(1, 0);
	public static final Vector unitY = vector(0, 1);

	private Geometries() {
	}

	public static Vector vector(double x, double y) {
		return new VectorImpl(x, y);
	}

	public static Vector vectorFromAngle(double angle) {
		return vector(Math.cos(angle), Math.sin(angle));
	}

	public static Vector vectorFromAngle(double angle, double length) {
		return vector(Math.cos(angle) * length, Math.sin(angle) * length);
	}
}
