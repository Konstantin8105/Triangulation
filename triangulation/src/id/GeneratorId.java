package id;

public class GeneratorId {
    private static int position = 1;

    public static int getID() {
        return position++;
    }
}
