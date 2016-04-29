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

    public Plane(int materialIndex, Vector3D direction, double v) {
        super(Integer.toString(materialIndex));
        this.normal = direction;
        this.offset = v;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public double getOffset() {
        return offset;
    }

    @Override
    public Intersection getIntersection(Vector3D V, Vector3D P0) {
        double dotProd = V.dotProduct(getNormal());
        if (dotProd == 0) return null;

        double distance = (getOffset() - P0.dotProduct(getNormal())) / dotProd;
        if (distance <= 0) return null;

        Vector3D location = P0.add(V.scalarMultiply(distance));
        location = location.add(getNormal().scalarMultiply(1e-10));
        return new Intersection(getMaterialIndex(), distance, location,  getNormal());
    }
}
