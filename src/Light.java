import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Light {
    private Vector3D position;
    private Color lightColor;
    private float specularIntensity;
    private float shadowIntensity;
    private float softShadowsRadius;

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }

    public float getShadowIntensity() {
        return shadowIntensity;
    }

    public void setShadowIntensity(float shadowIntensity) {
        this.shadowIntensity = shadowIntensity;
    }

    public float getSoftShadowsRadius() {
        return softShadowsRadius;
    }

    public void setSoftShadowsRadius(float softShadowsRadius) {
        this.softShadowsRadius = softShadowsRadius;
    }

    public Light(String[] params) {

        position = new Vector3D(Double.parseDouble(params[0]),
                Double.parseDouble(params[1]),
                Double.parseDouble(params[2]));
        lightColor = new Color(params[3], params[4], params[5]);
        specularIntensity = Float.parseFloat(params[6]);
        shadowIntensity = Float.parseFloat(params[7]);
        softShadowsRadius = Float.parseFloat(params[8]);

    }
}
