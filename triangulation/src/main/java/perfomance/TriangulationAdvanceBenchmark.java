package perfomance;

import org.openjdk.jmh.annotations.*;
import research.ResearchTest;
import triangulationAdvance.Point;
import triangulationAdvance.TriangulationAdvance;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 10, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10)
@Timeout(time = 50, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)//.Thread)
public class TriangulationAdvanceBenchmark {
    @Param({
//            "10",
//            "100",
//            "1000",
//            "10000",
//            "20000", "30000", "40000",
            "50000",
            "100000", "200000", "500000",
    })
    int size;

    @Param({
            "Random",
            "Circle",
            "Line_in_line",
            "In_triangle"
    })
    String test;

    Point[] points;

    @Setup
    public void prepare() {
        switch (test) {
            case "Random":
                points = (Point[]) ResearchTest.getRandomPoints(size).toArray();
                break;
            case "Circle":
                points = (Point[]) ResearchTest.getCirclePoints(size).toArray();
                break;
            case "Line_in_line":
                points = (Point[]) ResearchTest.getLineOnLine(size).toArray();
                break;
            case "In_triangle":
                points = (Point[]) ResearchTest.getInTriangles(size).toArray();
                break;
        }
    }

    @Benchmark
    public int triangulationMesh() throws Exception {
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        return triangulation.getTriangles().size();
    }

}
