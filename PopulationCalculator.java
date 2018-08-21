package main;

import com.mathworks.engine.MatlabEngine;

import java.util.concurrent.ExecutionException;

import static com.mathworks.engine.MatlabEngine.startMatlab;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

public class PopulationCalculator {
    public static final int POPULATION_LIMIT = 1000;
    public static final int SIMULAION_TIME_SCALE = 1000;

    public static volatile int population = 0;
    private static PlotData plotData = new PlotData();
    private static MatlabEngine matlabEngine;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        matlabEngine = startMatlab();
        Bacterium originalBacterium = new Bacterium();
        originalBacterium.live();
        while (population < POPULATION_LIMIT) {
            plotData.updateData(currentTimeMillis(), population);
            sleep(100);
        }
        fit();
        plot();
    }

    private static void fit() throws ExecutionException, InterruptedException {
        matlabEngine.eval("X = " + plotData.getxString() + " * " + SIMULAION_TIME_SCALE + " / 60 / 1000;");
        matlabEngine.eval("Y = " + plotData.getyString() + ";");
        matlabEngine.eval("logY = log(Y);");
        matlabEngine.eval("[P,~] = polyfit(X,logY,1);");
        matlabEngine.eval("logYfit = polyval(P,X);");
        matlabEngine.eval("Yfit = exp(logYfit);");
        matlabEngine.eval("k = exp(P(2))");
        matlabEngine.eval("l = exp(P(1))");
    }

    public static void plot() throws InterruptedException, ExecutionException {
        matlabEngine.eval("hold on;");
        matlabEngine.eval("plot(X,Yfit);");
        matlabEngine.eval("title('Bacterial Population in Growth Phase')");
        matlabEngine.eval("ylabel('Population')");
        matlabEngine.eval("xlabel('Time (minutes)')");
        matlabEngine.eval("print('populationPlot1','-djpeg');");
        matlabEngine.eval("plot(X,Y,'o');");
        matlabEngine.eval("legend('Exponential Fit', 'Simulation data', 'Location', 'northwest')");
        matlabEngine.eval("print('populationPlot2','-djpeg');");
        sleep(20000);
    }
}
