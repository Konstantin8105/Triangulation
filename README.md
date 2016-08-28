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

        Triangulation triangulation = new Triangulation(coordinates);
        System.out.println(triangulation.getMesh());

        assertTrue(triangulation.getMesh().sizeTriangles() > 0);
    }

========================================

Performance for special test:

    TYPE OF TEST: RANDOM
    Amount points:      3 point. Time is         4.20 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         3.75 ms. Triangles is      3 triangles.
    Amount points:     10 point. Time is         2.35 ms. Triangles is     13 triangles.
    Amount points:     20 point. Time is         6.15 ms. Triangles is     30 triangles.
    Amount points:     50 point. Time is         8.60 ms. Triangles is     90 triangles.
    Amount points:    100 point. Time is         9.45 ms. Triangles is    188 triangles.
    Amount points:    200 point. Time is         9.25 ms. Triangles is    384 triangles.
    Amount points:    500 point. Time is        23.20 ms. Triangles is    981 triangles.
    Amount points:   1000 point. Time is        64.00 ms. Triangles is   1981 triangles.
    Amount points:   2000 point. Time is        35.40 ms. Triangles is   3984 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0.45 ms. Triangles is      3 triangles.
    Amount points:      5 point. Time is         0.25 ms. Triangles is      5 triangles.
    Amount points:     10 point. Time is         0.50 ms. Triangles is     10 triangles.
    Amount points:     20 point. Time is         0.55 ms. Triangles is     20 triangles.
    Amount points:     50 point. Time is         1.35 ms. Triangles is     50 triangles.
    Amount points:    100 point. Time is         1.55 ms. Triangles is    100 triangles.
    Amount points:    200 point. Time is         3.25 ms. Triangles is    200 triangles.
    Amount points:    500 point. Time is         8.45 ms. Triangles is    500 triangles.
    Amount points:   1000 point. Time is        37.15 ms. Triangles is   1000 triangles.
    Amount points:   2000 point. Time is        54.00 ms. Triangles is   2000 triangles.
    TYPE OF TEST: LINE_IN_LINE
    Amount points:      3 point. Time is         0.40 ms. Triangles is      4 triangles.
    Amount points:      5 point. Time is         0.15 ms. Triangles is      6 triangles.
    Amount points:     10 point. Time is         0.20 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0.30 ms. Triangles is     21 triangles.
    Amount points:     50 point. Time is         0.30 ms. Triangles is     51 triangles.
    Amount points:    100 point. Time is         0.70 ms. Triangles is    101 triangles.
    Amount points:    200 point. Time is         1.45 ms. Triangles is    201 triangles.
    Amount points:    500 point. Time is         4.15 ms. Triangles is    399 triangles.
    Amount points:   1000 point. Time is         4.55 ms. Triangles is    400 triangles.
    Amount points:   2000 point. Time is         6.15 ms. Triangles is    401 triangles.
    TYPE OF TEST: IN_TRIANGLE
    Amount points:      3 point. Time is         0.05 ms. Triangles is      8 triangles.
    Amount points:      5 point. Time is         0.00 ms. Triangles is     12 triangles.
    Amount points:     10 point. Time is         0.00 ms. Triangles is     22 triangles.
    Amount points:     20 point. Time is         0.05 ms. Triangles is     42 triangles.
    Amount points:     50 point. Time is         0.20 ms. Triangles is    102 triangles.
    Amount points:    100 point. Time is         0.50 ms. Triangles is    202 triangles.
    Amount points:    200 point. Time is         0.75 ms. Triangles is    402 triangles.
    Amount points:    500 point. Time is         1.85 ms. Triangles is    796 triangles.
    Amount points:   1000 point. Time is         2.25 ms. Triangles is    800 triangles.
    Amount points:   2000 point. Time is         4.35 ms. Triangles is    802 triangles.


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