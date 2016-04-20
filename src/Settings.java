
public class Settings {
    private Color backgroundColor;
    private int numberOfShadowRays;
    private int maxRecursion;

    public Settings(String[] params) {
        backgroundColor = new Color(params[0], params[1], params[2]);
        numberOfShadowRays = Integer.parseInt(params[3]);
        maxRecursion = Integer.parseInt(params[4]);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getNumberOfShadowRays() {
        return numberOfShadowRays;
    }

    public void setNumberOfShadowRays(int numberOfShadowRays) {
        this.numberOfShadowRays = numberOfShadowRays;
    }

    public int getMaxRecursion() {
        return maxRecursion;
    }

    public void setMaxRecursion(int maxRecursion) {
        this.maxRecursion = maxRecursion;
    }
}
