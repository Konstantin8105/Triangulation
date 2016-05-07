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

========================================

Performance for special test:

    TYPE OF TEST: RANDOM
    Amount points:      3 point. Time is        17.00 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         5.00 ms. Triangles is      5 triangles.
    Amount points:     10 point. Time is         4.00 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         9.00 ms. Triangles is     29 triangles.
    Amount points:     50 point. Time is        26.00 ms. Triangles is     91 triangles.
    Amount points:    100 point. Time is        39.00 ms. Triangles is    188 triangles.
    Amount points:    200 point. Time is       112.00 ms. Triangles is    387 triangles.
    Amount points:    500 point. Time is       242.00 ms. Triangles is    985 triangles.
    Amount points:   1000 point. Time is       937.00 ms. Triangles is   1981 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0.00 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0.00 ms. Triangles is      3 triangles.
    Amount points:     10 point. Time is         1.00 ms. Triangles is      8 triangles.
    Amount points:     20 point. Time is         3.00 ms. Triangles is     18 triangles.
    Amount points:     50 point. Time is        15.00 ms. Triangles is     48 triangles.
    Amount points:    100 point. Time is        81.00 ms. Triangles is     98 triangles.
    Amount points:    200 point. Time is       205.00 ms. Triangles is    198 triangles.
    Amount points:    500 point. Time is      1902.00 ms. Triangles is    498 triangles.
    Amount points:   1000 point. Time is     15628.00 ms. Triangles is    998 triangles.


![CIRCLE](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/CIRCLE.png)



![RANDOM](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/RANDOM.png)


========================================

