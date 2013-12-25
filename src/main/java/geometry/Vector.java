package geometry;

public interface Vector {
	double getX();

	double getY();

	double getLength();

	double getAngle();

	Vector times(double factor);

	Vector rotated(double angle);

	Vector plus(Vector vec);

	Vector minus(Vector vec);

	double dotProduct(Vector vec);
}
