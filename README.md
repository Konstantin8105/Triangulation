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

    # Run complete. Total time: 1 days, 07:27:20

    Benchmark                                        (size)  (test)  Mode  Cnt    Score   Error  Units
    TriangulationAdvanceBenchmark.triangulationMesh      10  Random  avgt   20    0.053 ± 0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      20  Random  avgt   20    0.110 ± 0.007  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      50  Random  avgt   20    0.225 ± 0.002  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100  Random  avgt   20    0.434 ± 0.005  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     200  Random  avgt   20    0.848 ± 0.008  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     500  Random  avgt   20    2.180 ± 0.019  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000  Random  avgt   20    4.566 ± 0.017  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    2000  Random  avgt   20    9.846 ± 0.052  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    5000  Random  avgt   20   25.634 ± 0.352  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000  Random  avgt   20   54.966 ± 0.222  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000  Random  avgt   20  113.908 ± 1.724  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000  Random  avgt   20  324.403 ± 4.944  ms/op

    TriangulationAdvanceBenchmark.triangulationMesh       3        Circle  avgt   20     0.021 ±  0.002  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh       5        Circle  avgt   20     0.039 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      10        Circle  avgt   20     0.089 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      20        Circle  avgt   20     0.174 ±  0.002  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      50        Circle  avgt   20     0.429 ±  0.011  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100        Circle  avgt   20     0.745 ±  0.038  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     200        Circle  avgt   20     1.667 ±  0.022  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     500        Circle  avgt   20     4.119 ±  0.052  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000        Circle  avgt   20     8.131 ±  0.074  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    2000        Circle  avgt   20    15.634 ±  0.203  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    5000        Circle  avgt   20    37.029 ±  0.340  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000        Circle  avgt   20   185.757 ±  2.752  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000        Circle  avgt   20   359.432 ±  1.432  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000        Circle  avgt   20  1039.053 ± 20.391  ms/op

    TriangulationAdvanceBenchmark.triangulationMesh       3  Line_in_line  avgt   20     0.025 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh       5  Line_in_line  avgt   20     0.044 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      10  Line_in_line  avgt   20     0.077 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      20  Line_in_line  avgt   20     0.134 ±  0.002  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      50  Line_in_line  avgt   20     0.303 ±  0.021  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100  Line_in_line  avgt   20     0.642 ±  0.028  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     200  Line_in_line  avgt   20     1.317 ±  0.015  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     500  Line_in_line  avgt   20     2.908 ±  0.038  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000  Line_in_line  avgt   20     4.191 ±  0.046  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    2000  Line_in_line  avgt   20     6.412 ±  0.233  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    5000  Line_in_line  avgt   20    13.238 ±  0.561  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000  Line_in_line  avgt   20    23.105 ±  1.049  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000  Line_in_line  avgt   20    42.096 ±  0.386  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000  Line_in_line  avgt   20   116.534 ±  6.222  ms/op

    TriangulationAdvanceBenchmark.triangulationMesh       3   In_triangle  avgt   20     0.014 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh       5   In_triangle  avgt   20     0.025 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      10   In_triangle  avgt   20     0.037 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      20   In_triangle  avgt   20     0.074 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh      50   In_triangle  avgt   20     0.159 ±  0.001  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     100   In_triangle  avgt   20     0.322 ±  0.006  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     200   In_triangle  avgt   20     0.653 ±  0.006  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh     500   In_triangle  avgt   20     1.763 ±  0.014  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    1000   In_triangle  avgt   20     2.964 ±  0.038  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    2000   In_triangle  avgt   20     5.464 ±  0.058  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh    5000   In_triangle  avgt   20    12.306 ±  0.185  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   10000   In_triangle  avgt   20    21.666 ±  0.540  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   20000   In_triangle  avgt   20    45.646 ±  4.257  ms/op
    TriangulationAdvanceBenchmark.triangulationMesh   50000   In_triangle  avgt   20   118.031 ±  4.780  ms/op

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