package com.model.Others.aga;

import com.entity.Instance;
import com.entity.Item;
import com.entity.Solution;

import java.util.*;

public class AGA {

    //Maximum number of iterations
    private int MAX_GEN;
   // Population information
    private int popSize;
    //Number of mutations
    private double variationExchangeCount;
    //The number of times the optimal solution is replicated (the optimal individuals of the population are selected, then replicated several times, and the optimal individuals are duplicated multiple times and stored in a new set)
    int cloneNumOfBestIndividual;
    //Gene mutation probability
    private double mutationRate;
    //Gene crossover probability
    private double crossoverRate;

    //The total fitness value of the population
    private double totalFitness;
    //The corresponding chromosomes (gene sequence, pathway sequence) for the best fitness
    private Genome bestGenome;
    //Place all population genetic information
    private List<Genome> population = new ArrayList<>();
    //Genetic information for a new generation of populations
    private List<Genome> newPopulation = new ArrayList<>();
    //Hereditary algebra (generations)
    private int t = 0;
    //The optimal number of iterations
    private int bestT = -1;
    //Random function object
    public Random random;
    //The cumulative probability of individual individuals
    double[] probabilitys;

    //The number of rectangle items
    int itemNum;
    //The width of the bin
    private double W;
    //The height of the bin
    private double H;
    //A rectangle items array
    Item[] items;
    //Is it rotatable or not
    private boolean isRotateEnable;

    /**
     * @param MAX_GEN number of iterations (increasing this value can steadily improve the solution quality, but will increase the solution time)
     * @param popSize population siz
     * @param  of times cloneNumOfBestIndividual replicates the optimal solution (generally less than 5% of the population number, select the optimal individual of the population, and then replicate several times, copy the optimal individual multiple times, and store it in a new set, which helps to maintain the good gene)
     * @param mutationRate gene mutation probability
     * @param crossoverRate gene cross probability
     * @param instance object
     * @param seed random number seed, if null is passed, the random number seed is not set, otherwise it is set according to the incoming seed to facilitate the reproduction of the result
     * @Description The constructor of the adaptive genetic algorithm
     */
    public AGA(int MAX_GEN, int popSize, int variationExchangeCount, int cloneNumOfBestIndividual, double mutationRate, double crossoverRate, Instance instance, Long seed) {
        this.MAX_GEN = MAX_GEN;
        this.popSize = popSize;
        this.variationExchangeCount = variationExchangeCount;
        this.cloneNumOfBestIndividual = cloneNumOfBestIndividual;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.W = instance.getW();
        this.H = instance.getH();
        this.isRotateEnable = instance.isRotateEnable();
        this.items = Item.copy(instance.getItemList().toArray(new Item[0]));
        this.random = seed == null ? new Random() : new Random(seed);
    }

    /**
     * @return Optimal loading result object Solution
     * @Description Adaptive Genetic algorithm main function
     */
    public Solution solve() {
        initVar();
        while (t < MAX_GEN) {
            popSize = population.size();
            //Genetic evolution
            evolution();
            //Update the population
            population = copyGenomeList(newPopulation);
            t++;
        }
        return bestGenome.getSolution();
    }

    /**
     * @Description Evolutionary function, normal cross-mutation
     */
    public void evolution() {
       // Update the cumulative probability and total fitness
        updateProbabilityAndTotalFitness();
        //Pick the most adapted individuals in a certain generation of the population
        selectBestGenomeAndJoinNext();
        //The round selection strategy randomly crosses and mutates the remaining scale-1 individuals
        while (newPopulation.size() < population.size()) {
            double r = random.nextDouble();
            for (int j = 0; j < probabilitys.length; j++) {
                if (compareDouble(r, probabilitys[j]) != 1) {
                    newPopulation.add(population.get(j));
                    break;
                }
            }
        }
        //cross
        if (compareDouble(random.nextDouble(), crossoverRate) != 1) {
            int r = random.nextInt(popSize);
            int k = random.nextInt(popSize);
            while (r == k) {
                r = random.nextInt(popSize);
            }
            cross(k, r);
        }
        // variation
        for (int i = 0; i < population.size(); i++) {
            if (compareDouble(random.nextDouble(), mutationRate) != 1) {
                variation(i);
            }
        }
    }

    /**
     * Index of @param K1 Gene 1
     * Index of @param K2 gene2
     * @Description Single-point mapping of gene 1 and gene 2
     */
    private void cross(int k1, int k2) {
        // Acquire the two genes to be crossed
        int[] genomeArray1 = newPopulation.get(k1).getGenomeArray();
        int[] genomeArray2 = newPopulation.get(k2).getGenomeArray();
       // Find the intersection location
        int crossIndex = random.nextInt(itemNum);
        //Gets the crossed fragment
        for (int i = 0; i <= crossIndex; i++) {
            int temp = genomeArray1[i];
            genomeArray1[i] = genomeArray2[i];
            genomeArray2[i] = temp;
        }
        // Find duplicate genes
        HashSet<Integer> set = new HashSet<>();
        // <index,value>
        HashMap<Integer, Integer> repeatMap = new HashMap<>();
        for (int i = 0; i < genomeArray1.length; i++) {
            if (!set.add(genomeArray1[i])) {
                repeatMap.put(i, genomeArray1[i]);
            }
        }
        set.clear();
        for (int i = 0; i < genomeArray2.length; i++) {
            if (!set.add(genomeArray2[i])) {
                for (int key : repeatMap.keySet()) {
                    genomeArray1[key] = genomeArray2[i];
                    genomeArray2[i] = repeatMap.get(key);
                    repeatMap.remove(key);
                    break;
                }
            }
        }
        // After crossing, the gene is put back into the individual, and the individual is put back into the population, and their adaptation values and path lengths are updated
        newPopulation.get(k1).setGenomeArray(genomeArray1);
        newPopulation.get(k1).updateFitnessAndSolution();
        newPopulation.get(k2).setGenomeArray(genomeArray2);
        newPopulation.get(k2).updateFitnessAndSolution();
    }

    /**
     * @param k Gene index to be mutated
     * @Description mutation (n times two pairs)
     */
    private void variation(int k) {
        Genome genome = newPopulation.get(k);
        int[] genomeArray = genome.getGenomeArray();
        for (int i = 0; i < variationExchangeCount; i++) {
            int r1 = random.nextInt(itemNum);
            int r2 = random.nextInt(itemNum);
            while (r1 == r2) {
                r2 = random.nextInt(itemNum);
            }
            //exchange
            int temp = genomeArray[r1];
            genomeArray[r1] = genomeArray[r2];
            genomeArray[r2] = temp;
        }
        //Place the mutated gene sequence back into the individual
        genome.setGenomeArray(genomeArray);
        //Update the adaptation value and distance length of the gene
        genome.updateFitnessAndSolution();
        //Return the mutated individual to the population
        newPopulation.set(k, genome);
    }

    /**
     * @Description initialization function to perform some initialization operations
     */
    public void initVar() {
        this.itemNum = this.items.length;
        this.population = new ArrayList<>();
        //Initialize the population information
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            sequence.add(i);
        }
        for (int i = 0; i < popSize; i++) {
            Collections.shuffle(sequence);
            int[] initSequence = new int[itemNum];
            for (int j = 0; j < sequence.size(); j++) {
                initSequence[j] = sequence.get(j);
            }
            Genome genome = new Genome(W, H, items, isRotateEnable, initSequence.clone());
            genome.updateFitnessAndSolution();
            population.add(genome);
        }
        bestGenome = copyGenome(population.get(0));
        for (int i = 1; i < popSize; i++) {
            Genome genome = population.get(i);
            if (bestGenome.getFitness() < genome.getFitness()) {
                bestGenome = copyGenome(genome);
            }
        }
        System.out.println("The initial solution is：" + bestGenome.getSolution().getRate());
    }

    /**
     * @Description Selecting the most adapted individual genes in the population and making n copies directly into the next generation of the population
     */
    public void selectBestGenomeAndJoinNext() {
        newPopulation = new ArrayList<>();
        Genome tempBest = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            if (population.get(i).getFitness() > tempBest.getFitness()) {
                tempBest = population.get(i);
            }
        }
        if (compareDouble(tempBest.getFitness(), bestGenome.getFitness()) == 1) {
            bestGenome = copyGenome(tempBest);
            bestT = t;
            System.out.println(" Current Algebra:" + t + " : " + bestGenome.getSolution().getRate());
        }
        for (int i = 0; i < cloneNumOfBestIndividual; i++) {
            newPopulation.add(copyGenome(tempBest));
        }
    }

    /**
     * @Description Update the cumulative probability and total population fitness of each individual
     */
    public void updateProbabilityAndTotalFitness() {
        probabilitys = new double[population.size()];
        totalFitness = 0;
        for (Genome genome : population) {
            totalFitness += genome.getFitness();
        }
        double rate = 0.0;
        for (int i = 0; i < population.size(); i++) {
            rate += (population.get(i).getFitness() / totalFitness);
            probabilitys[i] = rate;
        }
    }

    /**
     * @param genome template gene
     * @return Returns the copied gene
     * @Description According to the incoming template gene, copy and return the copied gene
     */
    public Genome copyGenome(Genome genome) {
        Genome copy = new Genome(genome.getW(), genome.getH(), genome.getItems(), genome.isRotateEnable(), genome.getGenomeArray().clone());
        copy.setSolution(genome.getSolution());
        copy.setFitness(genome.getFitness());
        return copy;
    }

    /**
     * @param genomeList template gene collection
     * @return Returns the copied gene collection
     * @Description According to the incoming template gene set, make a copy and return the copied gene set
     */
    public List<Genome> copyGenomeList(List<Genome> genomeList) {
        List<Genome> copyList = new ArrayList<>();
        for (Genome genome : genomeList) {
            copyList.add(copyGenome(genome));
        }
        return copyList;
    }

    /**
     * @param d1 double-precision floating-point variable 1
     * @param d2 double-precision floating-point variable 2
     * @return Return 0 means that two numbers are equal, return 1 means that the former is greater than the latter, and return -1 means that the former is less than the latter,
     * @Description Determine the size relationship between two double-precision floating-point variables
     */
    private int compareDouble(double d1, double d2) {
        // Defines a margin of error where two numbers are considered equal if they differ less than this error 1e-06 = 0.000001
        double error = 1e-06;
        if (Math.abs(d1 - d2) < error) {
            return 0;
        } else if (d1 < d2) {
            return -1;
        } else if (d1 > d2) {
            return 1;
        } else {
            throw new RuntimeException("d1 = " + d1 + " , d2 = " + d2);
        }
    }

}
