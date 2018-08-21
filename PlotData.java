package main;

import java.util.ArrayList;
import java.util.List;

public class PlotData {
    private List<Double> xData;
    private List<Double> yData;
    private String xString;
    private String yString;

    public PlotData() {
        this.xData = new ArrayList<>();
        this.yData = new ArrayList<>();
        xString = "";
        yString = "";
    }

    public List<Double> getxData() {
        return xData;
    }

    public void setxData(List<Double> xData) {
        this.xData = xData;
    }

    public List<Double> getyData() {
        return yData;
    }

    public void setyData(List<Double> yData) {
        this.yData = yData;
    }

    public String getxString() {
        return "[" + xString.substring(0, xString.length() - 1) + "]";
    }

    public void setxString(String xString) {
        this.xString = xString;
    }

    public String getyString() {
        return "[" + yString.substring(0, yString.length() - 1) + "]";
    }

    public void setyString(String yString) {
        this.yString = yString;
    }

    public void updateData(double x, double y) {
        xData.add(x);
        yData.add(y);
        xString += (x - xData.get(0)) + ",";
        yString += y + ",";
    }
}
