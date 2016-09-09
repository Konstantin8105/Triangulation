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

**java -jar target/microbenchmarks.jar**

    Benchmark                                         (size)        (test)  Mode  Cnt     Score     Error  Units
    TriangulationDelaunayBenchmark.triangulationMesh      10        Random  avgt   20     0.088 ±   0.059  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh     100        Random  avgt   20     0.585 ±   0.064  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh    1000        Random  avgt   20     7.166 ±   0.843  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh   10000        Random  avgt   20    86.201 ±   6.242  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh  100000        Random  avgt   20  2116.499 ±  68.317  ms/op

    TriangulationDelaunayBenchmark.triangulationMesh      10        Circle  avgt   20     0.104 ±   0.052  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh     100        Circle  avgt   20     0.848 ±   0.067  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh    1000        Circle  avgt   20    12.771 ±   0.597  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh   10000        Circle  avgt   20   187.117 ±   6.940  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh  100000        Circle  avgt   20  2924.161 ± 142.246  ms/op

    TriangulationDelaunayBenchmark.triangulationMesh      10  Line_in_line  avgt   20     0.078 ±   0.008  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh     100  Line_in_line  avgt   20     0.605 ±   0.033  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh    1000  Line_in_line  avgt   20     2.388 ±   0.172  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh   10000  Line_in_line  avgt   20     3.290 ±   0.503  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh  100000  Line_in_line  avgt   20     8.170 ±   0.333  ms/op

    TriangulationDelaunayBenchmark.triangulationMesh      10   In_triangle  avgt   20     0.033 ±   0.012  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh     100   In_triangle  avgt   20     0.231 ±   0.032  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh    1000   In_triangle  avgt   20     0.979 ±   0.144  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh   10000   In_triangle  avgt   20     1.549 ±   0.158  ms/op
    TriangulationDelaunayBenchmark.triangulationMesh  100000   In_triangle  avgt   20     7.102 ±   0.309  ms/op

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