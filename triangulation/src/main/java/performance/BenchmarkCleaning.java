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
//@Timeout(time = 20, timeUnit = TimeUnit.MILLISECONDS)
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

//    Benchmark                            (factor)  (size)  Mode  Cnt      Score      Error  Units
//    BenchmarkCleaning.triangulationMesh     0.001    1000  avgt   20     30,778 ?    0,695  ms/op
//    BenchmarkCleaning.triangulationMesh     0.001   10000  avgt   20    645,043 ?   27,685  ms/op
//    BenchmarkCleaning.triangulationMesh     0.001  100000  avgt   20  22094,974 ? 5334,167  ms/op

//    BenchmarkCleaning.triangulationMesh      0.01    1000  avgt   20      8,087 ?    0,501  ms/op
//    BenchmarkCleaning.triangulationMesh      0.01   10000  avgt   20    128,920 ?    5,179  ms/op
//    BenchmarkCleaning.triangulationMesh      0.01  100000  avgt   20   4223,685 ?  618,294  ms/op

//    BenchmarkCleaning.triangulationMesh       0.1    1000  avgt   20      3,600 ?    0,420  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1   10000  avgt   20     44,154 ?    5,497  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1  100000  avgt   20   1038,346 ?   92,778  ms/op

//    BenchmarkCleaning.triangulationMesh       1.0    1000  avgt   20      2,874 ?    0,378  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0   10000  avgt   20     40,094 ?    5,362  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0  100000  avgt   20    477,653 ?    5,996  ms/op

//    BenchmarkCleaning.triangulationMesh      10.0    1000  avgt   20      2,970 ?    0,386  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0   10000  avgt   20     38,669 ?    5,007  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0  100000  avgt   20    481,892 ?   43,864  ms/op

//    BenchmarkCleaning.triangulationMesh     100.0    1000  avgt   20      2,885 ?    0,394  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0   10000  avgt   20     37,377 ?    5,037  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0  100000  avgt   20    586,222 ?  121,739  ms/op







//    Benchmark                            (factor)  (size)  Mode  Cnt     Score     Error  Units
//    BenchmarkCleaning.triangulationMesh       0.1    1000  avgt   20     3,574 ?   0,449  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1   10000  avgt   20    45,400 ?   5,385  ms/op
//    BenchmarkCleaning.triangulationMesh       0.1  100000  avgt   20  1056,232 ?  89,157  ms/op

//    BenchmarkCleaning.triangulationMesh       0.5    1000  avgt   20     3,000 ?   0,402  ms/op
//    BenchmarkCleaning.triangulationMesh       0.5   10000  avgt   20    38,714 ?   5,716  ms/op
//    BenchmarkCleaning.triangulationMesh       0.5  100000  avgt   20   540,289 ?  16,936  ms/op

//    BenchmarkCleaning.triangulationMesh       1.0    1000  avgt   20     2,971 ?   0,412  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0   10000  avgt   20    40,116 ?   5,261  ms/op
//    BenchmarkCleaning.triangulationMesh       1.0  100000  avgt   20   471,391 ?   3,751  ms/op

//    BenchmarkCleaning.triangulationMesh       2.0    1000  avgt   20     2,890 ?   0,397  ms/op
//    BenchmarkCleaning.triangulationMesh       2.0   10000  avgt   20    39,525 ?   5,217  ms/op
//    BenchmarkCleaning.triangulationMesh       2.0  100000  avgt   20   450,498 ?   7,663  ms/op

//    BenchmarkCleaning.triangulationMesh       3.0    1000  avgt   20     2,962 ?   0,398  ms/op
//    BenchmarkCleaning.triangulationMesh       3.0   10000  avgt   20    39,415 ?   4,887  ms/op
//    BenchmarkCleaning.triangulationMesh       3.0  100000  avgt   20   443,815 ?  12,778  ms/op

//    BenchmarkCleaning.triangulationMesh       4.0    1000  avgt   20     2,901 ?   0,397  ms/op
//    BenchmarkCleaning.triangulationMesh       4.0   10000  avgt   20    38,957 ?   4,991  ms/op
//    BenchmarkCleaning.triangulationMesh       4.0  100000  avgt   20   475,754 ?  16,196  ms/op

//    BenchmarkCleaning.triangulationMesh       5.0    1000  avgt   20     2,900 ?   0,409  ms/op
//    BenchmarkCleaning.triangulationMesh       5.0   10000  avgt   20    38,571 ?   5,049  ms/op
//    BenchmarkCleaning.triangulationMesh       5.0  100000  avgt   20   462,554 ?  24,002  ms/op

//    BenchmarkCleaning.triangulationMesh       6.0    1000  avgt   20     2,887 ?   0,389  ms/op
//    BenchmarkCleaning.triangulationMesh       6.0   10000  avgt   20    38,581 ?   5,242  ms/op
//    BenchmarkCleaning.triangulationMesh       6.0  100000  avgt   20   466,044 ?  19,446  ms/op

//    BenchmarkCleaning.triangulationMesh       7.0    1000  avgt   20     2,901 ?   0,403  ms/op
//    BenchmarkCleaning.triangulationMesh       7.0   10000  avgt   20    37,972 ?   5,300  ms/op
//    BenchmarkCleaning.triangulationMesh       7.0  100000  avgt   20   466,730 ?  20,156  ms/op

//    BenchmarkCleaning.triangulationMesh       8.0    1000  avgt   20     3,022 ?   0,400  ms/op
//    BenchmarkCleaning.triangulationMesh       8.0   10000  avgt   20    38,430 ?   5,191  ms/op
//    BenchmarkCleaning.triangulationMesh       8.0  100000  avgt   20   474,123 ?  28,910  ms/op

//    BenchmarkCleaning.triangulationMesh       9.0    1000  avgt   20     2,916 ?   0,401  ms/op
//    BenchmarkCleaning.triangulationMesh       9.0   10000  avgt   20    38,680 ?   4,991  ms/op
//    BenchmarkCleaning.triangulationMesh       9.0  100000  avgt   20   492,157 ?  51,714  ms/op

//    BenchmarkCleaning.triangulationMesh      10.0    1000  avgt   20     2,888 ?   0,387  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0   10000  avgt   20    38,862 ?   5,137  ms/op
//    BenchmarkCleaning.triangulationMesh      10.0  100000  avgt   20   531,658 ?  47,603  ms/op

//    BenchmarkCleaning.triangulationMesh     100.0    1000  avgt   20     3,042 ?   0,449  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0   10000  avgt   20    37,747 ?   5,086  ms/op
//    BenchmarkCleaning.triangulationMesh     100.0  100000  avgt   20   579,278 ? 107,180  ms/op
/*
    # Parameters: (factor = 0.1, size = 10000)
    Result "triangulationMesh":
            46,133 ?(99.9%) 5,590 ms/op [Average]
            (min, avg, max) = (40,987, 46,133, 59,900), stdev = 6,437
    CI (99.9%): [40,543, 51,723] (assumes normal distribution)

# Parameters: (factor = 1.0, size = 10000)
Result "triangulationMesh":
  42,773 ?(99.9%) 5,535 ms/op [Average]
  (min, avg, max) = (36,464, 42,773, 53,770), stdev = 6,374
  CI (99.9%): [37,239, 48,308] (assumes normal distribution)

# Parameters: (factor = 2.0, size = 10000)
Result "triangulationMesh":
  41,022 ?(99.9%) 5,178 ms/op [Average]
  (min, avg, max) = (34,845, 41,022, 51,608), stdev = 5,963
  CI (99.9%): [35,845, 46,200] (assumes normal distribution)




  */
    @Param({
//            "1000",
            "10000",
//            "100000",
//            "1000000",
    })
    int size;

    @Param({
//            "0.001",
//            "0.01",
            "0.1",
//            "0.5",
            "1.0",
            "2.0",
            "2.2",
            "2.4",
            "2.6",
            "2.8",
            "3.0",
            "4.0",
//            "5.0",
//            "6.0",
//            "7.0",
//            "8.0",
//            "9.0",
            "10.0",
//            "100.0"
    })
    String factor;


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
