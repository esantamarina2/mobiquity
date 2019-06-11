package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.mobiquityinc.exception.APIException;
import lombok.Data;

@Data
public class PackerInstance {

    private static final String ELEMENT_REGEX = "\\((.*?)\\)";
    private List<Item> items;
    private int bagCapacity;

    /**
     *
     * Represents a single instance of the knapsack problem, containing
     * a list of items and a bag capacity
     */
    public PackerInstance(String instance) {

        try {
            String[] values = instance.split(":");
            bagCapacity = Integer.parseInt(values[0].trim()) * 100;
            items = new ArrayList<>();
            Pattern pattern = Pattern.compile(ELEMENT_REGEX);
            Matcher matcher = pattern.matcher(values[1]);

            while (matcher.find()) {
                items.add(new Item(matcher.group(1).trim()));
            }
            Collections.sort(items);
        } catch (RuntimeException e) {
            throw new APIException("Line Items could not be read: " + instance);
        }
    }

}
