import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

class Cylinder extends Surface {
    private Vector3D center;
    private double length;
    private double radius;
    private Vector3D rotation;

    Cylinder(String[] params) {
        super(params[8]);
        center = new Vector3D(Double.parseDouble(params[0]),
                Double.parseDouble(params[1]),
                Double.parseDouble(params[2]));
        rotation = new Vector3D(Double.parseDouble(params[5]),
                Double.parseDouble(params[6]),
                Double.parseDouble(params[7]));
        length = Double.parseDouble(params[3]);
        radius = Double.parseDouble(params[4]);
    }

    public Vector3D getCenter() {
        return center;
    }

    public double getLength() {
        return length;
    }

    public double getRadius() {
        return radius;
    }

    public Vector3D getRotation() {
        return rotation;
    }

    @Override
    public Intersection getIntersection(Vector3D V, Vector3D P0) {
        return null;
    }
}
