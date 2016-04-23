import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;


class Camera {
    private Vector3D position;
    private Vector3D lookAt;
    private Vector3D up;
    private Vector3D upFixedNormalized;
    private Vector3D toScreenNormalized;
    private Vector3D toCenterScreen;

    Vector3D getToCenterScreen() {
        return toCenterScreen;
    }

    void setToCenterScreen(Vector3D toCenterScreen) {
        this.toCenterScreen = toCenterScreen;
    }

    private Vector3D leftSideScreenNormalized;

    Vector3D getUpFixedNormalized() {
        return upFixedNormalized;
    }

    Vector3D getToScreenNormalized() {
        return toScreenNormalized;
    }

    Vector3D getLeftSideScreenNormalized() {
        return leftSideScreenNormalized;
    }

    float getScreenHeight() {
        return screenHeight;
    }

    void setUpFixedNormalized(Vector3D upFixedNormalized) {

        this.upFixedNormalized = upFixedNormalized;
    }

    void setToScreenNormalized(Vector3D toScreenNormalized) {
        this.toScreenNormalized = toScreenNormalized;
    }

    void setLeftSideScreenNormalized(Vector3D leftSideScreenNormalized) {
        this.leftSideScreenNormalized = leftSideScreenNormalized;
    }

    void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    private float screenDistance;
    private float screenWidth;
    private float screenHeight;


    Camera(String[] params) {
        double[] vectors = new double[9];
        for (int i = 0; i < vectors.length; i++){vectors[i] = Double.parseDouble(params[i]);}
        position = new Vector3D(vectors[0],vectors[1],vectors[2]);
        lookAt = new Vector3D(vectors[3],vectors[4],vectors[5]);
        up = new Vector3D(vectors[6],vectors[7],vectors[8]);
        screenDistance = Float.parseFloat(params[9]);
        screenWidth = Float.parseFloat(params[10]);
    }

    public Camera(Vector3D position, Vector3D lookAt, Vector3D up, float screenDistance, float screenWidth) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;
        this.screenDistance = screenDistance;
        this.screenWidth = screenWidth;
    }

    Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    Vector3D getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3D lookAt) {
        this.lookAt = lookAt;
    }

    Vector3D getUp() {
        return up;
    }

    public void setUp(Vector3D up) {
        this.up = up;
    }

    float getScreenDistance() {
        return screenDistance;
    }

    public void setScreenDistance(float screenDistance) {
        this.screenDistance = screenDistance;
    }

    float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }
}
