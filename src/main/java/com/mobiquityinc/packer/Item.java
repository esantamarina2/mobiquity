package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import lombok.Data;

@Data
public class Item implements Comparable<Item> {

    private static final String COMMA = ",";
    private static final String EURO = "€";

    private int id;
    private int weight;
    private double cost;


    public Item(String lineItem) {
        try {
            String[] fields = lineItem.split(COMMA);
            id = Integer.parseInt(fields[0]);
            weight = (int) (Float.parseFloat(fields[1]) * 100); //Removing decimals
            cost = Double.parseDouble(fields[2].replace(EURO, ""));
        } catch (RuntimeException e) {
            throw new APIException("Item could not be read: " + lineItem);
        }
    }

    @Override
    public String toString() {
        return "Item {" +
                "id=" + id +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }

    @Override
    public int compareTo(Item secondItem) {
        double diffValue = this.getCost() - secondItem.getCost();
        if (this.getCost() - secondItem.getCost() != 0) {
            return (int) (diffValue * 100);
        }
        return this.getWeight() - secondItem.getWeight();
    }
}
