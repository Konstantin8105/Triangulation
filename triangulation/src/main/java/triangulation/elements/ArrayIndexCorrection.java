package triangulation.elements;

public class ArrayIndexCorrection {

    public static int normalize(int index, int size) {
        if (0 <= index && index < size) {
            return index;
        }
        if (index < 0)
            return normalize(index + size,size);
        return normalize(index - size,size);
    }

    public static int normalizeSizeBy3(int index){
        return normalize(index,3);
    }
}
