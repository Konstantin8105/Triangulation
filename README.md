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
    Amount points:      3 point. Time is         3.50 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         1.50 ms. Triangles is      4 triangles.
    Amount points:     10 point. Time is         4.50 ms. Triangles is     14 triangles.
    Amount points:     20 point. Time is         7.00 ms. Triangles is     32 triangles.
    Amount points:     50 point. Time is        24.50 ms. Triangles is     87 triangles.
    Amount points:    100 point. Time is        22.50 ms. Triangles is    184 triangles.
    Amount points:    200 point. Time is        46.50 ms. Triangles is    382 triangles.
    Amount points:    500 point. Time is       271.00 ms. Triangles is    976 triangles.
    Amount points:   1000 point. Time is       531.00 ms. Triangles is   1978 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0.00 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0.50 ms. Triangles is      4 triangles.
    Amount points:     10 point. Time is         0.50 ms. Triangles is      9 triangles.
    Amount points:     20 point. Time is         2.00 ms. Triangles is     19 triangles.
    Amount points:     50 point. Time is         5.50 ms. Triangles is     49 triangles.
    Amount points:    100 point. Time is        14.50 ms. Triangles is     99 triangles.
    Amount points:    200 point. Time is        63.50 ms. Triangles is    199 triangles.
    Amount points:    500 point. Time is       636.00 ms. Triangles is    499 triangles.
    Amount points:   1000 point. Time is      4515.50 ms. Triangles is    999 triangles.

Present algorithm complexity in worst case - O(n^2), average - O(N).

// TODO: Dynamic caching of line and triangles maps for change algorithm complexity in worst case to O(N*LOG(N))

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