import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Light {
    private Vector3D position;
    private Color lightColor;
    private float specularIntensity;
    private float shadowIntensity;
    private float softShadowsRadius;

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
