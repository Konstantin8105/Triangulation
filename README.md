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
    PerformanceBenchmark.triangulationMesh      10        Random  avgt   20    0.007 ±   0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000        Random  avgt   20  156.381 ±   2.236  ms/op
    PerformanceBenchmark.triangulationMesh   60000        Random  avgt   20  342.539 ±   9.282  ms/op
    PerformanceBenchmark.triangulationMesh   90000        Random  avgt   20  586.725 ±  54.283  ms/op
    PerformanceBenchmark.triangulationMesh  120000        Random  avgt   20  932.582 ± 223.114  ms/op

    PerformanceBenchmark.triangulationMesh      10        Circle  avgt   20    0.002 ±   0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000        Circle  avgt   20    5.047 ±   0.351  ms/op
    PerformanceBenchmark.triangulationMesh   60000        Circle  avgt   20   10.827 ±   0.659  ms/op
    PerformanceBenchmark.triangulationMesh   90000        Circle  avgt   20   88.873 ±   5.979  ms/op
    PerformanceBenchmark.triangulationMesh  120000        Circle  avgt   20   20.291 ±   0.295  ms/op

    PerformanceBenchmark.triangulationMesh      10  Line_in_line  avgt   20    0.005 ±   0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000  Line_in_line  avgt   20   14.626 ±   0.208  ms/op
    PerformanceBenchmark.triangulationMesh   60000  Line_in_line  avgt   20   31.025 ±   0.969  ms/op
    PerformanceBenchmark.triangulationMesh   90000  Line_in_line  avgt   20   60.588 ±   9.938  ms/op
    PerformanceBenchmark.triangulationMesh  120000  Line_in_line  avgt   20   63.440 ±   0.720  ms/op

    PerformanceBenchmark.triangulationMesh      10   In_triangle  avgt   20    0.010 ±   0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000   In_triangle  avgt   20   44.317 ±   0.615  ms/op
    PerformanceBenchmark.triangulationMesh   60000   In_triangle  avgt   20   97.107 ±  10.877  ms/op
    PerformanceBenchmark.triangulationMesh   90000   In_triangle  avgt   20  145.108 ±   3.980  ms/op
    PerformanceBenchmark.triangulationMesh  120000   In_triangle  avgt   20  197.915 ±  16.130  ms/op

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