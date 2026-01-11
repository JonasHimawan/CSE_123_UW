//Jonas Himawan
//5/20/2025
//CSE 123
//P2: Disaster Relief
//TA: Cady Xia
// A client class for creating scenarios (list of regions) and allocating relief to said regions.
import java.util.*;

public class Client {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {

        List<Region> result = new ArrayList<>();
        Region reg1 = new Region("Region #1", 500, 200);
        Region reg2 = new Region("Region #2", 300, 800);
        Region reg3 = new Region("Region #3", 200, 400);
        Region reg4 = new Region("Region #4", 700, 200);
        Region reg5 = new Region("Region #5", 400, 500);
        Region reg6 = new Region("Region #5", 400, 500);
        Region reg7 = new Region("Region #5", 400, 500);

        result.add(reg1);
        result.add(reg2);
        result.add(reg3);
        result.add(reg4);
        result.add(reg5);

        double budget = 1000;
        Allocation allocation = allocateRelief(budget, result);
        printResult(allocation, budget);
        Allocation testing = new Allocation();
        testing = testing.withRegion(reg1);
        testing = testing.withRegion(reg4);
        testing = testing.withRegion(reg5);

        System.out.println(allocation.equals(testing));
    }
    
    
    //Behavior:
    // - Returns the allocation that will cause the most people being helped for the best
    //   cost available.
    // - The returned allocation is in the given budget.
    // - If multiple allocations help the same number of people, the one with
    //   the lower cost is preferred.
    // - The possible regions considered for help cannot be null.
    //Exceptions:
    // - Throws IllegalArgumentException if sites is null.
    //Returns:
    // - An Allocation object that will cause the most people being helped for the best cost.
    //Parameters:
    // - double budget: The maximum amount of money that can be spent.
    // - List<Region> sites: The list of possible regions to consider for allocation.
    public static Allocation allocateRelief(double budget, List<Region> sites) {
        if (sites == null) {
            throw new IllegalArgumentException("Sites cannot be null");
        }
        return allocateRelief(budget, sites, new Allocation(), 0);
    }

    //Behavior:
    // - Helper method for allocateRelief.
    // - Builds and returns the best Allocation (most people helped, lowest cost if tied)
    //Exceptions:
    // - None
    //Returns:
    // - An Allocation object that will cause the most people being helped for the best cost.
    //Parameters:
    // - double budget: The maximum amount of money that can be spent.
    // - List<Region> sites: The list of possible regions to conside
    // - Allocation soFar: The current allocation being built.
    // - int index: The current index in the list of regions to consider.
    private static Allocation allocateRelief(double budget, List<Region> sites, 
                                                Allocation soFar, int index) {
        // Base case: 
        if (index == sites.size()) {
            return soFar;
        }
        Region currRegion = sites.get(index);
        Allocation best = null;

        if (currRegion.getCost() <= budget) {
            best = allocateRelief(budget - currRegion.getCost(), sites, 
            soFar.withRegion(currRegion), index + 1);
        }
        
        soFar = allocateRelief(budget, sites, soFar, index + 1);

        if (best == null){
            return soFar;
        }
        return better(best, soFar);
    }

    //Behavior:
    // - Helper method for allocateRelief.
    // - Compares two Allocation objects and returns the one that is considered better.
    // - 'Better' means more people helped, if tied, then lower cost wins.
    //Exceptions:
    // - None
    //Returns:
    // - The better of the two Allocation objects based on population helped and total cost.
    //Parameters:
    // - Allocation best: The first allocation to compare.
    // - Allocation soFar: The second allocation to compare.    
    private static Allocation better(Allocation best, Allocation soFar) {
        int bestPop = best.totalPeople();
        int soFarPop = soFar.totalPeople();
        if (bestPop > soFarPop || (bestPop == soFarPop && best.totalCost() <= soFar.totalCost())) {
            return best;
        }
        return soFar;
    }
    
    /**
    * Prints each allocation in the provided set. Useful for getting a quick overview
    * of all allocations currently in the system.
    * @param allocations Set of allocations to print
    */
    public static void printAllocations(Set<Allocation> allocations) {
        System.out.println("All Allocations:");
        for (Allocation a : allocations) {
            System.out.println("  " + a);
        }
    }

    /**
    * Prints details about a specific allocation result, including the total people
    * helped, total cost, and any leftover budget. Handy for checking if we're
    * within budget limits!
    * @param alloc The allocation to print
    * @param budget The budget to compare against
    */
    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    /**
    * Creates a scenario with numRegions regions by randomly choosing the population 
    * and cost of each region.
    * @param numRegions Number of regions to create
    * @param minPop Minimum population per region
    * @param maxPop Maximum population per region
    * @param minCostPer Minimum cost per person
    * @param maxCostPer Maximum cost per person
    * @return A list of randomly generated regions
    */
    public static List<Region> createRandomScenario(int numRegions, int minPop, int maxPop,
                                                    double minCostPer, double maxCostPer) {
        List<Region> result = new ArrayList<>();

        for (int i = 0; i < numRegions; i++) {
            int pop = RAND.nextInt(maxPop - minPop + 1) + minPop;
            double cost = (RAND.nextDouble(maxCostPer - minCostPer) + minCostPer) * pop;
            result.add(new Region("Region #" + i, pop, round2(cost)));
        }

        return result;
    }

    /**
    * Manually creates a simple list of regions to represent a known scenario.
    * @return A simple list of regions
    */
    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();

        result.add(new Region("Region #1", 50, 500));
        result.add(new Region("Region #2", 100, 700));
        result.add(new Region("Region #3", 60, 1000));
        result.add(new Region("Region #4", 20, 1000));
        result.add(new Region("Region #5", 200, 900));

        return result;
    }    

    /**
    * Rounds a number to two decimal places.
    * @param num The number to round
    * @return The number rounded to two decimal places
    */
    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
