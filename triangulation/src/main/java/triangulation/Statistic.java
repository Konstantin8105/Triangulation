package triangulation;

import java.util.List;

public class Statistic {

    public Statistic() {
    }

    public void histogram(List<Double> list, double[] listLimit) {
        double min = list.get(0);
        double max = list.get(0);
        for (Double area : list) {
            min = Math.min(min, area);
            max = Math.max(max, area);
        }

        if (listLimit[0] > max) {
            int interval = 0;
            for (Double area : list) {
                if (area < listLimit[0])
                    interval++;
            }
            show(min,listLimit[0],interval);
        }

        for (int i = 1; i < listLimit.length; i++) {
            int interval = 0;
            for (Double area : list) {
                if (listLimit[i - 1] < area && area < listLimit[i])
                    interval++;
            }
            show(listLimit[i - 1],listLimit[i],interval);
        }

        if (listLimit[listLimit.length - 1] < max) {
            int interval = 0;
            for (Double area : list) {
                if (area > listLimit[listLimit.length - 1])
                    interval++;
            }
            show(listLimit[listLimit.length - 1],max,interval);
        }

        System.out.println("Minimal : " + String.format("%.3f",min));
        System.out.println("Maximal : " + String.format("%.3f",max));
        System.out.println();
    }

    private void show(double intervalMin, double intervalMax, int amount){
        System.out.println(String.format("%6.1f - %6.1f : %10d",intervalMin,intervalMax,amount));
    }
}
