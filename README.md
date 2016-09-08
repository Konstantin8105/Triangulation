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

Performance with different cases:

    Benchmark                                        (size)        (test)  Mode  Cnt     Score    Error  Units
    TriangulationAdvanceBenchmark.triangulationMesh   50000        Random  avgt   20   325.045 ± 13.335  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000        Circle  avgt   20  1007.281 ± 17.755  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000  Line_in_line  avgt   20     5.407 ±  0.238  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000   In_triangle  avgt   20     4.560 ±  0.289  ms/op


    Benchmark                                        (size)        (test)  Mode  Cnt     Score    Error  Units
    TriangulationAdvanceBenchmark.triangulationMesh      10        Random  avgt   20  0.065 ± 0.023  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100        Random  avgt   20  0.437 ± 0.055  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000        Random  avgt   20  4.744 ± 0.293  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000        Random  avgt   20    55.091 ±  1.589  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000        Random  avgt   20   120.496 ±  2.794  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   30000        Random  avgt   20   184.443 ± 10.619  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   40000        Random  avgt   20   269.808 ± 14.129  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000        Random  avgt   20   354.092 ± 17.693  ms/op

    TriangulationAdvanceBenchmark.triangulationMesh      10        Circle  avgt   20  0.104 ± 0.059  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100        Circle  avgt   20  0.806 ± 0.060  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000        Circle  avgt   20  7.974 ± 0.639  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000        Circle  avgt   20   201.117 ±  7.926  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000        Circle  avgt   20   397.493 ± 12.365  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   30000        Circle  avgt   20   599.424 ± 14.640  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   40000        Circle  avgt   20   863.022 ± 53.199  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000        Circle  avgt   20  1066.332 ± 29.487  ms/op

    TriangulationAdvanceBenchmark.triangulationMesh      10  Line_in_line  avgt   20  0.080 ± 0.011  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100  Line_in_line  avgt   20  0.626 ± 0.036  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000  Line_in_line  avgt   20  2.530 ± 0.108  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000  Line_in_line  avgt   20     3.170 ±  0.262  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000  Line_in_line  avgt   20     3.671 ±  0.221  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   30000  Line_in_line  avgt   20     4.335 ±  0.278  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   40000  Line_in_line  avgt   20     4.958 ±  0.357  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000  Line_in_line  avgt   20     5.581 ±  0.163  ms/op

    TriangulationAdvanceBenchmark.triangulationMesh      10   In_triangle  avgt   20  0.046 ± 0.016  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100   In_triangle  avgt   20  0.318 ± 0.021  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000   In_triangle  avgt   20  1.557 ± 0.196  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000   In_triangle  avgt   20     2.196 ±  0.172  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000   In_triangle  avgt   20     2.759 ±  0.137  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   30000   In_triangle  avgt   20     3.517 ±  0.286  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   40000   In_triangle  avgt   20     4.057 ±  0.172  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000   In_triangle  avgt   20     4.643 ±  0.189  ms/op

Present algorithm complexity in worst case - O(N*LOG(N)), average - O(N*LOG(N)).

![CIRCLE](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/CIRCLE.png)

![RANDOM](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/RANDOM.png)

![GRAPH](https://github.com/Konstantin8105/Triangulation/blob/master/triangulation/other/Performance.png)

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