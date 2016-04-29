import static java.lang.StrictMath.min;

class Color {
    private final static int MAX_COLOR_VALUE = 1;
    private final static int MIN_COLOR_VALUE = 0;
    private final static int TO_BYTE = 255;

    private double R;

    public void setR(double r) {
        R = r;
    }

    public void setG(double g) {
        G = g;
    }

    public void setB(double b) {
        B = b;
    }

    private double G;
    private double B;

    public Color() {
        this(MIN_COLOR_VALUE, MIN_COLOR_VALUE, MIN_COLOR_VALUE);
    }

    Color(double r, double g, double b) {
        R = r;
        G = g;
        B = b;
    }

    Color(String r, String g, String b) {
        this(Double.parseDouble(r),
                Double.parseDouble(g),
                Double.parseDouble(b));
    }

    byte getRed() {
        return (byte) (min(MAX_COLOR_VALUE, R) * TO_BYTE);
    }

    byte getGreen() {
        return (byte) (min(MAX_COLOR_VALUE, G) * TO_BYTE);
    }

    byte getBlue() {
        return (byte) (min(MAX_COLOR_VALUE, B) * TO_BYTE);
    }


    public double getR() {
        return R;
    }

    public double getG() {
        return G;
    }

    public double getB() {
        return B;
    }

    public static Color sum(Color a, Color b) {
        Color c = new Color();
        c.setR(a.getR() + b.getR());
        c.setG(a.getG() + b.getG());
        c.setB(a.getB() + b.getB());
        return c;
    }

    public static Color multiplyWithFactor(Color a, Color b, double d) {
        Color c = new Color();

        c.setR(a.getR() * b.getR() * d);
        c.setG(a.getG() * b.getG() * d);
        c.setB(a.getB() * b.getB() * d);
        return c;
    }
}
