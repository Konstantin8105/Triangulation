package performance;

import org.openjdk.jmh.annotations.*;
import research.ResearchTriangulation;
import triangulation.elements.Point;
import triangulation.TriangulationDelaunay;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 10, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20)
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

Benchmark                            (factor)  (size)  Mode  Cnt   Score    Error  Units
BenchmarkCleaning.triangulationMesh       0.1   10000  avgt   20  46,138 ?  5,899  ms/op
BenchmarkCleaning.triangulationMesh       1.0   10000  avgt   20  46,379 ? 15,840  ms/op
BenchmarkCleaning.triangulationMesh       2.0   10000  avgt   20  44,213 ?  7,440  ms/op
BenchmarkCleaning.triangulationMesh       2.2   10000  avgt   20  40,610 ?  5,256  ms/op
BenchmarkCleaning.triangulationMesh       2.4   10000  avgt   20  43,445 ?  8,661  ms/op
BenchmarkCleaning.triangulationMesh       2.6   10000  avgt   20  39,800 ?  5,354  ms/op
BenchmarkCleaning.triangulationMesh       2.8   10000  avgt   20  40,492 ?  4,867  ms/op
BenchmarkCleaning.triangulationMesh       3.0   10000  avgt   20  41,966 ?  6,790  ms/op
BenchmarkCleaning.triangulationMesh       4.0   10000  avgt   20  40,562 ?  5,096  ms/op
BenchmarkCleaning.triangulationMesh      10.0   10000  avgt   20  39,257 ?  6,107  ms/op

Benchmark                            (factor)  (size)  Mode  Cnt    Score    Error  Units
BenchmarkCleaning.triangulationMesh       0.1   30000  avgt   40  208,653 ? 13,002  ms/op
BenchmarkCleaning.triangulationMesh       1.0   30000  avgt   40  138,751 ?  7,085  ms/op
BenchmarkCleaning.triangulationMesh       2.0   30000  avgt   40  130,696 ?  6,916  ms/op
BenchmarkCleaning.triangulationMesh       2.2   30000  avgt   40  125,426 ?  6,386  ms/op
BenchmarkCleaning.triangulationMesh       2.4   30000  avgt   40  127,995 ?  7,347  ms/op
BenchmarkCleaning.triangulationMesh       2.6   30000  avgt   40  128,486 ?  6,828  ms/op
BenchmarkCleaning.triangulationMesh       2.8   30000  avgt   40  125,136 ?  6,986  ms/op
BenchmarkCleaning.triangulationMesh       3.0   30000  avgt   40  124,602 ?  6,871  ms/op
BenchmarkCleaning.triangulationMesh       4.0   30000  avgt   40  123,566 ?  6,499  ms/op
BenchmarkCleaning.triangulationMesh      10.0   30000  avgt   40  129,366 ?  7,202  ms/op

Benchmark                            (factor)  (size)  Mode  Cnt     Score    Error  Units
BenchmarkCleaning.triangulationMesh       0.1  100000  avgt   40  1071,889 ? 56,296  ms/op
BenchmarkCleaning.triangulationMesh       1.0  100000  avgt   40   503,116 ? 12,181  ms/op
BenchmarkCleaning.triangulationMesh       2.0  100000  avgt   40   477,607 ?  7,994  ms/op
BenchmarkCleaning.triangulationMesh       2.2  100000  avgt   40   466,779 ?  9,081  ms/op
BenchmarkCleaning.triangulationMesh       2.4  100000  avgt   40   449,071 ?  5,096  ms/op
BenchmarkCleaning.triangulationMesh       2.6  100000  avgt   40   452,015 ?  5,155  ms/op
BenchmarkCleaning.triangulationMesh       2.8  100000  avgt   40   473,128 ?  4,473  ms/op
BenchmarkCleaning.triangulationMesh       3.0  100000  avgt   40   465,667 ?  9,479  ms/op
BenchmarkCleaning.triangulationMesh       4.0  100000  avgt   40   461,853 ? 12,198  ms/op
BenchmarkCleaning.triangulationMesh      10.0  100000  avgt   40   507,926 ? 30,328  ms/op
BenchmarkCleaning.triangulationMesh      20.0  100000  avgt   40   530,102 ? 25,511  ms/op
BenchmarkCleaning.triangulationMesh      30.0  100000  avgt   40   513,386 ? 46,840  ms/op

Benchmark                            (factor)  (size)  Mode  Cnt     Score     Error  Units
BenchmarkCleaning.triangulationMesh       0.1  500000  avgt   40  4609,569 ?  86,783  ms/op
BenchmarkCleaning.triangulationMesh       0.5  500000  avgt   40  3272,425 ? 132,258  ms/op
BenchmarkCleaning.triangulationMesh       1.0  500000  avgt   40  3196,638 ? 164,217  ms/op

Benchmark                            (factor)   (size)  Mode  Cnt      Score      Error  Units
BenchmarkCleaning.triangulationMesh       0.1  1000000  avgt   40  9445,725 ? 262,377  ms/op
BenchmarkCleaning.triangulationMesh       0.5  1000000  avgt   40  7756,311 ? 308,443  ms/op
BenchmarkCleaning.triangulationMesh       1.0  1000000  avgt   40  7578,386 ? 271,732  ms/op
BenchmarkCleaning.triangulationMesh       1.0  1000000  avgt   40   7771,651 ?  394,038  ms/op
BenchmarkCleaning.triangulationMesh       2.0  1000000  avgt   40   8986,883 ?  343,261  ms/op
BenchmarkCleaning.triangulationMesh       2.2  1000000  avgt   40   9251,798 ?  519,798  ms/op
BenchmarkCleaning.triangulationMesh       2.4  1000000  avgt   40   9370,443 ?  311,326  ms/op
BenchmarkCleaning.triangulationMesh       2.6  1000000  avgt   40   9616,804 ?  355,762  ms/op
BenchmarkCleaning.triangulationMesh       2.8  1000000  avgt   40   9701,925 ?  420,566  ms/op
BenchmarkCleaning.triangulationMesh       3.0  1000000  avgt   40   9774,818 ?  324,036  ms/op
BenchmarkCleaning.triangulationMesh       4.0  1000000  avgt   40  10634,840 ?  450,099  ms/op
BenchmarkCleaning.triangulationMesh      10.0  1000000  avgt   40  11438,163 ?  194,758  ms/op
BenchmarkCleaning.triangulationMesh      20.0  1000000  avgt   40  15454,797 ? 1138,569  ms/op
BenchmarkCleaning.triangulationMesh      30.0  1000000  avgt   40  12651,203 ? 1210,017  ms/op

*/
    @Param({
//            "1000",
//            "10000",
//            "30000",
//            "100000",
            "500000",
//            "1000000",
    })
    int size;

    @Param({
//            "0.001",
//            "0.01",
//            "0.1",
//            "0.5",
//            "1.0",
            "1.5",
            "2.0",
//            "2.2",
            "2.4",
//            "2.6",
//            "2.8",
            "3.0",
//            "4.0",
//            "5.0",
//            "6.0",
//            "7.0",
//            "8.0",
//            "9.0",
//            "10.0",
//            "20.0",
//            "30.0",
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
        TriangulationDelaunay.AMOUNT_CLEANING_FACTOR_TRIANGLE_STRUCTURE = Double.parseDouble(factor);
        triangulation.run(points);
        return triangulation.getTriangles().size();
    }
}
