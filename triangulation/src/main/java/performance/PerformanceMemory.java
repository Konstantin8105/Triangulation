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

    @Param({
            "10",
            "100",
            "1000",
            "10000",
            "100000",
            "1000000",
    })
    int size;
    List<Integer> list = new LinkedList<>();

    @Setup(Level.Invocation)
    public void prepare() {
        list.clear();
        for (int i = 0; i < size; i++) {
            list.add(12);
        }
    }

    @Benchmark
    public boolean add() throws Exception {
        return list.add(12);
    }
}
