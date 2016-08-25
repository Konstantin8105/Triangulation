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
    Amount points:      3 point. Time is         0,85 ms. Triangles is      1 triangles.
    Amount points:      5 point. Time is         0,35 ms. Triangles is      3 triangles.
    Amount points:     10 point. Time is         0,70 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0,75 ms. Triangles is     30 triangles.
    Amount points:     50 point. Time is         1,25 ms. Triangles is     88 triangles.
    Amount points:    100 point. Time is         1,95 ms. Triangles is    187 triangles.
    Amount points:    200 point. Time is         7,50 ms. Triangles is    384 triangles.
    Amount points:    500 point. Time is        16,15 ms. Triangles is    984 triangles.
    Amount points:   1000 point. Time is        13,80 ms. Triangles is   1983 triangles.
    Amount points:   2000 point. Time is        13,40 ms. Triangles is   3978 triangles.
    TYPE OF TEST: CIRCLE
    Amount points:      3 point. Time is         0,25 ms. Triangles is      3 triangles.
    Amount points:      5 point. Time is         0,25 ms. Triangles is      5 triangles.
    Amount points:     10 point. Time is         0,35 ms. Triangles is     10 triangles.
    Amount points:     20 point. Time is         0,40 ms. Triangles is     20 triangles.
    Amount points:     50 point. Time is         0,50 ms. Triangles is     50 triangles.
    Amount points:    100 point. Time is         0,80 ms. Triangles is    100 triangles.
    Amount points:    200 point. Time is         1,60 ms. Triangles is    200 triangles.
    Amount points:    500 point. Time is         8,65 ms. Triangles is    500 triangles.
    Amount points:   1000 point. Time is        12,90 ms. Triangles is   1000 triangles.
    Amount points:   2000 point. Time is        26,35 ms. Triangles is   2000 triangles.
    TYPE OF TEST: LINE_IN_LINE
    Amount points:      3 point. Time is         0,20 ms. Triangles is      4 triangles.
    Amount points:      5 point. Time is         0,10 ms. Triangles is      6 triangles.
    Amount points:     10 point. Time is         0,10 ms. Triangles is     11 triangles.
    Amount points:     20 point. Time is         0,20 ms. Triangles is     21 triangles.
    Amount points:     50 point. Time is         0,30 ms. Triangles is     51 triangles.
    Amount points:    100 point. Time is         0,55 ms. Triangles is    101 triangles.
    Amount points:    200 point. Time is         1,45 ms. Triangles is    201 triangles.
    Amount points:    500 point. Time is         2,10 ms. Triangles is    399 triangles.
    Amount points:   1000 point. Time is         3,10 ms. Triangles is    400 triangles.
    Amount points:   2000 point. Time is         3,85 ms. Triangles is    401 triangles.
    TYPE OF TEST: IN_TRIANGLE
    Amount points:      3 point. Time is         0,00 ms. Triangles is      8 triangles.
    Amount points:      5 point. Time is         0,00 ms. Triangles is     12 triangles.
    Amount points:     10 point. Time is         0,05 ms. Triangles is     22 triangles.
    Amount points:     20 point. Time is         0,00 ms. Triangles is     42 triangles.
    Amount points:     50 point. Time is         0,05 ms. Triangles is    102 triangles.
    Amount points:    100 point. Time is         0,05 ms. Triangles is    202 triangles.
    Amount points:    200 point. Time is         0,15 ms. Triangles is    402 triangles.
    Amount points:    500 point. Time is         0,45 ms. Triangles is    796 triangles.
    Amount points:   1000 point. Time is         1,15 ms. Triangles is    800 triangles.
    Amount points:   2000 point. Time is         2,70 ms. Triangles is    802 triangles.

    # Run complete. Total time: 00:20:18

    Benchmark                                 (size)        (test)  Mode  Cnt   Score    Error  Units
    TriangulationBenchmark.triangulationMesh       3        Random  avgt   20   0.001 ±  0.001  ms/op
    TriangulationBenchmark.triangulationMesh       3        Circle  avgt   20   0.001 ±  0.001  ms/op
    TriangulationBenchmark.triangulationMesh       3  Line_in_line  avgt   20   0.006 ±  0.001  ms/op
    TriangulationBenchmark.triangulationMesh       3   In_triangle  avgt   20   0.013 ±  0.001  ms/op

    TriangulationBenchmark.triangulationMesh       5        Random  avgt   20   0.004 ±  0.001  ms/op
    TriangulationBenchmark.triangulationMesh       5        Circle  avgt   20   0.004 ±  0.001  ms/op
    TriangulationBenchmark.triangulationMesh       5  Line_in_line  avgt   20   0.011 ±  0.001  ms/op
    TriangulationBenchmark.triangulationMesh       5   In_triangle  avgt   20   0.019 ±  0.001  ms/op

    TriangulationBenchmark.triangulationMesh     100        Random  avgt   20   1.303 ±  0.056  ms/op
    TriangulationBenchmark.triangulationMesh     100        Circle  avgt   20   2.403 ±  0.094  ms/op
    TriangulationBenchmark.triangulationMesh     100  Line_in_line  avgt   20   1.220 ±  0.038  ms/op
    TriangulationBenchmark.triangulationMesh     100   In_triangle  avgt   20   2.210 ±  0.151  ms/op

    TriangulationBenchmark.triangulationMesh     200        Random  avgt   20   3.474 ±  0.345  ms/op
    TriangulationBenchmark.triangulationMesh     200        Circle  avgt   20  10.144 ±  0.553  ms/op
    TriangulationBenchmark.triangulationMesh     200  Line_in_line  avgt   20   6.059 ±  0.224  ms/op
    TriangulationBenchmark.triangulationMesh     200   In_triangle  avgt   20   8.057 ±  0.323  ms/op

    TriangulationBenchmark.triangulationMesh     500        Random  avgt   20  12.430 ±  0.642  ms/op
    TriangulationBenchmark.triangulationMesh     500        Circle  avgt   20  97.872 ± 11.548  ms/op
    TriangulationBenchmark.triangulationMesh     500  Line_in_line  avgt   20  20.037 ±  2.350  ms/op
    TriangulationBenchmark.triangulationMesh     500   In_triangle  avgt   20  50.085 ±  2.244  ms/op



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