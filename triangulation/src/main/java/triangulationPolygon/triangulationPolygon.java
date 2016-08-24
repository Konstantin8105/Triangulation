package triangulationPolygon;


/*
import un.api.Sorter;
import un.api.array.Arrays;
import un.api.collection.ArraySequence;
import un.api.collection.Collections;
import un.api.collection.Sequence;
import un.impl.geometry.Geometries;
import un.impl.geometry.operation.Nearest;
import un.api.geometry.operation.OperationException;
import un.impl.geometry.path.PathIterator;
import un.impl.geometry.s2d.Geometry2D;
import un.impl.geometry.s2d.Polygon;
import un.impl.geometry.s2d.Polyline;
import un.impl.geometry.s2d.SingularGeometry2D;
import un.impl.math.DefaultTuple;
import un.api.math.Tuple;
 */

public class triangulationPolygon {


//    https://en.wikipedia.org/wiki/Polygon_triangulation



// http://grafika.me/node/459
//    void triangulate() //триангуляция
//    {
//        int trainPos = 0; //
//        int leftPoints = points.Length; //сколько осталось рассмотреть вершин
//
//        //текущие вершины рассматриваемого треугольника
//        int ai = findNextNotTaken(0);
//        int bi = findNextNotTaken(ai + 1);
//        int ci = findNextNotTaken(bi + 1);
//
//        int count = 0; //количество шагов
//
//        while (leftPoints > 3) //пока не остался один треугольник
//        {
//            if (isLeft(points[ai], points[bi], points[ci]) && canBuildTriangle(ai, bi, ci)) //если можно построить треугольник
//            {
//                triangles[trainPos++] = new Triangle(points[ai], points[bi], points[ci]); //новый треугольник
//                taken[bi] = true; //исключаем вершину b
//                leftPoints--;
//                bi = ci;
//                ci = findNextNotTaken(ci + 1); //берем следующую вершину
//            }
//            else { //берем следующие три вершины
//                ai = findNextNotTaken(ai + 1);
//                bi = findNextNotTaken(ai + 1);
//                ci = findNextNotTaken(bi + 1);
//            }
//
//            if (count > points.Length * points.Length)
//            { //если по какой-либо причине (например, многоугольник задан по часовой стрелке) триангуляцию провести невозможно, выходим
//                triangles = null;
//                break;
//            }
//
//            count++;
//        }
//
//        if (triangles != null) //если триангуляция была проведена успешно
//            triangles[trainPos] = new Triangle(points[ai], points[bi], points[ci]);
//    }
//
//    private int findNextNotTaken(int startPos) //найти следущую нерассмотренную вершину
//    {
//        startPos %= points.Length;
//        if (!taken[startPos])
//            return startPos;
//
//        int i = (startPos + 1) % points.Length;
//        while (i != startPos)
//        {
//            if (!taken[i])
//                return i;
//            i = (i + 1) % points.Length;
//        }
//
//        return -1;
//    }
//
//    private bool isLeft(PointF a, PointF b, PointF c) //левая ли тройка векторов
//    {
//        float abX = b.X - a.X;
//        float abY = b.Y - a.Y;
//        float acX = c.X - a.X;
//        float acY = c.Y - a.Y;
//
//        return abX * acY - acX * abY < 0;
//    }
//
//    private bool isPointInside(PointF a, PointF b, PointF c, PointF p) //находится ли точка p внутри треугольника abc
//    {
//        float ab = (a.X - p.X) * (b.Y - a.Y) - (b.X - a.X) * (a.Y - p.Y);
//        float bc = (b.X - p.X) * (c.Y - b.Y) - (c.X - b.X) * (b.Y - p.Y);
//        float ca = (c.X - p.X) * (a.Y - c.Y) - (a.X - c.X) * (c.Y - p.Y);
//
//        return (ab >= 0 && bc >= 0 && ca >= 0) || (ab <= 0 && bc <= 0 && ca <= 0);
//    }
//
//    private bool canBuildTriangle(int ai, int bi, int ci) //false - если внутри есть вершина
//    {
//        for (int i = 0; i < points.Length; i++) //рассмотрим все вершины многоугольника
//            if (i != ai && i != bi && i != ci) //кроме троих вершин текущего треугольника
//                if (isPointInside(points[ai], points[bi], points[ci], points[i]))
//                    return false;
//        return true;
//    }
//
//















//
// http://grafika.me/node/473
//
//    vector <List> List :: triangulation () {
//        //массив, c координатами треугольников, образующими триангуляцию
//        vector <List> triangles, empty(0, 0); //empty будет возвращен в случае ошибки
//        List *l; //список координат треугольника
//        //берем три последовательно расположенные вершины
//        Node *first = cur, *second = cur->next, *third = cur->next->next;
//        //определяем направление обхода(по часовой стрелке/против часовой стрелки)
//        type cond = direction();
//        double prod;
//        if ( cond != error ) {
//            while (size > 3) {
//                prod = cross_prod(first, second, third);
//			/*вторая вершина должна лежать левее прямой, соединяющей first и third, если вершины
//			заданы по часовой стрелке и правее прямой, если против часовой стрелки*/
//                if ( ( cond == clockwise && prod < 0 ) || ( cond == count_clockwise && prod > 0 ) ) {
//                    //внутри треугольника нет других вершин многоугольника
//                    if ( !is_in_triangle ( first, second, third ) ) {
//                        l = new List;
//                        l->insert(first->x, first->y);
//                        l->insert(second->x, second->y);
//                        l->insert(third->x, third->y);
//                        triangles.push_back(*l);
//                        //исключаем вершину second из рассмотрения
//                        delete_node(second->x, second->y);
//                        delete l;
//                    }
//                    second = third;
//                    third = third->next;
//                }
//                else {
//                    first = second;
//                    second = third;
//                    third = third->next;
//                }
//            }
//            if (size == 3) { //добавляем последний треугольник
//                l = new List;
//                l->insert(first->x, first->y);
//                l->insert(second->x, second->y);
//                l->insert(third->x, third->y);
//                triangles.push_back(*l);
//                delete l;
//            }
//            return triangles;
//        }
//        return empty;
//    }












//
//    private static final Sorter X_SORTER = new Sorter() {
//        public int sort(Object first, Object second) {
//            final double m1 = ((Geometry2D)first).getBoundingBox().getMin(0);
//            final double m2 = ((Geometry2D)second).getBoundingBox().getMin(0);
//            double d = m2-m1;
//            return (d<0) ? -1 : (d>0) ? +1 : 0;
//        }
//    };
//    private static final class SimplePolygon{
//        private Polyline outter;
//        private final Sequence inners = new ArraySequence();
//        /**
//         * direction of the outter loop
//         */
//        private boolean outterIsClockwise;
//    }
//
//    private final Sequence triangles = new ArraySequence();
//
//    /**
//     * A path can be composed of multiple parts.
//     * Each part is a closed polyline with inner holes.
//     */
//    private final Sequence parts = new ArraySequence();
//
//    /** number of coordinates, may be inferior to coords length. */
//    private int nbCoords;
//    /** current coordinates to triangulate. */
//    private Tuple[] coords;
//    /**
//     * store the type of angle made by the coordinate
//     * avoid recalculate it each time.
//     * 0 : not calculated
//     * 1 : convex
//     * 2 : concave
//     */
//    private int[] coordType;
//
//    //current segment analyzed
//    private int indexPrevious;
//    private int index;
//    private int indexNext;
//    private Tuple t1; //previous point
//    private Tuple t2; //point at index
//    private Tuple t3; //next point
//
//    private void reset(){
//        //reset values
//        triangles.removeAll();
//        nbCoords = 0;
//        coords = null;
//        coordType = null;
//        parts.removeAll();
//    }
//
//    /**
//     *
//     * @param geometry geometry to triangulate
//     * @return Sequence of Tuple[] for each triangle.
//     */
//    public Sequence triangulate(Geometry2D geometry) throws OperationException{
//
//        if(geometry instanceof SingularGeometry2D){
//            geometry = ((SingularGeometry2D)geometry).toPolygon();
//        }
//
//        //start by collecting all points
//        if(geometry instanceof Polygon){
//            reset();
//
//            final SimplePolygon part = new SimplePolygon();
//            //copy collection to avoid modifications
//            part.outter = new Polyline(((Polygon)geometry).getExterior().getCoordinates().copy());
//
//            final Sequence inners = ((Polygon)geometry).getHoles();
//            if(inners != null){
//                for(int i=0,n=inners.getSize();i<n;i++){
//                    Polyline inner = (Polyline) inners.get(i);
//                    part.inners.add(new Polyline(inner.getCoordinates().copy()));
//                }
//            }
//
//            run(part);
//
//            return triangles;
//
//        }else{
//            final PathIterator ite = geometry.createPathIterator();
//            return triangulate(ite);
//        }
//
//    }
//
//    /**
//     *
//     * @param PathIterator geometry to triangulate
//     * @return Sequence of Tuple[] for each triangle.
//     */
//    public Sequence triangulate(PathIterator ite) throws OperationException{
//        reset();
//
//        //collect all loops and rebuild simple polygons
//        //we expect to find parts ordered correctly :
//        // outter1,inner1,inner2,inner3,outter2,inner1,inner2...
//        SimplePolygon current = null;
//        while(ite.next()){
//            final Polyline loop = nextLoop(ite);
//            if(loop == null) continue;
//
//            final boolean clockwise = Geometries.isClockWise(loop.getCoordinates());
//
//            if(current == null){
//                current = new SimplePolygon();
//                current.outterIsClockwise = clockwise;
//            }
//
//            if(current.outterIsClockwise == clockwise){
//                //this is an outter loop
//                if(current.outter != null){
//                    //outter is already defined, switch to a new simple polygon
//                    parts.add(current);
//                    current = new SimplePolygon();
//                    current.outterIsClockwise = clockwise;
//                }
//                current.outter = loop;
//            }else{
//                //this is an inner loop
//                current.inners.add(loop);
//            }
//        }
//
//        if(current!=null) parts.add(current);
//
//        for(int i=0,n=parts.getSize();i<n;i++){
//            run((SimplePolygon)parts.get(i));
//        }
//
//        return triangles;
//    }
//
//    private Polyline nextLoop(PathIterator ite){
//        //start by collecting all points
//        final Sequence coords = new ArraySequence();
//        final Tuple start = new DefaultTuple(2);
//        do{
//            final int type = ite.getType();
//            if(type==PathIterator.TYPE_MOVE_TO){
//                ite.getPosition(start);
//            }else if(type==PathIterator.TYPE_CLOSE){
//                coords.add(start);
//                break;
//            }
//            coords.add(ite.getPosition(new DefaultTuple(2)));
//        }while(ite.next());
//
//        if(coords.isEmpty()) return null;
//
//        //ensure last point == first point
//        if(!coords.get(0).equals(coords.get(coords.getSize()-1))){
//            coords.add( ((Tuple)coords.get(0)).copy() );
//        }
//
//        return new Polyline(Geometries.toTupleBuffer(coords));
//    }
//
//
//    private void run(SimplePolygon part) throws OperationException{
//
//        //build a single geometry linking inner holes.
//        final Sequence borderCoords = new ArraySequence();
//        borderCoords.addAll(Geometries.toTupleArray(part.outter.getCoordinates()));
//        //sort inner holes by minimum x value
//        orderHoles(part);
//
//        //attach holes to the main geometry
//        for(int i=0,n=part.inners.getSize();i<n;i++){
//            //we must find the minimum x coordinate in the inner loop
//            final Sequence loop = new ArraySequence( Geometries.toTupleArray( ((Polyline)part.inners.get(i)).getCoordinates()));
//            int index = 0;
//            Tuple min = (Tuple) loop.get(index);
//            for(int k=1,p=loop.getSize();k<p;k++){
//                Tuple candidate = (Tuple) loop.get(1);
//                if(candidate.getX() < min.getX()){
//                    min = candidate;
//                    index = k;
//                }
//            }
//
//            //now find the closest point on the outter loop
//            final Sequence line2 = new ArraySequence();
//            line2.add(min);line2.add(min);
//            final double[] buffer1 = new double[2];
//            final double[] buffer2 = new double[2];
//            final double[] ratio = new double[2];
//            final int[] offset = new int[2];
//            Nearest.nearest(borderCoords, buffer1, line2, buffer2, ratio, offset, 0.000000001);
//
//            //make a cut from outter loop to inner loop
//            int insertIndex = offset[0]+1;
//            borderCoords.add(insertIndex, new DefaultTuple(buffer1));
//            insertIndex++;
//            //remove the duplicateion inner loop end point
//            loop.remove(loop.getSize()-1);
//            for(int k=index,p=loop.getSize();k<p;k++,insertIndex++){
//                borderCoords.add(insertIndex,loop.get(k));
//            }
//            for(int k=0;k<=index;k++,insertIndex++){
//                borderCoords.add(insertIndex,loop.get(k));
//            }
//            borderCoords.add(insertIndex, new DefaultTuple(buffer1));
//
//        }
//
//        //remove any neighor points overlaping
//        Tuple t = (Tuple) borderCoords.get(0);
//        for(int i=1,n=borderCoords.getSize();i<n;i++){
//            Tuple candidate = (Tuple) borderCoords.get(i);
//            if(candidate.equals(t)){
//                borderCoords.remove(i);
//                i--;
//                n--;
//            }else{
//                t = candidate;
//            }
//        }
//
//
//
//        final boolean clockwise = Geometries.isClockWise(borderCoords);
//
//        nbCoords = borderCoords.getSize();
//        coords = new Tuple[nbCoords];
//        coordType = new int[nbCoords];
//        Collections.copy(borderCoords, coords, 0);
//
//        //flip coordinates if not clockwise
//        if(!clockwise){
//            Arrays.reverse(coords, 0, coords.length);
//        }
//
//        //now cut ears progressively
//        int nbcut = 0;
//        while(nbCoords > 3){
//            nbcut = 0;
//            for(index=0;index<nbCoords-2 && nbCoords>3;){
//                indexPrevious = (index==0) ? nbCoords-2 : index-1 ;
//                indexNext = index+1;
//
//                t1 = coords[indexPrevious];
//                t2 = coords[index];
//                t3 = coords[indexNext];
//
//                if(isConvex() && isEar()){
//                    //reset angle type, we remove a point so it might change
//                    coordType[indexPrevious]=0;
//                    coordType[indexNext]=0;
//
//                    triangles.add(new Tuple[]{t1,t2,t3});
//                    Arrays.removeWithin(coords, index);
//                    Arrays.removeWithin(coordType, index);
//                    nbCoords--;
//                    nbcut++;
//                    //we do not increment since we have remove the point
//                }else{
//                    index++;
//                }
//            }
//
//            if(nbcut == 0){
//                //this should not happen if the geometry is correct
//                //System.out.println("Triangulation failed. no ear to cut.");
//                return;
//            }
//        }
//    }
//
//    /**
//     * Sort inner holes by minimum x value.
//     */
//    private void orderHoles(SimplePolygon part){
//        Collections.sort(part.inners, X_SORTER);
//    }
//
//
//    /**
//     * @return true if  currentsegment is convex
//     */
//    private boolean isConvex(){
//        if(coordType[index]==0){
//            //calculate angle type
//            final double side = Geometries.lineSide(t1.getValues(), t3.getValues(), t2.getValues());
//            coordType[index] = side>0 ? 1 : 2;
//        }
//        return coordType[index] == 1;
//    }
//
//    /**
//     * @return true if segment is convex
//     */
//    private boolean isConvex(int idx){
//        if(coordType[idx]==0){
//            //calculate angle type
//            final Tuple s1 = coords[(idx==0) ? (nbCoords-2) : (idx-1)];
//            final Tuple s2 = coords[idx];
//            final Tuple s3 = coords[idx+1];
//            final double side = Geometries.lineSide(s1.getValues(), s3.getValues(), s2.getValues());
//            coordType[idx] = side>0 ? 1 : 2;
//        }
//        return coordType[idx] == 1;
//    }
//
//    /**
//     * Check this segment triangle is an ear.
//     * Does not contain any other point.
//     * @return
//     * @throws OperationException
//     */
//    private boolean isEar() throws OperationException {
//        final double[] a = t1.getValues();
//        final double[] b = t2.getValues();
//        final double[] c = t3.getValues();
//
//        for(int i=0; i<nbCoords-1; i++){
//            //test only concave points
//            if (!isConvex(i)){
//                //check it's not one of the current segment
//                if(i!=index && i!=indexNext && i!=indexPrevious){
//                    //check if it's in the segment triangle
//                    if(Geometries.inTriangle(a, b, c, coords[i].getValues())){
//                        return false;
//                    }
//                }
//            }
//        }
//
//        return true;
//    }

}
