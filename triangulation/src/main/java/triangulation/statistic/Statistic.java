package triangulation.statistic;

import java.util.List;


public class Statistic {

    public Statistic() {
    }

    private int max(int a, int b) {
        if (a < b) return b;
        return a;
    }

    public void histogram(List<Double> list, double[] listLimit) {
        int maxInterval = 0;
        {
            for (int i = 1; i < listLimit.length; i++) {
                int interval = 0;
                for (Double element : list) {
                    if (listLimit[i - 1] < element && element <= listLimit[i])
                        interval++;
                }
                maxInterval = max(maxInterval, interval);
            }
        }

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
            show(min, listLimit[0], interval,maxInterval);
        }

        for (int i = 1; i < listLimit.length; i++) {
            int interval = 0;
            for (Double element : list) {
                if (listLimit[i - 1] < element && element <= listLimit[i])
                    interval++;
            }
            show(listLimit[i - 1], listLimit[i], interval,maxInterval);
        }

        if (listLimit[listLimit.length - 1] < max) {
            int interval = 0;
            for (Double area : list) {
                if (area > listLimit[listLimit.length - 1])
                    interval++;
            }
            show(listLimit[listLimit.length - 1], max, interval,maxInterval);
        }

        System.out.println("Minimal : " + String.format("%.3f", min));
        System.out.println("Maximal : " + String.format("%.3f", max));
        System.out.println();
    }

    private void show(double intervalMin, double intervalMax, int amount, int maxInterval) {
        System.out.print(String.format("%6.1f - %6.1f : %10d : ", intervalMin, intervalMax, amount));
        int amountStars = (int)(15*(double)amount/(double)maxInterval);
        for (int i = 0; i < amountStars; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
