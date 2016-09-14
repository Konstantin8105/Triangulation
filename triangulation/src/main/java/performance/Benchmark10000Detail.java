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
public class Benchmark10000Detail {

    // java -jar target/microbenchmarks.jar performance.Benchmark10000Detail

//    Benchmark                               (cleaningSize)  (searcherSize)  Mode  Cnt     Score     Error  Units
//    Benchmark10000Detail.triangulationMesh             0.1             0.1  avgt   20  1044.498 ± 129.426  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1            0.25  avgt   20   981.355 ± 128.323  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1             0.5  avgt   20  1106.500 ± 145.230  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1            0.75  avgt   20  1238.956 ± 259.168  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1             1.0  avgt   20   972.260 ± 108.318  ms/op

//    Benchmark10000Detail.triangulationMesh            0.25             0.1  avgt   20   545.741 ±  58.246  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25            0.25  avgt   20   978.316 ± 269.619  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25             0.5  avgt   20   704.299 ± 171.844  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25            0.75  avgt   20   703.856 ± 113.404  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25             1.0  avgt   20   637.340 ±  72.534  ms/op

//    Benchmark10000Detail.triangulationMesh             0.5             0.1  avgt   20   481.638 ± 102.712  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5            0.25  avgt   20   514.057 ± 101.620  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5             0.5  avgt   20   499.549 ± 101.710  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5            0.75  avgt   20   410.301 ±  54.261  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5             1.0  avgt   20   491.215 ±  71.222  ms/op

//    Benchmark10000Detail.triangulationMesh            0.75             0.1  avgt   20   350.816 ±  30.577  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75            0.25  avgt   20   374.989 ±  44.427  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75             0.5  avgt   20   389.662 ±  71.744  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75            0.75  avgt   20   470.595 ± 249.498  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75             1.0  avgt   20   359.569 ±  60.353  ms/op

//    Benchmark10000Detail.triangulationMesh             1.0             0.1  avgt   20   290.287 ±  25.534  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0            0.25  avgt   20   327.985 ±  34.416  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0             0.5  avgt   20   462.580 ± 154.933  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0            0.75  avgt   20   288.462 ±  39.499  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0             1.0  avgt   20   363.501 ±  67.155  ms/op



//    PerformanceBenchmark                              (cleaningSize)  (searcherSize)  Mode  Cnt  Score   Error  Units
//    Benchmark10000Detail.triangulationMesh             0.1             0.1  avgt   20  7.030 ± 0.782  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1            0.25  avgt   20  7.101 ± 0.865  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1             0.5  avgt   20  7.229 ± 0.794  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1            0.75  avgt   20  7.362 ± 0.777  ms/op
//    Benchmark10000Detail.triangulationMesh             0.1             1.0  avgt   20  7.157 ± 0.877  ms/op

//    Benchmark10000Detail.triangulationMesh            0.25             0.1  avgt   20  6.908 ± 0.759  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25            0.25  avgt   20  7.080 ± 0.904  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25             0.5  avgt   20  7.042 ± 0.821  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25            0.75  avgt   20  6.961 ± 0.827  ms/op
//    Benchmark10000Detail.triangulationMesh            0.25             1.0  avgt   20  7.175 ± 0.886  ms/op

//    Benchmark10000Detail.triangulationMesh             0.5             0.1  avgt   20  7.092 ± 0.812  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5            0.25  avgt   20  8.511 ± 1.468  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5             0.5  avgt   20  6.993 ± 0.851  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5            0.75  avgt   20  7.218 ± 0.795  ms/op
//    Benchmark10000Detail.triangulationMesh             0.5             1.0  avgt   20  7.407 ± 0.846  ms/op

//    Benchmark10000Detail.triangulationMesh            0.75             0.1  avgt   20  7.396 ± 0.749  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75            0.25  avgt   20  7.298 ± 0.843  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75             0.5  avgt   20  7.345 ± 0.765  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75            0.75  avgt   20  7.098 ± 0.832  ms/op
//    Benchmark10000Detail.triangulationMesh            0.75             1.0  avgt   20  7.167 ± 0.810  ms/op

    //    Benchmark10000Detail.triangulationMesh             1.0             0.1  avgt   20  7.088 ± 0.819  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0            0.25  avgt   20  6.999 ± 0.911  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0             0.5  avgt   20  7.116 ± 0.849  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0            0.75  avgt   20  7.003 ± 0.900  ms/op
//    Benchmark10000Detail.triangulationMesh             1.0             1.0  avgt   20  7.087 ± 0.878  ms/op

    @Param({
            "0.1",
            "0.25",
            "0.5",
            "0.75",
            "1.0",
    })
    String searcherSize;

    @Param({
            "0.1",
            "0.25",
            "0.5",
            "0.75",
            "1.0",
    })
    String cleaningSize;


    @Setup
    public void prepare() {
        points = (Point[]) ResearchTest.getRandomPoints(10_000).toArray();
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
