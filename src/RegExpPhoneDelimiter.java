/**
 * Created by Лев on 02.06.2015.
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegExpPhoneDelimiter {
    public static void main(String[] ars) {
        RegExpPhoneDelimiter fixer = new RegExpPhoneDelimiter();
        fixer.delimit(new File("someTextWithPhones"));
    }

    public RegExpPhoneDelimiter() {
    }

    public void delimit(File file) {
        String input;
        try {
            input = new String(Files.readAllBytes(file.toPath()), "UTF-8");
        } catch (IOException e) {
            System.out.println("Нет файла по указанному адресу!");
            return;
        }
        Matcher matcher = Pattern.compile("\\s+\\+?(\\d[\\s\\-\\(\\)\\+]{0,3}){11}").matcher(input);
        StringBuffer output = new StringBuffer();
        while (matcher.find()) {
            String phoneNumber = matcher.group();
            phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
            StringBuilder stringBuilder = new StringBuilder(phoneNumber.replaceFirst("\\d", "7"));
            phoneNumber = stringBuilder
                    .insert(0, " +").insert(3, " ").insert(4, "(").insert(8, ") ").insert(13, "-").insert(16, "-")
                    .insert(19, " ").toString();
            matcher.appendReplacement(output, phoneNumber);
        }
        System.out.println(matcher.appendTail(output).toString());
    }
}