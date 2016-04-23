import static java.lang.StrictMath.min;

class Color {
    private final static int MAX_COLOR_VALUE = 1;
    private final static int MIN_COLOR_VALUE = 0;
    private final static int TO_BYTE = 255;

    private double R;
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

}
