package info.jab.fp.euler.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class AnswerLoader {

    //TODO Refactor this method
    private List<String> loadFile() {

        ArrayList<String> arr = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(
                getClass().getClassLoader().getResource("euler/answers.txt").getFile())))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }

    Function<String, String > getAnswer = row -> Arrays.stream(row.split(":"))
            .skip(1)
            .map(part -> part.trim())
            .findFirst()
            .get();

    public String getAnswer(int index) {

        return loadFile().stream()
                .skip(index - 1)
                .findFirst()
                .map(getAnswer)
                .get();
    }

    public int getAnswerToInt(int index) {

        return Integer.parseInt(loadFile().stream()
                .skip(index - 1)
                .findFirst()
                .map(getAnswer)
                .get());
    }

    public long getAnswerToLong(int index) {

        return Long.parseLong(loadFile().stream()
                .skip(index - 1)
                .findFirst()
                .map(getAnswer)
                .get());
    }
}
