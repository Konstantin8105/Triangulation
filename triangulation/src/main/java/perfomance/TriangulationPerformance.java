package perfomance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 1, time = 32, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Timeout(time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 16, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class TriangulationPerformance {
    @Param({
//            "3", "5",
            "100", "200", "500",
            "1000", "2000", "5000",
            "10000", "20000", "50000",
            "100000", "200000", "500000"})
    int size;

    private static Random random = new Random();
    List<Point> points;

    @Setup
    public void prepare() {
        points = new ArrayList<>(size);
        for (int j = 0; j < points.size(); j++) {
            points.add(new Point((random.nextFloat()) * 600, (random.nextFloat()) * 600));
        }
    }

    @Benchmark
    public void randomMesh(Blackhole fox) throws Exception {
        Triangulation triangulation = new Triangulation(points);
    }
}
