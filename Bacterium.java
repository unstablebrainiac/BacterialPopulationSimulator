package main;

import java.util.Random;

import static java.lang.Thread.sleep;
import static main.PopulationCalculator.POPULATION_LIMIT;
import static main.PopulationCalculator.SIMULAION_TIME_SCALE;
import static main.PopulationCalculator.population;

public class Bacterium {
    private static final long REPLICATION_TIME_MEAN = 20 * 60 * 1000 / SIMULAION_TIME_SCALE;
    private static final long REPLICATION_TIME_STANDARD_DEVIATION = 10 * 60 * 1000 / SIMULAION_TIME_SCALE;
    private static final long LIFESPAN_LOWER_BOUND = 0;
    private static final long LIFESPAN_UPPER_BOUND = 2 * REPLICATION_TIME_MEAN;

    private Thread life;

    public Bacterium() {
        life = new Thread(() -> {
            try {
                double lifespan = getLifespan();
                sleep((long) lifespan);
                if (population < POPULATION_LIMIT)
                    replicate();
                die();
                restInPeace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void live() {
        life.start();
        population++;
    }

    private void replicate() {
        Bacterium child1 = new Bacterium();
        Bacterium child2 = new Bacterium();
        child1.live();
        child2.live();
    }

    private void die() {
        life.stop();
        population--;
    }

    private void restInPeace() {
        life.destroy();
    }

    private double getLifespan() {
        Random random = new Random();
        double lifespan = random.nextGaussian() * REPLICATION_TIME_STANDARD_DEVIATION + REPLICATION_TIME_MEAN;
        if (lifespan < LIFESPAN_LOWER_BOUND) {
            lifespan = LIFESPAN_LOWER_BOUND;
        }
        if (lifespan > LIFESPAN_UPPER_BOUND) {
            lifespan = LIFESPAN_UPPER_BOUND;
        }
        return lifespan;
    }
}
