import java.util.Vector;

public class RayVector {
    private Vector<Integer> vector = new Vector<>(3);

    public RayVector(int x, int y, int z) {
        vector.add(0, x); vector.add(1, y); vector.add(2, z);
    }

    public Vector<Integer> getVector() {
        return vector;
    }

/*    public int dotProduct(RayVector other) {

    }

    public RayVector crossProduct(RayVector other) {

    }*/
}
