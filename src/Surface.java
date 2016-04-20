public abstract class Surface {
    private int materialIndex;

    public Surface(String param) {
        materialIndex = Integer.parseInt(param);
    }

    public int getMaterialIndex() {
        return materialIndex;
    }
}
