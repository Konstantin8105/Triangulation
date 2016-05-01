package old.core;

import com.home.fgd.stack.triangulation.storage.ram.RamMesh;

public class Statistic {
    // TODO: 2/14/16 Add algoritm
    RamMesh dataMesh;
    int amount_points;

    public Statistic(RamMesh dataMesh) throws Exception {
        this.dataMesh = dataMesh;
        calculate();
    }

    private void calculate() throws Exception {
        amount_points = dataMesh.sizePoints();
    }

    @Override
    public String toString() {
        String str = new String();
        str += "Mesh quality statistics:" + "\n";
        str += "Amount_points = " + amount_points + "\n";
        return str;
    }

    /*
    EXAMPLE:
  Mesh quality statistics:

  Smallest area:       2.4708e-06   |  Largest area:         0.002675
  Shortest edge:        0.0018129   |  Longest edge:          0.10057
  Shortest altitude:    0.0011442   |  Largest aspect ratio:   4.6264

  Triangle aspect ratio histogram:
  1.1547 - 1.5       :       158    |     15 - 25         :         0
     1.5 - 2         :       366    |     25 - 50         :         0
       2 - 2.5       :       259    |     50 - 100        :         0
     2.5 - 3         :       178    |    100 - 300        :         0
       3 - 4         :       142    |    300 - 1000       :         0
       4 - 6         :         7    |   1000 - 10000      :         0
       6 - 10        :         0    |  10000 - 100000     :         0
      10 - 15        :         0    | 100000 -            :         0
  (Aspect ratio is longest edge divided by shortest altitude)

  Smallest angleBetween2Line:          15.024   |  Largest angleBetween2Line:           133.2

  Angle histogram:
      0 -  10 degrees:         0    |     90 - 100 degrees:       150
     10 -  20 degrees:         1    |    100 - 110 degrees:       151
     20 -  30 degrees:       379    |    110 - 120 degrees:        68
     30 -  40 degrees:       466    |    120 - 130 degrees:        20
     40 -  50 degrees:       439    |    130 - 140 degrees:         3
     50 -  60 degrees:       441    |    140 - 150 degrees:         0
     60 -  70 degrees:       367    |    150 - 160 degrees:         0
     70 -  80 degrees:       650    |    160 - 170 degrees:         0
     80 -  90 degrees:       195    |    170 - 180 degrees:         0

Memory allocation statistics:

  Maximum number of vertices: 811
  Maximum number of triangles: 1110
  Maximum number of subsegments: 522
  Maximum number of encroached subsegments: 1
  Maximum number of bad triangles: 531
  Maximum number of stacked triangle flips: 4
  Approximate heap memory use (bytes): 95440
     */
}
