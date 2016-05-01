package old.core;

import com.home.fgd.stack.triangulation.Statistic;
import com.home.fgd.stack.triangulation.Triangulation;
import com.home.fgd.stack.triangulation.elements.Coordinate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TriangulationTest {

    @Test
    public void testGetters2points() throws Exception {
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 2);
        assertEquals(triangulation.getDB().sizeLines(), 0);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 0);

        triangulation.close();
    }

    @Test
    public void testGettersWithStatistic() throws Exception {
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 1f);
        coordinates[2] = new Coordinate(0f, 1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 3);
        assertEquals(triangulation.getDB().sizeLines(), 3);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 1);

        Statistic statistic = new Statistic(triangulation.getDB());
        System.out.println(statistic);
        triangulation.close();
    }

    @Test
    public void testGetters3points() throws Exception {
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 1f);
        coordinates[2] = new Coordinate(0f, 1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 3);
        assertEquals(triangulation.getDB().sizeLines(), 3);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 1);
        triangulation.close();
    }

    @Test
    public void testGetters3pointsOnOneLine1() throws Exception {
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(1f, 1f);
        coordinates[1] = new Coordinate(0f, 0f);
        coordinates[2] = new Coordinate(2f, 2f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 3);
        assertEquals(triangulation.getDB().sizeLines(), 0);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 0);
        triangulation.close();
    }

    @Test
    public void testGetters3pointsOnOneLine2() throws Exception {
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(-1f, -1f);
        coordinates[2] = new Coordinate(-2f, -2f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 3);
        assertEquals(triangulation.getDB().sizeLines(), 0);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 0);
        triangulation.close();
    }

    @Test
    public void testGetters3pointsOnOneLine3() throws Exception {
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(1f, 1f);
        coordinates[1] = new Coordinate(-1f, -1f);
        coordinates[2] = new Coordinate(0f, 0f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 3);
        assertEquals(triangulation.getDB().sizeLines(), 0);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 0);
        triangulation.close();
    }


    @Test
    public void testGetters7pointsWith6On1line() throws Exception {
        Coordinate[] coordinates = new Coordinate[7];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 1f);
        coordinates[2] = new Coordinate(2f, 2f);
        coordinates[3] = new Coordinate(3f, 3f);
        coordinates[4] = new Coordinate(4f, 4f);
        coordinates[5] = new Coordinate(5f, 5f);
        coordinates[6] = new Coordinate(1f, 0f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 7);
        assertEquals(triangulation.getDB().sizeLines(), 11);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 5);
        triangulation.close();
    }

    @Test
    public void testGetters4points() throws Exception {
        Coordinate[] coordinates = new Coordinate[4];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(0f, 1f);
        coordinates[2] = new Coordinate(1f, 0f);
        coordinates[3] = new Coordinate(1f, 1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 4);
        assertEquals(triangulation.getDB().sizeLines(), 5);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 2);
        triangulation.close();
    }

    @Test
    public void testGetters4points2() throws Exception {
        Coordinate[] coordinates = new Coordinate[4];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 0f);
        coordinates[2] = new Coordinate(0f, 1f);
        coordinates[3] = new Coordinate(1f, 1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 4);
        assertEquals(triangulation.getDB().sizeLines(), 5);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 2);
        triangulation.close();
    }

    @Test
    public void testGetters5points() throws Exception {
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 1f);
        coordinates[2] = new Coordinate(-1f, 1f);
        coordinates[3] = new Coordinate(1f, -1f);
        coordinates[4] = new Coordinate(-1f, -1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 5);
        assertEquals(triangulation.getDB().sizeLines(), 8);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 4);
        triangulation.close();
    }

    @Test
    public void testSpiral() throws Exception {
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(0f, 0f);
        for (int i = 0; i < coordinates.length - 1; i++) {
            float radius = 50f;
            float angle = 2f * 3.1415926f * ((float) (i)) / ((float) (coordinates.length + 1));
            coordinates[i + 1] = new Coordinate(radius * (float) Math.sin(angle), radius * (float) Math.cos(angle));
            System.out.println("i = " + coordinates[i + 1]);
        }

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        triangulation.close();
    }

    @Test
    public void testManyPointsOnOneLine() throws Exception {
        Coordinate[] coordinates = new Coordinate[17];
        coordinates[0] = new Coordinate(0f, 0f);
        coordinates[1] = new Coordinate(1f, 1f);
        coordinates[2] = new Coordinate(2f, 2f);
        coordinates[3] = new Coordinate(3f, 3f);
        coordinates[4] = new Coordinate(4f, 4f);
        coordinates[5] = new Coordinate(5f, 5f);
        coordinates[6] = new Coordinate(6f, 6f);
        coordinates[7] = new Coordinate(1.5f, 1.5f);
        coordinates[8] = new Coordinate(-1f, -1f);
        coordinates[9] = new Coordinate(-2f, -2f);
        coordinates[10] = new Coordinate(-3f, -3f);
        coordinates[11] = new Coordinate(-4f, -4f);
        coordinates[12] = new Coordinate(1f, -1f);
        coordinates[13] = new Coordinate(2f, -2f);
        coordinates[14] = new Coordinate(3f, -3f);
        coordinates[15] = new Coordinate(4f, -4f);
        coordinates[16] = new Coordinate(5f, -5f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 17);
//        assertEquals(triangulation.getDB().sizeLines()       ,8);
//        assertEquals(triangulation.getDB().getTrianglesID().size()   ,4);
        triangulation.close();
    }


    @Test
    public void testRandom1() throws Exception {
        String[] sd =
                {
                        "19.0995597839355", "-57.6626586914062",
                        "-27.6496410369873", "35.6753959655762",
                        "36.2190208435059", "30.5687294006348",
                        "11.4679737091064", "-56.8158302307129",
                        "33.413028717041", "52.4026908874512",
                        "22.9687995910645", "-6.67420148849487",
                        "12.1463375091553", "30.8549957275391",
                        "-11.4252452850342", "-31.1935691833496",
                        "-35.1983070373535", "2.59248018264771",
                        "-37.8859176635742", "27.7958030700684",
                        "-55.1695289611816", "-44.1072044372559"
                };


        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Double.parseDouble(sd[i * 2]), Double.parseDouble(sd[i * 2 + 1]));
        }

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        triangulation.close();
    }

    @Test
    public void testRandom2() throws Exception {
        String[] sd =
                {
                        "24.6247882843018", "-32.9209480285645",
                        "-56.3960037231445", "-9.26701736450195",
                        "56.9165382385254", "-20.0892524719238",
                        "-46.6352882385254", "8.6562967300415",
                        "-46.5097465515137", "10.259220123291"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        triangulation.close();
    }


    @Test
    public void testRandom3() throws Exception {
        String[] sd =
                {
                        "-13.9319944381714", "58.9681167602539",
                        "34.9756698608398", "49.266544342041",
                        "8.80909729003906", "33.7286071777344",
                        "-10.0766296386719", "1.6643214225769",
                        "-4.77735757827759", "-32.4209632873535"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom4() throws Exception {
        String[] sd =
                {
                        "+1.73743367", "+1.70922279",
                        "+1.96382821", "+4.81900871",
                        "+0.43933868", "-3.68514121",
                        "+1.74848318", "-3.26640546",
                        "+4.07117724", "-2.72354841"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom5() throws Exception {
        String[] sd =
                {
                        "+0.23624420", "+1.54300809",
                        "-3.96601558", "+3.23307693",
                        "+2.69377410", "-1.95407748",
                        "+2.23470449", "+2.13656247",
                        "+1.39454305", "+0.73590755"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom6() throws Exception {
        String[] sd =
                {
                        "+2.21706569", "+4.94942367",
                        "-3.32126260", "-0.34290969",
                        "-3.98626089", "-3.97501409",
                        "-3.62462103", "+3.54449451",
                        "-4.91298556", "+2.73551047"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom7() throws Exception {
        String[] sd =
                {
                        "-2.09450960", "+0.21593392",
                        "+3.87800992", "+2.67457604",
                        "-4.52040493", "-0.61397016",
                        "-1.73895895", "-2.20483840",
                        "+0.57869792", "+0.37405431"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom8() throws Exception {
        String[] sd =
                {
                        "+1.33413732","+0.06054670",
                        "+1.46700948","+1.16436183",
                        "+0.11810631","+0.53973168",
                        "+3.57751161","+3.70832026",
                        "+3.39187294","+1.29668802"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom9() throws Exception {
        String[] sd =
                {
                        "+0.07347107","+2.07574040",
                        "+1.96917415","+4.64517742",
                        "+4.89548624","+4.38915849",
                        "+3.04877460","+3.95381689",
                        "+1.04284257","+4.29461509"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());
        triangulation.close();
    }

    @Test
    public void testRandom10() throws Exception {
        String[] sd =
                {
                        "+1.61027580","+3.43846411",
                        "+3.47259372","+0.22233099",
                        "+1.99892044","+2.70686090",
                        "3","+0",
                        "+2.28121191","+0.25351465"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 5);
        assertEquals(triangulation.getDB().sizeLines(), 8);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 4);

        triangulation.close();
    }

    @Test
    public void testRandom11() throws Exception {
        String[] sd =
                {
                        "+2.87257969","+3.72186184",
                        "+0.01406759","+1.80638552",
                        "+1.58912182","+0.54620415",
                        "+1.28760755","+1.32848769",
                        "+1.81159079","+0.84039569"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 5);
        assertEquals(triangulation.getDB().sizeLines(), 8);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 4);

        triangulation.close();
    }

    @Test
    public void testRandom12() throws Exception {
        String[] sd =
                {
                        "+0.44128716","+4.45994854",
                        "+1.97576165","+4.42290336",
                        "+4.35392201","+0.95685005",
                        "+0.74773312","+4.29970711",
                        "+1.32661253","+1.64213061"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        triangulation.close();
    }


    @Test
    public void testRandom13() throws Exception {
        String[] sd =
                {
                        "+2.88205713","+2.52917528",
                        "+0.89632124","+3.69507521",
                        "+0.45458972","+4.62785929",
                        "+0.85078418","+3.23024362",
                        "+1.86506510","+3.22768062"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        triangulation.close();
    }


    @Test
    public void testRandom14() throws Exception {
        String[] sd =
                {
                        "+1.44489735","+4.21332330",
                        "+3.24500769","+2.71192789",
                        "+4.52277333","+0.14852315",
                        "+3.47377717","+2.76669562",
                        "+4.71780568","+4.96341884"
                };
        Coordinate[] coordinates = new Coordinate[sd.length / 2];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(Float.parseFloat(sd[i * 2]), Float.parseFloat(sd[i * 2 + 1]));
        }
        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        triangulation.close();
    }

    @Test
    public void testGetters6pointsWith5On1line() throws Exception {
        Coordinate[] coordinates = new Coordinate[6];
        coordinates[0] = new Coordinate(1f, 0f);
        coordinates[1] = new Coordinate(0f, 0f);
        coordinates[2] = new Coordinate(1f, 1f);
        coordinates[3] = new Coordinate(2f, 2f);
        coordinates[4] = new Coordinate(3f, 3f);
        coordinates[5] = new Coordinate(4f, 4f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 6);
        assertEquals(triangulation.getDB().sizeLines(), 9);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 4);
        triangulation.close();
    }

    @Test
    public void testGetters5pointsWith4On1line() throws Exception {
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(1f, 0f);
        coordinates[1] = new Coordinate(0f, 0f);
        coordinates[2] = new Coordinate(1f, 1f);
        coordinates[3] = new Coordinate(2f, 2f);
        coordinates[4] = new Coordinate(3f, 3f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 5);
        assertEquals(triangulation.getDB().sizeLines(), 7);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 3);
        triangulation.close();
    }

    @Test
    public void testGetters4pointsWith3On1line() throws Exception {
        Coordinate[] coordinates = new Coordinate[4];
        coordinates[0] = new Coordinate(1f, 0f);
        coordinates[1] = new Coordinate(0f, 0f);
        coordinates[2] = new Coordinate(1f, 1f);
        coordinates[3] = new Coordinate(2f, 2f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 4);
        assertEquals(triangulation.getDB().sizeLines(), 5);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 2);
        triangulation.close();
    }

    @Test
    public void testGetters3pointsWith2On1line() throws Exception {
        Coordinate[] coordinates = new Coordinate[3];
        coordinates[0] = new Coordinate(1f, 0f);
        coordinates[1] = new Coordinate(0f, 0f);
        coordinates[2] = new Coordinate(1f, 1f);

        Triangulation triangulation = new Triangulation();
        triangulation.add(Arrays.asList(coordinates));
        assertTrue(triangulation.run());

        assertEquals(triangulation.getDB().sizePoints(), 3);
        assertEquals(triangulation.getDB().sizeLines(), 3);
        assertEquals(triangulation.getDB().getTrianglesID().size(), 1);
        triangulation.close();
    }

    @Test
    public void testTriangulationRegular() throws Exception {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                coordinates.add(new Coordinate(i, j));
            }
        }

        Triangulation triangulation = new Triangulation();
        triangulation.add(coordinates);
        assertTrue(triangulation.run());

        System.out.println("Point=" + triangulation.getDB().sizePoints());
        System.out.println("Line=" + triangulation.getDB().sizeLines());
        System.out.println("Triangle=" + triangulation.getDB().getTrianglesID().size());

        triangulation.close();
    }


    @Test
    public void testTriangulationRegular2() throws Exception {
        List<Coordinate> coordinates = new ArrayList<>();
        double A = 100d;
        for (double i = 0; i < 2 * Math.PI; i += Math.PI / 5d) {
            coordinates.add(new Coordinate(A * Math.sin(i), A * Math.cos(i)));
        }
        A /= 0.01d;
        for (double i = 0; i < 2 * Math.PI; i += Math.PI / 5d) {
            coordinates.add(new Coordinate(A * Math.sin(i), A * Math.cos(i)));
        }

        Triangulation triangulation = new Triangulation();
        triangulation.add(coordinates);
        assertTrue(triangulation.run());

        triangulation.close();
    }
}