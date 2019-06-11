package com.mobiquityinc.packer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mobiquityinc.exception.APIException;

public class Packer {

    public static String pack(String filePath) {
        try (
            Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(filePath).toURI()))) {
            return stream.map(line -> solvePackerInstance(new PackerInstance(line)))
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception ex) {
            throw new APIException("File could not be opened.");
        }
    }

    /**
     *
     * Dynamic programming algorithm to solve the knapsack problem. This solutions have a
     * time and space complexity of O(M*N) where M -> possible items and N bag capacity
     */
    public static String solvePackerInstance(PackerInstance packer) {
        int itemsListSize = packer.getItems().size();
        double[][] solutionMatrix = new double[itemsListSize + 1][packer.getBagCapacity() + 1];

        for (int index = 1; index <= itemsListSize; index++) {
            for (int weight = 1; weight <= packer.getBagCapacity(); weight++) {
                Item item = packer.getItems().get(index - 1);
                if (item.getWeight() <= weight) {
                    double newValue = item.getCost() + solutionMatrix[index - 1][weight - item.getWeight()];
                    double currentValue = solutionMatrix[index - 1][weight];
                    solutionMatrix[index][weight] = Math.max(newValue, currentValue);
                } else {
                    solutionMatrix[index][weight] = solutionMatrix[index - 1][weight];
                }
            }
        }
        return findSolutionIndexes(packer, solutionMatrix);
    }

    private static String findSolutionIndexes(PackerInstance packer, double[][] solutionMatrix) {
        List<Integer> indexesSolution = new ArrayList<>();
        int remainingCapacity = packer.getBagCapacity();

        for(int currentItemIndex  = packer.getItems().size()-1; currentItemIndex>0; currentItemIndex-- ) {
            if (solutionMatrix[currentItemIndex][remainingCapacity] != solutionMatrix[currentItemIndex - 1][remainingCapacity]) {
                indexesSolution.add(packer.getItems().get(currentItemIndex - 1).getId());
                remainingCapacity -= packer.getItems().get(currentItemIndex - 1).getWeight();
            }
        }

        Collections.sort(indexesSolution);

        if(indexesSolution.size()>0) {
            return indexesSolution.stream()
                    .map(index -> index.toString())
                    .collect(Collectors.joining(","));
        }

        return "-";
    }
}
