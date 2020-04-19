import java.io.*;
import java.util.Scanner;


class Loader {

    String[] loadPreviousSheet() {
        try {
            File file = new File("../PreviousSheet.txt");
            Scanner scanner = new Scanner(file);

            String currentLine = scanner.nextLine();

            System.out.println("Reading: " + currentLine);

            if (currentLine.length() < 1) {
                throw new Exception();
            } else {
                return currentLine.split("\\s+");
            }
        } catch (Exception e) {
            System.out.println("error");
            return new String[0];
        }
    }

    boolean saveCurrentSheet(Sheet sheet) {
        StringBuilder builder = new StringBuilder();
        int[] sheetNumbers = sheet.getSheetNumbers();

        for (int i = 0; i < 15; i++) {
            builder.append(sheetNumbers[i]);
            builder.append(' ');
        }
        String saveString = builder.toString();
        System.out.println("Trying to save: " + saveString);

        try {
            File file = new File("../PreviousSheet.txt");
            OutputStream out = new FileOutputStream(file);

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            out.write(saveString.getBytes());
            out.flush();
            out.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
