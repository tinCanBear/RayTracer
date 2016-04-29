import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

class Cylinder extends Surface {
    private Vector3D center;
    private double length;
    private double radius;
    private Vector3D rotation;
    private Vector3D direction;
    private Vector3D upperCapCenter;
    private Vector3D lowerCapCenter;
    private Plane upCap;
    private Plane downCap;

    public Vector3D getUpperCapCenter() {
        return upperCapCenter;
    }

    public Vector3D getLowerCapCenter() {
        return lowerCapCenter;
    }

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
        direction = figureOutDirection();
        upperCapCenter = getCenter().add(getDirection().scalarMultiply(getLength() / 2));
        lowerCapCenter = getCenter().add(getDirection().scalarMultiply(-getLength() / 2));
        upCap = new Plane(getMaterialIndex(), getDirection(), getUpperCapCenter().dotProduct(getDirection()));
        downCap = new Plane(getMaterialIndex(), getDirection(), getLowerCapCenter().dotProduct(getDirection()));
    }

    private Vector3D figureOutDirection() {
        Vector3D res = new Vector3D(0, 0, -1);
        Matrix rotX = new Matrix(getRotation().getX(), 'x');
        Matrix rotY = new Matrix(getRotation().getY(), 'y');
        Matrix rotZ = new Matrix(getRotation().getZ(), 'z');
        res = Matrix.multVectorMatrix(rotX, res);
        res = Matrix.multVectorMatrix(rotY, res);
        res = Matrix.multVectorMatrix(rotZ, res);
        return res.normalize();
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

    public Vector3D getDirection() {
        return direction;
    }

    public Plane getUpCap() {
        return upCap;
    }

    public Plane getDownCap() {
        return downCap;
    }

    @Override
    public Intersection getIntersection(Vector3D V, Vector3D P0) {

        List<Intersection> solutions = new ArrayList<>();

        //calculate intersection with infinite cylinder:
        Vector3D deltaP = P0.subtract(getLowerCapCenter());
        Vector3D temp1 = V.subtract(getDirection().scalarMultiply(V.dotProduct(getDirection())));
        Vector3D temp2 = deltaP.subtract(getDirection().scalarMultiply(deltaP.dotProduct(getDirection())));


        double A = temp1.getNormSq();
        double B = 2 * (temp1.dotProduct(temp2));
        double C = temp2.getNormSq() - pow(getRadius(), 2);
        double det = pow(B, 2) - 4 * A * C;

        if (A == 0) {
            det= -1;
        }

        // solve quadratic equation
        if (det >= 0) {
            double t1 = (-B + sqrt(det)) / (2 * A);
            double t2 = (-B - sqrt(det)) / (2 * A);

            boolean checkT1 = true;
            if (t1 <= 0) checkT1 = false;
            boolean checkT2 = true;
            if (t2 <= 0) checkT2 = false;

            if (checkT1 && (getDirection().dotProduct(P0.add(V.scalarMultiply(t1)).subtract(getLowerCapCenter()))> 0) &&
                    (getDirection().dotProduct(P0.add(V.scalarMultiply(t1)).subtract(getUpperCapCenter())) < 0)) {

                Vector3D v1 = P0.add(V.scalarMultiply(t1)).subtract(getLowerCapCenter());
                double len1 = sqrt(v1.getNormSq() - pow(getRadius(), 2));
                Vector3D toDeepIntersect = getLowerCapCenter().add(getDirection().scalarMultiply(len1));
                v1 = P0.add(V.scalarMultiply(t1)).subtract(toDeepIntersect).normalize();
                Vector3D locationInter1 = P0.add(V.scalarMultiply(t2)).add(v1.scalarMultiply(1e-10)); //TODO
                solutions.add(new Intersection(getMaterialIndex(), t1, locationInter1, v1));
            }
            if (checkT2 && (getDirection().dotProduct(P0.add(V.scalarMultiply(t2)).subtract(getLowerCapCenter())) > 0) &&
                    (getDirection().dotProduct(P0.add(V.scalarMultiply(t2)).subtract(getUpperCapCenter())) < 0)) {
                Vector3D v2 = P0.add(V.scalarMultiply(t2)).subtract(getLowerCapCenter());
                double len2 = sqrt(v2.getNormSq() - pow(getRadius(), 2));
                Vector3D toDeepIntersect = getLowerCapCenter().add(getDirection().scalarMultiply(len2));
                v2 = P0.add(V.scalarMultiply(t2)).subtract(toDeepIntersect).normalize();
                Vector3D locationInter2 = P0.add(V.scalarMultiply(t2)).add(v2.scalarMultiply(1e-10)); //TODO
                solutions.add(new Intersection(getMaterialIndex(), t2, locationInter2, v2));
            }

        }

//          possible intersection with cylinder's caps
        Intersection i3 = getUpCap().getIntersection(V, P0);
        Intersection i4 = getDownCap().getIntersection(V, P0);

        if (i3 != null && i3.getLocation().subtract(getUpperCapCenter()).getNormSq() < pow(getRadius(), 2))
            solutions.add(i3);
        if (i4 != null && i4.getLocation().subtract(getLowerCapCenter()).getNormSq() < pow(getRadius(), 2))
            solutions.add(i4);

        if (solutions.isEmpty()) return null;

        //sort intersections by distance
        Collections.sort(solutions, new Comparator<Intersection>() {
            @Override
            public int compare(Intersection a, Intersection b) {
                return Double.compare(a.getDistance(), b.getDistance());
            }
        });

        return solutions.get(0);
    }
}
