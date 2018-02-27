package cypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import util.FileManager;

/**
 *
 * @author rajitha
 */
public class Decryptor {

    private HashMap<Integer, Integer> characterMap;
    private HashMap<Integer, Integer> permutationMatrix;
    private ArrayList<String> blockTextArray;
    private double timeElapse = 0;

    public Decryptor() {
        characterMap = new HashMap<Integer, Integer>();

        for (int key = 128; key < 384; key++) {
            characterMap.put(key, key - 128);
        }

    }

    public void decrypt(String key, String path, String saveFilePath) throws IOException {
        FileManager fileManager = new FileManager();
        characterMapping(key);
        permutationMatrix = createPermutationMatrix(key);
        BufferedReader bufferReader = fileManager.readTextFile(path);

        long time = System.currentTimeMillis();
        
        blockTextArray = textSplit(bufferReader);

        for (int i = 0; i < blockTextArray.size(); i++) {
            String substituted = substitution(blockTextArray.get(i));
            String permutated = permutation(substituted, permutationMatrix);

            fileManager.writeTextFile(saveFilePath, permutated, true);
        }

        timeElapse = (System.currentTimeMillis() - time) / 1000.0;

    }

    public double getTimeElapsed() {
        return this.timeElapse;
    }

    
    private void characterMapping(String key) {
        long newKey = hashCode(key, 9); //key should be less than 16 characters
        int step = (int) newKey % 8;

        if (step == 0) {
            step = 8;
        }

        for (int i = 0; i < 256; i += step) {
            characterMap.put(i + 384, i);
            int value = characterMap.get(i + 384);
            char x = (char) (i);
            char c = (char) (value);
            System.out.println(c + " : " + x);
        }
    }

    private ArrayList<String> textSplit(BufferedReader textBuffer) throws IOException {
        ArrayList<String> blockArray = new ArrayList<>();
        String textLine;
        int blockSize = 0;
        String block = "";

        while ((textLine = textBuffer.readLine()) != null) {
            int count = 0;
            while (count < textLine.length()) {
                block += textLine.charAt(count);
                if ((blockSize + 1) % 8 == 0) {
                    blockArray.add(block);
                    block = "";
                }
                blockSize++;
                count++;
            }
        }
        if (block.length() != 0) {
            blockArray.add(block);
        }

        textBuffer.close();
        return blockArray;
    }

    private String substitution(String textBlock) {
        String newTextBlock = "";
        for (int i = 0; i < textBlock.length(); i++) {
            char tempChar = textBlock.charAt(i);
            if (((int) tempChar) < 640) {
                int charVal = characterMap.get((int) tempChar);
                newTextBlock += (char) charVal;
            } else {
                newTextBlock += tempChar;
            }
        }
        return newTextBlock;
    }

    private String permutation(String textBlock, HashMap<Integer, Integer> matrix) {
        String newTextBlock = "";
//        if (textBlock.length()<8){
//            for(int x = textBlock.length(); x <= 8;x++){
//                textBlock+= (char) (641);
//            }
//        }
        for (int i = 0; i < 8; i++) {
            char replaceChar = textBlock.charAt(matrix.get(i));
            newTextBlock += replaceChar;
        }
        System.out.println(newTextBlock);
        return newTextBlock;
    }

    private HashMap<Integer, Integer> createPermutationMatrix(String key) {
        HashMap<Integer, Integer> matrix = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> reverceMatrix = new HashMap<Integer, Integer>();
        long newKey = hashCode(key, 7); //key should be less than 16 characters
        int remainder = (int) newKey % 8;

        for (int x = 0; x < 8; x++) {
            matrix.put(x, 7 - x);
        }

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                if ((remainder + i + j) % 2 == 0) {
                    int value1 = matrix.get(i);
                    int value2 = matrix.get(4 + i);
                    matrix.put(i, value2);
                    matrix.put(4 + i, value1);
                }
            }
        }

        for (int x = 0; x < 8; x++) {
            int value = matrix.get(x);
            reverceMatrix.put(value, x);
        }

        System.out.println(matrix.toString());
        return reverceMatrix;
    }

    private long hashCode(String key, int multiplier) { //key max 16 chars
        long h = 0;
        if (key.length() > 0) {
            char val[] = key.toCharArray();

            for (int i = 0; i < val.length; i++) {
                h = multiplier * h + val[i];
            }
        }
        return h;
    }
}
