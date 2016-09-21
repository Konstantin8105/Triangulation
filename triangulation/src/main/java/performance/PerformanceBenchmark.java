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
@Measurement(iterations = 30)
//@Timeout(time = 20, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class PerformanceBenchmark {

    // java -jar target/microbenchmarks.jar performance.PerformanceBenchmark

    @Param({
            "Random",
            "Circle",
            "Line_in_line",
            "In_triangle"
    })
    String test;

    @Param({
            "10",
            "30000",
            "60000",
            "90000",
            "120000",
    })
    int size;

    @Setup
    public void prepare() {
        switch (test) {
            case "Random":
                points = (Point[]) ResearchTriangulation.getRandomPoints(size).toArray();
                break;
            case "Circle":
                points = (Point[]) ResearchTriangulation.getCirclePoints(size).toArray();
                break;
            case "Line_in_line":
                points = (Point[]) ResearchTriangulation.getLineOnLine(size).toArray();
                break;
            case "In_triangle":
                points = (Point[]) ResearchTriangulation.getInTriangles(size).toArray();
                break;
        }
    }

    Point[] points;

    @org.openjdk.jmh.annotations.Benchmark
    public int triangulationMesh() throws Exception {
        TriangulationDelaunay triangulation = new TriangulationDelaunay(points);
        return triangulation.getTriangles().size();
    }
}
