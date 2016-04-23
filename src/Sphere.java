import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

class Sphere extends Surface {
    private Vector3D center;
    private float radius;

    Sphere(String[] params) {
        super(params[4]);
        center = new Vector3D(Double.parseDouble(params[0]),
                Double.parseDouble(params[1]),
                Double.parseDouble(params[2]));
        radius = Float.parseFloat(params[3]);
    }

    private Vector3D getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public Intersection getIntersection(Vector3D V, Vector3D P0) {
        Vector3D camToCenter = getCenter().subtract(P0);
        double dotProd = camToCenter.dotProduct(V);

        if (dotProd < 0) { // opposite direction; surface behind cam
            return null;
        }

        double sizeTimesSin = camToCenter.getNormSq() - pow(dotProd, 2);

        if (sizeTimesSin > pow(radius, 2)) {
            return null;
        }

        double d = sqrt(pow(radius, 2) - sizeTimesSin);
        double dist = dotProd - d;

        if (dist < 0) {
            return null;
        }

        Vector3D location = P0.add(V.scalarMultiply(dist));
        Vector3D normal = location.subtract(center);
//         location = location.add(normal.scalarMultiply(1e-10)); TODO - is this relevant???
        return new Intersection(getMaterialIndex(), dist, location, normal);
    }
}
