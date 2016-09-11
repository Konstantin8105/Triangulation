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

    Benchmark                               (size)        (test)  Mode  Cnt     Score     Error  Units
    PerformanceBenchmark.triangulationMesh      10        Random  avgt   20     0.070 ±   0.024  ms/op
    PerformanceBenchmark.triangulationMesh     100        Random  avgt   20     0.638 ±   0.089  ms/op
    PerformanceBenchmark.triangulationMesh    1000        Random  avgt   20     7.025 ±   0.822  ms/op
    PerformanceBenchmark.triangulationMesh   10000        Random  avgt   20    90.821 ±   8.807  ms/op
    PerformanceBenchmark.triangulationMesh  100000        Random  avgt   20  1266.770 ± 125.287  ms/op

    PerformanceBenchmark.triangulationMesh      10        Circle  avgt   20     0.119 ±   0.091  ms/op
    PerformanceBenchmark.triangulationMesh     100        Circle  avgt   20     0.853 ±   0.072  ms/op
    PerformanceBenchmark.triangulationMesh    1000        Circle  avgt   20     8.855 ±   0.595  ms/op
    PerformanceBenchmark.triangulationMesh   10000        Circle  avgt   20   202.680 ±  15.613  ms/op
    PerformanceBenchmark.triangulationMesh  100000        Circle  avgt   20  3063.978 ± 143.580  ms/op

    PerformanceBenchmark.triangulationMesh      10  Line_in_line  avgt   20     0.079 ±   0.009  ms/op
    PerformanceBenchmark.triangulationMesh     100  Line_in_line  avgt   20     0.604 ±   0.049  ms/op
    PerformanceBenchmark.triangulationMesh    1000  Line_in_line  avgt   20     2.382 ±   0.120  ms/op
    PerformanceBenchmark.triangulationMesh   10000  Line_in_line  avgt   20     5.492 ±   0.882  ms/op
    PerformanceBenchmark.triangulationMesh  100000  Line_in_line  avgt   20    13.685 ±   0.570  ms/op

    PerformanceBenchmark.triangulationMesh      10   In_triangle  avgt   20     0.035 ±   0.014  ms/op
    PerformanceBenchmark.triangulationMesh     100   In_triangle  avgt   20     0.234 ±   0.032  ms/op
    PerformanceBenchmark.triangulationMesh    1000   In_triangle  avgt   20     0.977 ±   0.111  ms/op
    PerformanceBenchmark.triangulationMesh   10000   In_triangle  avgt   20     1.807 ±   0.279  ms/op
    PerformanceBenchmark.triangulationMesh  100000   In_triangle  avgt   20     7.195 ±   0.337  ms/op

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