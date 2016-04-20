public class Material {
    private Color diffuseColor;
    private Color specularColor;
    private Color reflectionColor;
    private float specularityCoefficient;
    private float transparency;

    public float getSpecularityCoefficient() {
        return specularityCoefficient;
    }

    public float getTransparency() {
        return transparency;
    }

    public Material(String[] params) {
        diffuseColor = new Color(params[0], params[1], params[2]);
        specularColor = new Color(params[3], params[4], params[5]);
        reflectionColor = new Color(params[6], params[7], params[8]);
        specularityCoefficient = Float.parseFloat(params[9]);
        transparency = Float.parseFloat(params[10]);

    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
    }

    public Color getReflectionColor() {
        return reflectionColor;
    }

    public void setReflectionColor(Color reflectionColor) {
        this.reflectionColor = reflectionColor;
    }
}
