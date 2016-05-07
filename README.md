========================================

Triangulation

Project for create 2D triangulation.

Simple example:

    @Test
    public void simpleExample() throws Exception {
        Point[] coordinates = new Point[]{
                new Point(0.0f, 0.0f),
                new Point(1.0f, 0.0f),
                new Point(1.0f, 1.0f),
                new Point(1.0f, 5.0f)
        };

        Triangulation triangulation = new Triangulation(Arrays.asList(coordinates));
        System.out.println(triangulation.getMesh());

        assertTrue(triangulation.getMesh().sizeTriangles() > 0);
    }

Result:

    Mesh :
    --------------------
    Points :
    ID0 : Point{ x = 0.000000, y = 0.000000}
    ID1 : Point{ x = 1.000000, y = 0.000000}
    ID2 : Point{ x = 1.000000, y = 1.000000}
    ID3 : Point{ x = 1.000000, y = 5.000000}
    --------------------
    Line :
    ID4 : Line{idPointA=2, idPointB=1}
    ID5 : Line{idPointA=1, idPointB=0}
    ID6 : Line{idPointA=0, idPointB=2}
    ID8 : Line{idPointA=3, idPointB=0}
    ID9 : Line{idPointA=3, idPointB=2}
    --------------------
    Triangles :
    ID7 : Triangle{idPoint1=2, idPoint2=1, idPoint3=0}
    ID10 : Triangle{idPoint1=3, idPoint2=0, idPoint3=2}

