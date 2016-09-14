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

        TriangulationDelaunay triangulation = new TriangulationDelaunay(coordinates);
        System.out.println(triangulation.getMesh());

        assertTrue(triangulation.getMesh().sizeTriangles() > 0);
    }

========================================

Performance with different cases:

    Benchmark                               (size)        (test)  Mode  Cnt    Score     Error  Units
    PerformanceBenchmark.triangulationMesh    5000        Random  avgt   20   22.617 ±   3.468  ms/op
    PerformanceBenchmark.triangulationMesh   10000        Random  avgt   20   48.490 ±   6.859  ms/op
    PerformanceBenchmark.triangulationMesh   50000        Random  avgt   20  414.480 ±  64.190  ms/op
    PerformanceBenchmark.triangulationMesh  100000        Random  avgt   20  817.028 ± 198.210  ms/op

    PerformanceBenchmark.triangulationMesh    5000        Circle  avgt   20    5.068 ±   0.816  ms/op
    PerformanceBenchmark.triangulationMesh   10000        Circle  avgt   20   89.438 ±  11.124  ms/op
    PerformanceBenchmark.triangulationMesh   50000        Circle  avgt   20  691.605 ± 323.062  ms/op
    PerformanceBenchmark.triangulationMesh  100000        Circle  avgt   20  890.215 ±  69.800  ms/op

    PerformanceBenchmark.triangulationMesh    5000  Line_in_line  avgt   20    1.927 ±   0.091  ms/op
    PerformanceBenchmark.triangulationMesh   10000  Line_in_line  avgt   20    2.805 ±   0.470  ms/op
    PerformanceBenchmark.triangulationMesh   50000  Line_in_line  avgt   20    4.848 ±   0.179  ms/op
    PerformanceBenchmark.triangulationMesh  100000  Line_in_line  avgt   20    8.455 ±   0.612  ms/op

    PerformanceBenchmark.triangulationMesh    5000   In_triangle  avgt   20    0.808 ±   0.094  ms/op
    PerformanceBenchmark.triangulationMesh   10000   In_triangle  avgt   20    1.587 ±   0.372  ms/op
    PerformanceBenchmark.triangulationMesh   50000   In_triangle  avgt   20    3.643 ±   0.291  ms/op
    PerformanceBenchmark.triangulationMesh  100000   In_triangle  avgt   20    7.656 ±   0.771  ms/op

Present algorithm complexity in worst case - O(NxLOG(N)), average - O(NxLOG(N)).

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