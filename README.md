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

    Benchmark                               (size)        (test)  Mode  Cnt     Score    Error  Units
    PerformanceBenchmark.triangulationMesh      10        Random  avgt   60     0.008 ±  0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000        Random  avgt   60   158.032 ±  5.777  ms/op
    PerformanceBenchmark.triangulationMesh   60000        Random  avgt   60   329.993 ±  9.647  ms/op
    PerformanceBenchmark.triangulationMesh   90000        Random  avgt   60   522.203 ± 22.679  ms/op
    PerformanceBenchmark.triangulationMesh  120000        Random  avgt   60   792.240 ± 38.973  ms/op

    PerformanceBenchmark.triangulationMesh      10        Circle  avgt   60     0.009 ±  0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000        Circle  avgt   60   222.203 ±  4.764  ms/op
    PerformanceBenchmark.triangulationMesh   60000        Circle  avgt   60   467.556 ± 21.560  ms/op
    PerformanceBenchmark.triangulationMesh   90000        Circle  avgt   60   669.681 ±  3.911  ms/op
    PerformanceBenchmark.triangulationMesh  120000        Circle  avgt   60   932.616 ± 24.791  ms/op

    PerformanceBenchmark.triangulationMesh      10  Line_in_line  avgt   60     0.128 ±  0.005  ms/op
    PerformanceBenchmark.triangulationMesh   30000  Line_in_line  avgt   60   392.184 ±  5.562  ms/op
    PerformanceBenchmark.triangulationMesh   60000  Line_in_line  avgt   60   872.671 ± 70.395  ms/op
    PerformanceBenchmark.triangulationMesh   90000  Line_in_line  avgt   60  1238.298 ± 26.651  ms/op
    PerformanceBenchmark.triangulationMesh  120000  Line_in_line  avgt   60  1644.480 ± 24.890  ms/op

    PerformanceBenchmark.triangulationMesh      10   In_triangle  avgt   60     0.010 ±  0.001  ms/op
    PerformanceBenchmark.triangulationMesh   30000   In_triangle  avgt   60    43.634 ±  2.228  ms/op
    PerformanceBenchmark.triangulationMesh   60000   In_triangle  avgt   60    91.832 ±  4.443  ms/op
    PerformanceBenchmark.triangulationMesh   90000   In_triangle  avgt   60   144.639 ±  9.812  ms/op
    PerformanceBenchmark.triangulationMesh  120000   In_triangle  avgt   60   185.268 ±  4.815  ms/op

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