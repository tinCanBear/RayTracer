import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

abstract class Surface {
    private int materialIndex;

    Surface(String param) {
        materialIndex = Integer.parseInt(param);
    }

    int getMaterialIndex() {
        return materialIndex;
    }

    public abstract Intersection getIntersection(Vector3D V, Vector3D P0);
}
