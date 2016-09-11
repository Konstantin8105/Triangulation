package performance;

import org.openjdk.jmh.annotations.*;
import research.ResearchTest;
import triangulation.Point;
import triangulation.TriangulationDelaunay;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 10, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10)
@Timeout(time = 20, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkSearchers {

    // java -jar target/microbenchmarks.jar performance.BenchmarkSearchers

//    PerformanceBenchmark                             (factor)  (size)  Mode  Cnt      Score      Error  Units
//    BenchmarkSearchers.triangulationMesh     0.001    1000  avgt   20      7.380 ±    0.914  ms/op
//    BenchmarkSearchers.triangulationMesh      0.01    1000  avgt   20      7.386 ±    0.786  ms/op
//    BenchmarkSearchers.triangulationMesh       0.1    1000  avgt   20     11.109 ±    4.327  ms/op
//    BenchmarkSearchers.triangulationMesh       1.0    1000  avgt   20     11.417 ±    3.587  ms/op
//    BenchmarkSearchers.triangulationMesh      10.0    1000  avgt   20      7.835 ±    0.805  ms/op
//    BenchmarkSearchers.triangulationMesh     100.0    1000  avgt   20     37.393 ±    1.912  ms/op

//    BenchmarkSearchers.triangulationMesh     0.001   10000  avgt   20    120.364 ±   12.092  ms/op
//    BenchmarkSearchers.triangulationMesh      0.01   10000  avgt   20    112.977 ±    7.079  ms/op
//    BenchmarkSearchers.triangulationMesh       0.1   10000  avgt   20    115.559 ±   28.258  ms/op
//    BenchmarkSearchers.triangulationMesh       1.0   10000  avgt   20     94.551 ±    7.330  ms/op
//    BenchmarkSearchers.triangulationMesh      10.0   10000  avgt   20    120.801 ±    9.022  ms/op
//    BenchmarkSearchers.triangulationMesh     100.0   10000  avgt   20    425.317 ±   43.887  ms/op

//    BenchmarkSearchers.triangulationMesh     0.001  100000  avgt   20   2406.238 ±   87.632  ms/op
//    BenchmarkSearchers.triangulationMesh      0.01  100000  avgt   20   1543.508 ±   85.532  ms/op
//    BenchmarkSearchers.triangulationMesh       0.1  100000  avgt   20   1967.065 ±  292.237  ms/op
//    BenchmarkSearchers.triangulationMesh       1.0  100000  avgt   20   1281.724 ±  177.034  ms/op
//    BenchmarkSearchers.triangulationMesh      10.0  100000  avgt   20   1723.760 ±   72.219  ms/op
//    BenchmarkSearchers.triangulationMesh     100.0  100000  avgt   20  14183.523 ± 3048.428  ms/op


    @Param({
            "0.001",
            "0.01",
            "0.1",
            "1.0",
            "10.0",
            "100.0"
    })
    String factor;

    @Param({
            "1000",
            "10000",
            "100000"
    })
    int size;

    @Setup
    public void prepare() {
        points = (Point[]) ResearchTest.getRandomPoints(size).toArray();
    }

    Point[] points;

    @org.openjdk.jmh.annotations.Benchmark
    public int triangulationMesh() throws Exception {
        TriangulationDelaunay triangulation = new TriangulationDelaunay();
        TriangulationDelaunay.MINIMAL_POINTS_FOR_CLEANING = 3;
        TriangulationDelaunay.AMOUNT_SEARCHER_FACTOR = Double.parseDouble(factor);
        triangulation.run(points);
        return triangulation.getTriangles().size();
    }
}
