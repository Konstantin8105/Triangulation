package perfomance;

import org.openjdk.jmh.annotations.*;
import research.ResearchTest;
import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
@Timeout(time = 50, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)//.Thread)
public class TriangulationBenchmark {
    @Param({
            "3", "5",
            "10", "20", "50"//,
//            "100", "200", "500",
//            "1000", "2000", "5000"
    })
    int size;

    @Param({
            "Random",
            "Circle",
            "Line_in_line",
            "In_triangle"
    })
    String test;

    List<Point> points;

    @Setup
    public void prepare() {
        switch (test) {
            case "Random":
                points = ResearchTest.getRandomPoints(size);
                break;
            case "Circle":
                points = ResearchTest.getCirclePoints(size);
                break;
            case "Line_in_line":
                points = ResearchTest.getLineOnLine(size);
                break;
            case "In_triangle":
                points = ResearchTest.getInTriangles(size);
                break;
        }
    }

    @Benchmark
    public int triangulationMesh() throws Exception {
        Triangulation triangulation = new Triangulation(points);
        return triangulation.getMesh().sizeTriangles();
    }

}
