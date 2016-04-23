import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

class Plane extends Surface {
    private Vector3D normal;
    private double offset;

    Plane(String[] params) {
        super(params[4]);
        normal = new Vector3D(Double.parseDouble(params[0]),
                Double.parseDouble(params[1]),
                Double.parseDouble(params[2]));
        offset = Double.parseDouble(params[3]);
    }

    public Vector3D getNormal() {
        return normal;
    }

    public double getOffset() {
        return offset;
    }

    @Override
    public Intersection getIntersection(Vector3D V, Vector3D P0) {
        return null;
    }
}
