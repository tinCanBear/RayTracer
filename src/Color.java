public class Color {
    private double R;
    private double G;
    private double B;

    public Color() {
        this(0, 0, 0);
    }

    public Color(double r, double g, double b) {
        R = r;
        G = g;
        B = b;
    }

    public Color(String r, String g, String b) {
        this(Double.parseDouble(r),
             Double.parseDouble(g),
             Double.parseDouble(b));
    }

}
