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
//@Timeout(time = 20, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkSearchers {

    // java -jar target/microbenchmarks.jar performance.BenchmarkSearchers
/*
    PerformanceBenchmark                             (factor)  (size)  Mode  Cnt      Score      Error  Units
    BenchmarkSearchers.triangulationMesh     0.001    1000  avgt   20      7.380 ±    0.914  ms/op
    BenchmarkSearchers.triangulationMesh      0.01    1000  avgt   20      7.386 ±    0.786  ms/op
    BenchmarkSearchers.triangulationMesh       0.1    1000  avgt   20     11.109 ±    4.327  ms/op
    BenchmarkSearchers.triangulationMesh       1.0    1000  avgt   20     11.417 ±    3.587  ms/op
    BenchmarkSearchers.triangulationMesh      10.0    1000  avgt   20      7.835 ±    0.805  ms/op
    BenchmarkSearchers.triangulationMesh     100.0    1000  avgt   20     37.393 ±    1.912  ms/op

    BenchmarkSearchers.triangulationMesh     0.001   10000  avgt   20    120.364 ±   12.092  ms/op
    BenchmarkSearchers.triangulationMesh      0.01   10000  avgt   20    112.977 ±    7.079  ms/op
    BenchmarkSearchers.triangulationMesh       0.1   10000  avgt   20    115.559 ±   28.258  ms/op
    BenchmarkSearchers.triangulationMesh       1.0   10000  avgt   20     94.551 ±    7.330  ms/op
    BenchmarkSearchers.triangulationMesh      10.0   10000  avgt   20    120.801 ±    9.022  ms/op
    BenchmarkSearchers.triangulationMesh     100.0   10000  avgt   20    425.317 ±   43.887  ms/op

    BenchmarkSearchers.triangulationMesh     0.001  100000  avgt   20   2406.238 ±   87.632  ms/op
    BenchmarkSearchers.triangulationMesh      0.01  100000  avgt   20   1543.508 ±   85.532  ms/op
    BenchmarkSearchers.triangulationMesh       0.1  100000  avgt   20   1967.065 ±  292.237  ms/op
    BenchmarkSearchers.triangulationMesh       1.0  100000  avgt   20   1281.724 ±  177.034  ms/op
    BenchmarkSearchers.triangulationMesh      10.0  100000  avgt   20   1723.760 ±   72.219  ms/op
    BenchmarkSearchers.triangulationMesh     100.0  100000  avgt   20  14183.523 ± 3048.428  ms/op


Benchmark                             (factor)  (size)  Mode  Cnt     Score      Error  Units
BenchmarkSearchers.triangulationMesh     0.001    1000  avgt   20     3,714 ?    0,845  ms/op
BenchmarkSearchers.triangulationMesh     0.001   10000  avgt   20    53,725 ?    5,181  ms/op
BenchmarkSearchers.triangulationMesh     0.001  100000  avgt   20  1030,200 ?   36,135  ms/op

BenchmarkSearchers.triangulationMesh      0.01    1000  avgt   20     3,182 ?    0,432  ms/op
BenchmarkSearchers.triangulationMesh      0.01   10000  avgt   20    52,912 ?    5,364  ms/op
BenchmarkSearchers.triangulationMesh      0.01  100000  avgt   20   619,481 ?   12,101  ms/op

BenchmarkSearchers.triangulationMesh      0.01    1000  avgt   20    3,249 ?  0,443  ms/op
BenchmarkSearchers.triangulationMesh      0.01   10000  avgt   20   54,164 ?  5,825  ms/op
BenchmarkSearchers.triangulationMesh      0.01  100000  avgt   20  602,050 ?  9,547  ms/op

BenchmarkSearchers.triangulationMesh      0.05    1000  avgt   20    3,203 ?  0,409  ms/op
BenchmarkSearchers.triangulationMesh      0.05   10000  avgt   20   42,035 ?  5,272  ms/op
BenchmarkSearchers.triangulationMesh      0.05  100000  avgt   20  478,631 ? 14,474  ms/op

BenchmarkSearchers.triangulationMesh       0.1    1000  avgt   20     2,984 ?    0,441  ms/op
BenchmarkSearchers.triangulationMesh       0.1   10000  avgt   20    41,165 ?    5,160  ms/op
BenchmarkSearchers.triangulationMesh       0.1  100000  avgt   20   458,667 ?   13,032  ms/op

BenchmarkSearchers.triangulationMesh       0.1    1000  avgt   20    3,046 ?  0,419  ms/op
BenchmarkSearchers.triangulationMesh       0.1   10000  avgt   20   41,635 ?  5,720  ms/op
BenchmarkSearchers.triangulationMesh       0.1  100000  avgt   20  468,414 ? 13,212  ms/op

BenchmarkSearchers.triangulationMesh       0.5    1000  avgt   20    2,991 ?  0,454  ms/op
BenchmarkSearchers.triangulationMesh       0.5   10000  avgt   20   40,801 ?  5,354  ms/op
BenchmarkSearchers.triangulationMesh       0.5  100000  avgt   20  468,743 ?  7,695  ms/op

BenchmarkSearchers.triangulationMesh       1.0    1000  avgt   20     3,165 ?    0,404  ms/op
BenchmarkSearchers.triangulationMesh       1.0   10000  avgt   20    42,475 ?    5,127  ms/op
BenchmarkSearchers.triangulationMesh       1.0  100000  avgt   20   470,605 ?   14,014  ms/op

BenchmarkSearchers.triangulationMesh       1.0    1000  avgt   20    3,015 ?  0,429  ms/op
BenchmarkSearchers.triangulationMesh       1.0   10000  avgt   20   42,104 ?  5,530  ms/op
BenchmarkSearchers.triangulationMesh       1.0  100000  avgt   20  492,328 ? 13,737  ms/op

BenchmarkSearchers.triangulationMesh      10.0    1000  avgt   20     3,678 ?    0,456  ms/op
BenchmarkSearchers.triangulationMesh      10.0   10000  avgt   20    55,708 ?    6,023  ms/op
BenchmarkSearchers.triangulationMesh      10.0  100000  avgt   20   779,701 ?   21,080  ms/op

BenchmarkSearchers.triangulationMesh     100.0    1000  avgt   20    24,111 ?    7,821  ms/op
BenchmarkSearchers.triangulationMesh     100.0   10000  avgt   20   456,582 ?  117,958  ms/op
BenchmarkSearchers.triangulationMesh     100.0  100000  avgt   20  9922,326 ? 1940,980  ms/op

*/

    @Param({
            "1000",
            "10000",
            "100000"
    })
    int size;

    @Param({
//            "0.001",
            "0.01",
            "0.05",
            "0.1",
            "0.5",
            "1.0",
//            "10.0",
//            "100.0"
    })
    String factor;

    @Setup
    public void prepare() {
        points = (Point[]) ResearchTriangulation.getRandomPoints(size).toArray();
    }

    Point[] points;

    @org.openjdk.jmh.annotations.Benchmark
    public int triangulationMesh() throws Exception {
        TriangulationDelaunay triangulation = new TriangulationDelaunay();
        TriangulationDelaunay.MINIMAL_POINTS_FOR_CLEANING = 3;
        FastSearcher.AMOUNT_SEARCHER_FACTOR = Double.parseDouble(factor);
        triangulation.run(points);
        return triangulation.getTriangles().size();
    }
}
