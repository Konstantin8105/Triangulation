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
    Amount points:      3 point. Time is         0,05 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0,06 ms. Triangles is      5 triangles.
    Amount points:     10 point. Time is         0,19 ms. Triangles is     13 triangles.
    Amount points:     20 point. Time is         0,29 ms. Triangles is     31 triangles.
    Amount points:     50 point. Time is         0,64 ms. Triangles is     88 triangles.
    Amount points:    100 point. Time is         1,27 ms. Triangles is    188 triangles.
    Amount points:    200 point. Time is         1,87 ms. Triangles is    384 triangles.
    Amount points:    500 point. Time is         7,53 ms. Triangles is    984 triangles.
    Amount points:   1000 point. Time is        22,87 ms. Triangles is   1978 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0,00 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0,01 ms. Triangles is      4 triangles.
    Amount points:     10 point. Time is         0,04 ms. Triangles is      9 triangles.
    Amount points:     20 point. Time is         0,12 ms. Triangles is     19 triangles.
    Amount points:     50 point. Time is         0,70 ms. Triangles is     49 triangles.
    Amount points:    100 point. Time is         3,27 ms. Triangles is     99 triangles.
    Amount points:    200 point. Time is        17,23 ms. Triangles is    199 triangles.
    Amount points:    500 point. Time is       192,39 ms. Triangles is    499 triangles.
    Amount points:   1000 point. Time is      1366,71 ms. Triangles is    999 triangles.
    TYPE OF TEST: LINE_IN_LINE
    Amount points:      3 point. Time is         0,03 ms. Triangles is      4 triangles.
    Amount points:      5 point. Time is         0,02 ms. Triangles is      6 triangles.
    Amount points:     10 point. Time is         0,05 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0,09 ms. Triangles is     21 triangles.
    Amount points:     50 point. Time is         0,27 ms. Triangles is     51 triangles.
    Amount points:    100 point. Time is         0,79 ms. Triangles is    101 triangles.
    Amount points:    200 point. Time is         2,81 ms. Triangles is    201 triangles.
    Amount points:    500 point. Time is        26,87 ms. Triangles is    501 triangles.
    Amount points:   1000 point. Time is       253,83 ms. Triangles is   1001 triangles.
    TYPE OF TEST: IN_TRIANGLE
    Amount points:      3 point. Time is         0,04 ms. Triangles is      7 triangles.
    Amount points:      5 point. Time is         0,04 ms. Triangles is     11 triangles.
    Amount points:     10 point. Time is         0,09 ms. Triangles is     21 triangles.
    Amount points:     20 point. Time is         0,15 ms. Triangles is     42 triangles.
    Amount points:     50 point. Time is         0,65 ms. Triangles is    102 triangles.
    Amount points:    100 point. Time is         2,47 ms. Triangles is    202 triangles.
    Amount points:    200 point. Time is        10,74 ms. Triangles is    402 triangles.
    Amount points:    500 point. Time is        99,19 ms. Triangles is   1002 triangles.
    Amount points:   1000 point. Time is      1011,45 ms. Triangles is   2002 triangles.


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
    Triangulation           |    Points ony    |   triangulation   |   DONE         |
                            |                  |                   |                |
    ------------------------+------------------+-------------------+----------------+
                            |                  |                   |                |
    TriangulationDelaunay   |   Points only    |  triangulation    |   TODO         |
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