package src;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
}
