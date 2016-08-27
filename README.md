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
    ID0 : Line{idPointA=2, idPointB=1}
    ID1 : Line{idPointA=1, idPointB=0}
    ID2 : Line{idPointA=0, idPointB=2}
    ID3 : Line{idPointA=3, idPointB=0}
    ID4 : Line{idPointA=3, idPointB=2}
    --------------------
    Triangles :
    ID0 : Triangle{idPoint1=2, idPoint2=1, idPoint3=0}
    ID1 : Triangle{idPoint1=3, idPoint2=0, idPoint3=2}

========================================

Performance for special test:

    TYPE OF TEST: RANDOM
    Amount points:      3 point. Time is         1.95 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0.80 ms. Triangles is      3 triangles.
    Amount points:     10 point. Time is         1.50 ms. Triangles is     13 triangles.
    Amount points:     20 point. Time is         2.65 ms. Triangles is     31 triangles.
    Amount points:     50 point. Time is         7.20 ms. Triangles is     89 triangles.
    Amount points:    100 point. Time is         4.40 ms. Triangles is    187 triangles.
    Amount points:    200 point. Time is         8.10 ms. Triangles is    387 triangles.
    Amount points:    500 point. Time is        22.50 ms. Triangles is    983 triangles.
    Amount points:   1000 point. Time is        26.15 ms. Triangles is   1975 triangles.
    Amount points:   2000 point. Time is        35.45 ms. Triangles is   3979 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0.35 ms. Triangles is      3 triangles.
    Amount points:      5 point. Time is         0.75 ms. Triangles is      5 triangles.
    Amount points:     10 point. Time is         0.60 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0.65 ms. Triangles is     21 triangles.
    Amount points:     50 point. Time is         1.40 ms. Triangles is     51 triangles.
    Amount points:    100 point. Time is         2.10 ms. Triangles is    101 triangles.
    Amount points:    200 point. Time is         3.10 ms. Triangles is    201 triangles.
    Amount points:    500 point. Time is        12.10 ms. Triangles is    501 triangles.
    Amount points:   1000 point. Time is        63.20 ms. Triangles is   1001 triangles.
    Amount points:   2000 point. Time is        67.15 ms. Triangles is   2001 triangles.
    TYPE OF TEST: LINE_IN_LINE
    Amount points:      3 point. Time is         0.30 ms. Triangles is      4 triangles.
    Amount points:      5 point. Time is         0.25 ms. Triangles is      6 triangles.
    Amount points:     10 point. Time is         0.15 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0.30 ms. Triangles is     21 triangles.
    Amount points:     50 point. Time is         0.40 ms. Triangles is     51 triangles.
    Amount points:    100 point. Time is         0.75 ms. Triangles is    101 triangles.
    Amount points:    200 point. Time is         1.75 ms. Triangles is    201 triangles.
    Amount points:    500 point. Time is         3.75 ms. Triangles is    399 triangles.
    Amount points:   1000 point. Time is         6.05 ms. Triangles is    400 triangles.
    Amount points:   2000 point. Time is         4.95 ms. Triangles is    401 triangles.
    TYPE OF TEST: IN_TRIANGLE
    Amount points:      3 point. Time is         0.00 ms. Triangles is      9 triangles.
    Amount points:      5 point. Time is         0.00 ms. Triangles is     13 triangles.
    Amount points:     10 point. Time is         0.05 ms. Triangles is     23 triangles.
    Amount points:     20 point. Time is         0.10 ms. Triangles is     43 triangles.
    Amount points:     50 point. Time is         0.15 ms. Triangles is    103 triangles.
    Amount points:    100 point. Time is         0.35 ms. Triangles is    203 triangles.
    Amount points:    200 point. Time is         0.70 ms. Triangles is    403 triangles.
    Amount points:    500 point. Time is         1.25 ms. Triangles is    797 triangles.
    Amount points:   1000 point. Time is         2.55 ms. Triangles is    801 triangles.
    Amount points:   2000 point. Time is         4.10 ms. Triangles is    803 triangles.

Present algorithm complexity in worst case - O(n^2), average - O(N).

![CIRCLE](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/CIRCLE.png)

![RANDOM](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/RANDOM.png)

========================================

    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    Class name              |    Input data    |    Description    |    Status      |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    Triangulation           |   Points only    |   triangulation   |   DONE         |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    TriangulationDelaunay   |   Points only    |  triangulation    |   DONE         |
                            |                  |  with success     |                |
                            |                  |  Delaunay case    |                |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    PSLG                    |  Points and      |                   |   TODO         |
    (Planar Straight        |  Lines           |                   |                |
        Line Graph)         |                  |                   |                |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    TriangulationPSLG       |  Points and      |  triangulation    |   TODO         |
                            |  Lines           |  with input       |                |
                            |                  |  borders          |                |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    TriangulationCDT        | Points,          |  triangulation    |   TODO         |
    (Conforming             | Lines,           |  with input       |                |
       Delaunay             | Minimal area     |  borders and      |                |
          Triangulation)    | Max. area        |  separation big   |                |
                            |                  |  triangles        |                |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+

See [Definitions](https://www.cs.cmu.edu/~quake/triangle.defs.html)

========================================