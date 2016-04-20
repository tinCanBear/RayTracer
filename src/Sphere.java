import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Sphere extends Surface {
    private Vector3D center;
    private float radius;

    public Sphere(String[] params) {
        super(params[4]);
        center = new Vector3D(Double.parseDouble(params[0]),
                Double.parseDouble(params[1]),
                Double.parseDouble(params[2]));
        radius = Float.parseFloat(params[3]);
    }

    public Vector3D getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }
}
