package com.mobiquityinc;

import com.mobiquityinc.packer.Packer;

public  class Main {

    private static final String FILE_PATH = "test-data.txt";

    public static void main(String[] args){
        String result = Packer.pack(FILE_PATH);
        System.out.println(result);
    }
}
