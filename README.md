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
    Amount points:      3 point. Time is         0,45 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0,50 ms. Triangles is      3 triangles.
    Amount points:     10 point. Time is         0,80 ms. Triangles is     14 triangles.
    Amount points:     20 point. Time is         1,20 ms. Triangles is     30 triangles.
    Amount points:     50 point. Time is         1,60 ms. Triangles is     89 triangles.
    Amount points:    100 point. Time is         2,45 ms. Triangles is    189 triangles.
    Amount points:    200 point. Time is         4,25 ms. Triangles is    387 triangles.
    Amount points:    500 point. Time is        11,95 ms. Triangles is    986 triangles.
    Amount points:   1000 point. Time is        36,60 ms. Triangles is   1971 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0,00 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0,10 ms. Triangles is      4 triangles.
    Amount points:     10 point. Time is         0,15 ms. Triangles is      9 triangles.
    Amount points:     20 point. Time is         0,25 ms. Triangles is     19 triangles.
    Amount points:     50 point. Time is         1,35 ms. Triangles is     49 triangles.
    Amount points:    100 point. Time is         3,35 ms. Triangles is     99 triangles.
    Amount points:    200 point. Time is        19,55 ms. Triangles is    199 triangles.
    Amount points:    500 point. Time is       226,00 ms. Triangles is    499 triangles.
    Amount points:   1000 point. Time is      1775,80 ms. Triangles is    999 triangles.
    TYPE OF TEST: LINE_IN_LINE
    Amount points:      3 point. Time is         0,15 ms. Triangles is      4 triangles.
    Amount points:      5 point. Time is         0,15 ms. Triangles is      6 triangles.
    Amount points:     10 point. Time is         0,25 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0,30 ms. Triangles is     21 triangles.
    Amount points:     50 point. Time is         0,80 ms. Triangles is     51 triangles.
    Amount points:    100 point. Time is         1,95 ms. Triangles is    101 triangles.
    Amount points:    200 point. Time is         6,35 ms. Triangles is    201 triangles.
    Amount points:    500 point. Time is        44,35 ms. Triangles is    501 triangles.
    Amount points:   1000 point. Time is       292,25 ms. Triangles is   1001 triangles.
    TYPE OF TEST: IN_TRIANGLE
    Amount points:      3 point. Time is         0,15 ms. Triangles is      7 triangles.
    Amount points:      5 point. Time is         0,20 ms. Triangles is     11 triangles.
    Amount points:     10 point. Time is         0,15 ms. Triangles is     21 triangles.
    Amount points:     20 point. Time is         0,30 ms. Triangles is     42 triangles.
    Amount points:     50 point. Time is         1,45 ms. Triangles is    102 triangles.
    Amount points:    100 point. Time is         3,25 ms. Triangles is    202 triangles.
    Amount points:    200 point. Time is        11,75 ms. Triangles is    402 triangles.
    Amount points:    500 point. Time is       103,00 ms. Triangles is   1002 triangles.
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