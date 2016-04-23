import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

class Intersection {
    private int materialIndex;
    private double distance;
    private Vector3D location;
    private Vector3D normal;

    Intersection(int materialIndex, double distance, Vector3D location, Vector3D normal) {
        this.materialIndex = materialIndex;
        this.distance = distance;
        this.location = location;
        this.normal = normal.normalize();
    }

    public int getMaterialIndex() {

        return materialIndex;
    }

    public void setMaterialIndex(int materialIndex) {
        this.materialIndex = materialIndex;
    }

    double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector3D getLocation() {
        return location;
    }

    public void setLocation(Vector3D location) {
        this.location = location;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }
}
