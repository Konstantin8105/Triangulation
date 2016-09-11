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
public class BenchmarkCleaning {

    // java -jar target/microbenchmarks.jar performance.BenchmarkCleaning

//    BenchmarkCleaning.triangulationMesh     0.001    1000  avgt   20  7.226 ± 0.784  ms/op
//    BenchmarkCleaning.triangulationMesh      0.01    1000  avgt   20  7.370 ± 0.772  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1    1000  avgt   20  7.056 ± 0.799  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0    1000  avgt   20  7.157 ± 0.853  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0    1000  avgt   20  7.112 ± 0.857  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0    1000  avgt   20  6.964 ± 0.890  ms/op

//    BenchmarkCleaning.triangulationMesh     0.001   10000  avgt   20  142.593 ± 8.327  ms/op
//    BenchmarkCleaning.triangulationMesh      0.01   10000  avgt   20  145.925 ± 9.622  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1   10000  avgt   20   94.824 ± 7.109  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0   10000  avgt   20   86.930 ± 5.899  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0   10000  avgt   20   85.687 ± 5.751  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0   10000  avgt   20   88.331 ± 7.594  ms/op

//    BenchmarkCleaning.triangulationMesh     0.001  100000  avgt   20  2238.541 ±  53.757  ms/op
//    BenchmarkCleaning.triangulationMesh      0.01  100000  avgt   20  1463.578 ±  43.989  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1  100000  avgt   20  1173.669 ±  83.338  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0  100000  avgt   20  1496.815 ± 209.448  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0  100000  avgt   20  1539.799 ± 282.744  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0  100000  avgt   20  1518.114 ± 267.777  ms/op
//    BenchmarkCleaning.triangulationMesh    1000.0  100000  avgt   20  1480.888 ± 211.561  ms/op


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
        TriangulationDelaunay.AMOUNT_CLEANING_FACTOR_TRIANGLE_STRUCTURE = Double.parseDouble(factor);
        triangulation.run(points);
        return triangulation.getTriangles().size();
    }
}
