package performance;

import org.openjdk.jmh.annotations.*;
import research.ResearchTriangulation;
import triangulation.elements.Point;
import triangulation.TriangulationDelaunay;
import triangulation.FastSearcher;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 10, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10)
@Timeout(time = 20, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Benchmark1000 {

    // java -jar target/microbenchmarks.jar performance.Benchmark1000
//    Benchmark1000.triangulationMesh           0.001           0.001  avgt   20   7.922 ±  0.822  ms/op
//    Benchmark1000.triangulationMesh           0.001            0.01  avgt   20   7.868 ±  0.757  ms/op
//    Benchmark1000.triangulationMesh           0.001             0.1  avgt   20   7.476 ±  0.855  ms/op
//    Benchmark1000.triangulationMesh           0.001             1.0  avgt   20   7.375 ±  0.811  ms/op
//    Benchmark1000.triangulationMesh           0.001            10.0  avgt   20   8.555 ±  0.928  ms/op
//    Benchmark1000.triangulationMesh           0.001           100.0  avgt   20  52.882 ± 13.795  ms/op

//    Benchmark1000.triangulationMesh            0.01           0.001  avgt   20   7.700 ±  0.889  ms/op
//    Benchmark1000.triangulationMesh            0.01            0.01  avgt   20   7.628 ±  0.766  ms/op
//    Benchmark1000.triangulationMesh            0.01             0.1  avgt   20   7.360 ±  0.855  ms/op
//    Benchmark1000.triangulationMesh            0.01             1.0  avgt   20   7.482 ±  0.831  ms/op
//    Benchmark1000.triangulationMesh            0.01            10.0  avgt   20   8.298 ±  0.757  ms/op
//    Benchmark1000.triangulationMesh            0.01           100.0  avgt   20  31.802 ±  7.374  ms/op

//    Benchmark1000.triangulationMesh             0.1           0.001  avgt   20   7.348 ±  0.961  ms/op
//    Benchmark1000.triangulationMesh             0.1            0.01  avgt   20   7.405 ±  0.706  ms/op
//    Benchmark1000.triangulationMesh             0.1             0.1  avgt   20   7.005 ±  0.791  ms/op
//    Benchmark1000.triangulationMesh             0.1             1.0  avgt   20   6.993 ±  0.866  ms/op
//    Benchmark1000.triangulationMesh             0.1            10.0  avgt   20   8.103 ±  0.978  ms/op
//    Benchmark1000.triangulationMesh             0.1           100.0  avgt   20  25.693 ±  1.920  ms/op

//    Benchmark1000.triangulationMesh             1.0           0.001  avgt   20   7.510 ±  0.714  ms/op
//    Benchmark1000.triangulationMesh             1.0            0.01  avgt   20   7.468 ±  0.804  ms/op
//    Benchmark1000.triangulationMesh             1.0             0.1  avgt   20   7.101 ±  0.881  ms/op
//    Benchmark1000.triangulationMesh             1.0             1.0  avgt   20   7.072 ±  0.798  ms/op
//    Benchmark1000.triangulationMesh             1.0            10.0  avgt   20   8.267 ±  0.932  ms/op
//    Benchmark1000.triangulationMesh             1.0           100.0  avgt   20  39.068 ±  7.832  ms/op

//    Benchmark1000.triangulationMesh            10.0           0.001  avgt   20  12.629 ±  6.906  ms/op
//    Benchmark1000.triangulationMesh            10.0            0.01  avgt   20   7.326 ±  0.819  ms/op
//    Benchmark1000.triangulationMesh            10.0             0.1  avgt   20   6.954 ±  0.821  ms/op
//    Benchmark1000.triangulationMesh            10.0             1.0  avgt   20   7.672 ±  1.978  ms/op
//    Benchmark1000.triangulationMesh            10.0            10.0  avgt   20   8.288 ±  0.964  ms/op
//    Benchmark1000.triangulationMesh            10.0           100.0  avgt   20  31.108 ±  3.400  ms/op

//    Benchmark1000.triangulationMesh           100.0           0.001  avgt   20   7.450 ±  0.841  ms/op
//    Benchmark1000.triangulationMesh           100.0            0.01  avgt   20   7.468 ±  0.856  ms/op
//    Benchmark1000.triangulationMesh           100.0             0.1  avgt   20   7.096 ±  0.763  ms/op
//    Benchmark1000.triangulationMesh           100.0             1.0  avgt   20   7.265 ±  0.948  ms/op
//    Benchmark1000.triangulationMesh           100.0            10.0  avgt   20   8.722 ±  1.604  ms/op
//    Benchmark1000.triangulationMesh           100.0           100.0  avgt   20  41.475 ±  8.437  ms/op

    @Param({
            "0.001",
            "0.01",
            "0.1",
            "1.0",
            "10.0",
            "100.0"
    })
    String cleaningSize;

    @Param({
            "0.001",
            "0.01",
            "0.1",
            "1.0",
            "10.0",
            "100.0"
    })
    String searcherSize;


    @Setup
    public void prepare() {
        points = (Point[]) ResearchTriangulation.getRandomPoints(1_000).toArray();
    }

    Point[] points;

    @org.openjdk.jmh.annotations.Benchmark
    public int triangulationMesh(){
        TriangulationDelaunay triangulation = new TriangulationDelaunay();
        TriangulationDelaunay.MINIMAL_POINTS_FOR_CLEANING = 3;
        TriangulationDelaunay.AMOUNT_CLEANING_FACTOR_TRIANGLE_STRUCTURE = Double.parseDouble(cleaningSize);
        FastSearcher.AMOUNT_SEARCHER_FACTOR = Double.parseDouble(searcherSize);
        triangulation.run(points);
        return triangulation.getTriangles().size();
    }
}
