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
public class Benchmark1000Detail {

    // java -jar target/microbenchmarks.jar performance.Benchmark1000Detail
//    Benchmark                              (cleaningSize)  (searcherSize)  Mode  Cnt  Score   Error  Units
//    Benchmark1000Detail.triangulationMesh             0.1             0.1  avgt   20  7.030 ± 0.782  ms/op
//    Benchmark1000Detail.triangulationMesh             0.1            0.25  avgt   20  7.101 ± 0.865  ms/op
//    Benchmark1000Detail.triangulationMesh             0.1             0.5  avgt   20  7.229 ± 0.794  ms/op
//    Benchmark1000Detail.triangulationMesh             0.1            0.75  avgt   20  7.362 ± 0.777  ms/op
//    Benchmark1000Detail.triangulationMesh             0.1             1.0  avgt   20  7.157 ± 0.877  ms/op

//    Benchmark1000Detail.triangulationMesh            0.25             0.1  avgt   20  6.908 ± 0.759  ms/op
//    Benchmark1000Detail.triangulationMesh            0.25            0.25  avgt   20  7.080 ± 0.904  ms/op
//    Benchmark1000Detail.triangulationMesh            0.25             0.5  avgt   20  7.042 ± 0.821  ms/op
//    Benchmark1000Detail.triangulationMesh            0.25            0.75  avgt   20  6.961 ± 0.827  ms/op
//    Benchmark1000Detail.triangulationMesh            0.25             1.0  avgt   20  7.175 ± 0.886  ms/op

//    Benchmark1000Detail.triangulationMesh             0.5             0.1  avgt   20  7.092 ± 0.812  ms/op
//    Benchmark1000Detail.triangulationMesh             0.5            0.25  avgt   20  8.511 ± 1.468  ms/op
//    Benchmark1000Detail.triangulationMesh             0.5             0.5  avgt   20  6.993 ± 0.851  ms/op
//    Benchmark1000Detail.triangulationMesh             0.5            0.75  avgt   20  7.218 ± 0.795  ms/op
//    Benchmark1000Detail.triangulationMesh             0.5             1.0  avgt   20  7.407 ± 0.846  ms/op

//    Benchmark1000Detail.triangulationMesh            0.75             0.1  avgt   20  7.396 ± 0.749  ms/op
//    Benchmark1000Detail.triangulationMesh            0.75            0.25  avgt   20  7.298 ± 0.843  ms/op
//    Benchmark1000Detail.triangulationMesh            0.75             0.5  avgt   20  7.345 ± 0.765  ms/op
//    Benchmark1000Detail.triangulationMesh            0.75            0.75  avgt   20  7.098 ± 0.832  ms/op
//    Benchmark1000Detail.triangulationMesh            0.75             1.0  avgt   20  7.167 ± 0.810  ms/op

//    Benchmark1000Detail.triangulationMesh             1.0             0.1  avgt   20  7.088 ± 0.819  ms/op
//    Benchmark1000Detail.triangulationMesh             1.0            0.25  avgt   20  6.999 ± 0.911  ms/op
//    Benchmark1000Detail.triangulationMesh             1.0             0.5  avgt   20  7.116 ± 0.849  ms/op
//    Benchmark1000Detail.triangulationMesh             1.0            0.75  avgt   20  7.003 ± 0.900  ms/op
//    Benchmark1000Detail.triangulationMesh             1.0             1.0  avgt   20  7.087 ± 0.878  ms/op
    @Param({
            "0.1",
            "0.25",
            "0.5",
            "0.75",
            "1.0",
    })
    String cleaningSize;

    @Param({
            "0.1",
            "0.25",
            "0.5",
            "0.75",
            "1.0",
    })
    String searcherSize;


    @Setup
    public void prepare() {
        points = (Point[]) ResearchTest.getRandomPoints(1_000).toArray();
    }

    Point[] points;

    @org.openjdk.jmh.annotations.Benchmark
    public int triangulationMesh() throws Exception {
        TriangulationDelaunay triangulation = new TriangulationDelaunay();
        TriangulationDelaunay.MINIMAL_POINTS_FOR_CLEANING = 3;
        TriangulationDelaunay.AMOUNT_CLEANING_FACTOR_TRIANGLE_STRUCTURE = Double.parseDouble(cleaningSize);
        TriangulationDelaunay.AMOUNT_SEARCHER_FACTOR = Double.parseDouble(searcherSize);
        triangulation.run(points);
        return triangulation.getTriangles().size();
    }
}
