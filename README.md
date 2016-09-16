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
    PerformanceBenchmark.triangulationMesh      10        Random  avgt   20     0.009 ±   0.002  ms/op
    PerformanceBenchmark.triangulationMesh   30000        Random  avgt   20   183.101 ±  29.021  ms/op
    PerformanceBenchmark.triangulationMesh   60000        Random  avgt   20   527.194 ± 140.599  ms/op
    PerformanceBenchmark.triangulationMesh   90000        Random  avgt   20  1328.815 ± 280.890  ms/op
    PerformanceBenchmark.triangulationMesh  120000        Random  avgt   20  1809.766 ± 388.014  ms/op

    PerformanceBenchmark.triangulationMesh      10        Circle  avgt   20     0.015 ±   0.008  ms/op
    PerformanceBenchmark.triangulationMesh   30000        Circle  avgt   20   227.777 ±   5.877  ms/op
    PerformanceBenchmark.triangulationMesh   60000        Circle  avgt   20   616.926 ± 140.015  ms/op
    PerformanceBenchmark.triangulationMesh   90000        Circle  avgt   20  1498.155 ± 167.988  ms/op
    PerformanceBenchmark.triangulationMesh  120000        Circle  avgt   20  1841.663 ± 133.690  ms/op

    PerformanceBenchmark.triangulationMesh      10  Line_in_line  avgt   20     0.043 ±   0.003  ms/op
    PerformanceBenchmark.triangulationMesh   30000  Line_in_line  avgt   20     3.478 ±   0.124  ms/op
    PerformanceBenchmark.triangulationMesh   60000  Line_in_line  avgt   20     8.241 ±   2.542  ms/op
    PerformanceBenchmark.triangulationMesh   90000  Line_in_line  avgt   20    14.951 ±   2.954  ms/op
    PerformanceBenchmark.triangulationMesh  120000  Line_in_line  avgt   20    15.105 ±   1.750  ms/op

    PerformanceBenchmark.triangulationMesh      10   In_triangle  avgt   20     0.015 ±   0.003  ms/op
    PerformanceBenchmark.triangulationMesh   30000   In_triangle  avgt   20     2.515 ±   0.359  ms/op
    PerformanceBenchmark.triangulationMesh   60000   In_triangle  avgt   20     4.853 ±   0.740  ms/op
    PerformanceBenchmark.triangulationMesh   90000   In_triangle  avgt   20    11.789 ±   2.682  ms/op
    PerformanceBenchmark.triangulationMesh  120000   In_triangle  avgt   20    14.340 ±   1.837  ms/op

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