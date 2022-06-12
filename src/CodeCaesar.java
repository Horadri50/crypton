package src;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CodeCaesar {
    private static final List<Character> alphabet = Arrays.asList('А', 'Б', 'В', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П',
            'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
            'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', '!', '?', '-', ';',':', ',', '.', ' ', '"');

    public static void main(String[] args) {

    }

    public static String scanner() {
        return new Scanner(System.in).nextLine();
    }

    private static void encryptCesar(Path pathFile, int offset) throws IOException {
        Path resultFile = createFile(pathFile);
        try (FileReader fr = new FileReader(String.valueOf(pathFile));
             FileWriter fw = new FileWriter(String.valueOf(resultFile))) {
            while (fr.ready()) {
                int charRead = fr.read();
                if (alphabet.contains((char) charRead)) {
                    fw.write(alphabet.get((alphabet.indexOf((char) charRead) + offset) % alphabet.size()));
                } else {
                    fw.write(charRead);
                }
            }
        }
    }

    private static void decryptCesar(Path pathFile, int offset) throws IOException {
        Path resultFile = createFile(pathFile);
        if (Thread.currentThread().getStackTrace()[2].getMethodName().equals("bruteForce")) {
            resultFile = createFile(pathFile, offset);
        }
        try (FileReader fr = new FileReader(String.valueOf(pathFile));
             FileWriter fw = new FileWriter(String.valueOf(resultFile))) {
            while (fr.ready()) {
                int charRead = fr.read();
                if (alphabet.contains((char) charRead)) {
                    int step = alphabet.indexOf((char) charRead) - offset;
                    if (step < 0) step = step + alphabet.size();
                    fw.write(alphabet.get(step % alphabet.size()));
                } else {
                    fw.write(charRead);
                }
            }
        }
    }

    private static void bruteForce(Path fileName) throws IOException {
        for (int i = 1; i <= alphabet.size(); i++) {
            decryptCesar(fileName, i);
        }
        Map<Integer, Path> maxSpaceInFile = new TreeMap<>();
        for (Path files: Files.newDirectoryStream(Path.of(fileName.getParent() + "\\BruteForce\\"))) {
            String[] readFile = Files.readString(files).split(" ");
            maxSpaceInFile.put(readFile.length, files);
        }

        System.out.println("Ваш расшифрован файл в папке " + fileName.getParent() + "\\BruteForce\\" + maxSpaceInFile.get(Collections.max(maxSpaceInFile.keySet())).getFileName());
    }

    private static Path createFile(Path fileName) throws IOException {
        String crypt = "encrypt_";
        if (Thread.currentThread().getStackTrace()[2].getMethodName().equals("decryptCesar")) {
            crypt = "decrypt_";
        }
        Path resultFile = Path.of(fileName.getParent() + "\\" + crypt + fileName.getFileName());
        if (!Files.isRegularFile(resultFile)) {
            Files.createFile(resultFile);
        }
        return resultFile;
    }

    private static Path createFile(Path fileName, int offset) throws IOException {
        Path bruteForceFile = Path.of(fileName.getParent() + "\\BruteForce\\");
        if (!Files.isDirectory(bruteForceFile)) {
            Files.createDirectory(bruteForceFile);
        }
        Path resultFile = Path.of(bruteForceFile + "\\" + offset + ".txt");
        if (!Files.isRegularFile(resultFile)) {
            Files.createFile(resultFile);
        }
        return resultFile;
    }

}
