import triangulation.Triangulation;

public class ResearchViewTest {

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 200;
        {
            Triangulation triangulation = new Triangulation(ResearchTest.getRandomPoints(AMOUNT_POINTS));
            MeshView meshView = new MeshView(triangulation.getMesh());
        }
        {
            Triangulation triangulation = new Triangulation(ResearchTest.getCirclePoints(AMOUNT_POINTS));
            MeshView meshView = new MeshView(triangulation.getMesh());
        }
    }


}
