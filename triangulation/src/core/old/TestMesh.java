package core.old;

import core.old.storage.RamMesh;

public class TestMesh {

    public static void test(RamMesh data) throws Exception {
//        List<Point> listLines = data.getPoints();
//        int amount = 0;
//        for (int i = 0; i < listLines.size(); i++) {
//            int[] point1 = data.getIdPointsByLine(listLines.get(i));
//            for (int j = i; j < listLines.size(); j++) {
//                if (i != j) {
//                    int[] point2 = data.getIdPointsByLine(listLines.get(j));
//                    GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
//                            data.getCoordinateByPointId(point1[0]),
//                            data.getCoordinateByPointId(point1[1]),
//                            data.getCoordinateByPointId(point2[0]),
//                            data.getCoordinateByPointId(point2[1]));
//                    if (state == GeometryLineLine.IntersectState.INTERSECT ||
//                            state == GeometryLineLine.IntersectState.LINE_IN_LINE ||
//                            state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE) {
//                        amount++;
//                        String string = "\n";
//                        string += " POINTS COORDINATE:" + "\n";
//                        string += "Line :\n";
//                        string += data.getCoordinateByPointId(point1[0]);
//                        string += data.getCoordinateByPointId(point1[1]);
//                        string += "Line :\n";
//                        string += "\n";
//                        string += data.getCoordinateByPointId(point2[0]);
//                        string += data.getCoordinateByPointId(point2[1]);
//                        string += "\n";
//                        string += "STATE:" + "\n";
//                        string += state + "\n";
//                        string += "\n";
//                        string += "LINE:" + "\n";
//                        string += "Line" + listLines.get(i) + "\n";
//                        string += point1[0] + ":" + point1[1] + "\n";
//                        string += "Line" + listLines.get(j) + "\n";
//                        string += point2[0] + ":" + point2[1] + "\n";
//                        System.out.println(string);
//                    }
//                }
//            }
//        }
//        if (amount > 0) {
//            old.view.MeshView meshView = new old.view.MeshView(data);
//            //throw new Exception("Test wrong. Amount wrong = " + amount);
//        }
////        else
////            System.out.println("TEST OK");
    }

}
