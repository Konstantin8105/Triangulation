package elements.test;

import elements.Coordinate;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CoordinateTest {

    private float delta = 1e-5f;

    @Test
    public void testGettersZero() {
        float dataX = 0f;
        float dataY = 0f;
        Coordinate coordinate = new Coordinate(dataX, dataY);
        assertEquals(coordinate.getX(), dataX, delta);
        assertEquals(coordinate.getY(), dataY, delta);
    }

    @Test
    public void testGettersRandom() {
        Random random = new Random();
        float dataX = (float) (random.nextInt(100));
        float dataY = (float) (random.nextInt(100));
        Coordinate coordinate = new Coordinate(dataX, dataY);
        assertEquals(coordinate.getX(), dataX, delta);
        assertEquals(coordinate.getY(), dataY, delta);
    }

    @Test
    public void testGettersSing() {
        Random random = new Random();
        float dataX = -(float) (random.nextInt(100));
        float dataY = -(float) (random.nextInt(100));
        Coordinate coordinate = new Coordinate(dataX, dataY);
        assertEquals(coordinate.getX(), dataX, delta);
        assertEquals(coordinate.getY(), dataY, delta);
    }

    @Test
    public void testGettersMaxValue() {
        float dataX = Float.MAX_VALUE;
        float dataY = Float.MAX_VALUE;
        Coordinate coordinate = new Coordinate(dataX, dataY);
        assertEquals(coordinate.getX(), dataX, delta);
        assertEquals(coordinate.getY(), dataY, delta);
    }

    @Test
    public void testGettersMinValue() {
        float dataX = Float.MIN_VALUE;
        float dataY = Float.MIN_VALUE;
        Coordinate coordinate = new Coordinate(dataX, dataY);
        assertEquals(coordinate.getX(), dataX, delta);
        assertEquals(coordinate.getY(), dataY, delta);
    }
}