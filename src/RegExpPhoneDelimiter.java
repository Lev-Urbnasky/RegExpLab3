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
        String in;
        try {
            in = new String(Files.readAllBytes(file.toPath()), "UTF-8");
        } catch (IOException e) {
            System.out.println("Нет файла по указанному адресу!");
            return;
        }
        Matcher matcher = Pattern.compile("\\s+\\+?(\\d[\\s\\-\\(\\)\\+]{0,3}){11}").matcher(in);
        StringBuffer out = new StringBuffer();
        while (matcher.find()) {
            String number = matcher.group();
            number = number.replaceAll("[^\\d]", "");
            StringBuilder stringBuilder = new StringBuilder(number.replaceFirst("\\d", "7"));
            number = stringBuilder
                    .insert(0, " +").insert(3, " ").insert(4, "(").insert(8, ") ").insert(13, "-").insert(16, "-")
                    .insert(19, " ").toString();
            matcher.appendReplacement(out, number);
        }
        System.out.println(matcher.appendTail(out).toString());
    }
}