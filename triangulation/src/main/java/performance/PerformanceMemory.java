package performance;

import org.openjdk.jmh.annotations.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2)
@Warmup(iterations = 20, time = 500, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 20, time = 500, timeUnit = TimeUnit.NANOSECONDS)
//@Timeout(time = 20, timeUnit = TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class PerformanceMemory {

    // java -jar target/microbenchmarks.jar performance.PerformanceMemory

//    Benchmark                (size)  Mode  Cnt     Score     Error  Units
//    PerformanceMemory.add        10  avgt  200   198.528 ± 110.448  ns/op
//    PerformanceMemory.add       100  avgt  200   214.882 ± 204.952  ns/op
//    PerformanceMemory.add      1000  avgt  200   245.669 ±  21.567  ns/op
//    PerformanceMemory.add     10000  avgt  200  1315.645 ± 227.183  ns/op
//    PerformanceMemory.add    100000  avgt  200  3480.400 ± 241.004  ns/op
//    PerformanceMemory.add   1000000  avgt  200  5271.460 ± 246.289  ns/op
//    PerformanceMemory.add  10000000  avgt  200  5436.585 ± 351.245  ns/op
/*
Benchmark               (size)  Mode  Cnt     Score     Error  Units
PerformanceMemory.add       10  avgt   40   109,502 ?  14,626  ns/op
PerformanceMemory.add      100  avgt   40   114,240 ?  16,232  ns/op
PerformanceMemory.add     1000  avgt   40   267,216 ?  92,153  ns/op
PerformanceMemory.add    10000  avgt   40   874,353 ? 285,528  ns/op
PerformanceMemory.add   100000  avgt   40  1569,750 ? 269,754  ns/op
PerformanceMemory.add  1000000  avgt   40  4739,075 ? 632,132  ns/op

Benchmark               (size)  Mode  Cnt     Score     Error  Units
PerformanceMemory.add       10  avgt   40   100,414 ?   6,820  ns/op
PerformanceMemory.add       50  avgt   40   100,885 ?   7,075  ns/op
PerformanceMemory.add      100  avgt   40   118,015 ?  31,231  ns/op
PerformanceMemory.add      500  avgt   40   164,264 ?  34,618  ns/op
PerformanceMemory.add     1000  avgt   40   209,885 ?  44,666  ns/op
PerformanceMemory.add     5000  avgt   40   667,790 ? 258,809  ns/op
PerformanceMemory.add    10000  avgt   40   609,873 ? 121,036  ns/op
PerformanceMemory.add    50000  avgt   40  1011,100 ? 171,163  ns/op
PerformanceMemory.add   100000  avgt   40  1377,300 ? 239,579  ns/op
PerformanceMemory.add   500000  avgt   40  4112,800 ? 424,258  ns/op
PerformanceMemory.add  1000000  avgt   40  4769,300 ? 866,137  ns/op
 */
    @Param({
            "10",
            "50",
            "100",
            "500",
            "1000",
            "5000",
            "10000",
            "50000",
            "100000",
            "500000",
            "1000000",
    })
    int size;
    final List<Integer> list = new LinkedList<>();

    @Setup(Level.Invocation)
    public void prepare() {
        list.clear();
        for (int i = 0; i < size; i++) {
            list.add(12);
        }
    }

    @Benchmark
    public boolean add(){
        return list.add(12);
    }
}
