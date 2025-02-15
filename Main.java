import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Main {
  public static final String SRC = "src";
  public static final String DEST = "dest";
  public static final Set<String> ignoreTags = new HashSet<>();

  static {
    ignoreTags.add("<code>");
    ignoreTags.add("</code>");
    ignoreTags.add("<b>");
    ignoreTags.add("</b>");
    ignoreTags.add("<p>");
    ignoreTags.add("</p>");
    ignoreTags.add("<em>");
    ignoreTags.add("</em>");
    ignoreTags.add("<pre>");
    ignoreTags.add("</pre>");
    ignoreTags.add("<i>");
    ignoreTags.add("</i>");
    ignoreTags.add("<ul>");
    ignoreTags.add("</ul>");
    ignoreTags.add("<li>");
    ignoreTags.add("</li>");
    ignoreTags.add("<table>");
    ignoreTags.add("</table>");
    ignoreTags.add("<tr>");
    ignoreTags.add("</tr>");
    ignoreTags.add("<td>");
    ignoreTags.add("</td>");
    ignoreTags.add("<th>");
    ignoreTags.add("</th>");
    ignoreTags.add("<blockquote>");
    ignoreTags.add("</blockquote>");
    ignoreTags.add("<tt>");
    ignoreTags.add("</tt>");
  }

  public static void main(String[] args) throws Exception {
    String fileName = args[0];
    copy(fileName);
  }

  public static void copy(String fileName) throws Exception {
    File file = new File(SRC, fileName);

    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
    StringBuffer buff = new StringBuffer();
    String line = null;

    while ((line = in.readLine()) != null) {
      buff.append(processLine(line)).append("\n");
    }
    in.close();

    File outputFile = new File(DEST, fileName);
    FileOutputStream fos = new FileOutputStream(outputFile);
    fos.write(buff.toString().getBytes());
    fos.flush();
    fos.close();
  }

  public static String processLine(String text) throws Exception {
    if (isBlank(text)) {
      return "";
    }
    StringBuilder buff = new StringBuilder();

    boolean inTag = false;
    StringBuilder tagBuff = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (inTag) {
        tagBuff.append(c);
        if (c == '>') {
          inTag = false;
          String tag = tagBuff.toString();
          if (ignoreTags.contains(tag)) {
            continue;
          }
          buff.append(tagBuff);
        }
      } else {
        if (c == '<') {
          inTag = true;
          tagBuff = new StringBuilder();
          tagBuff.append(c);
        }
      }
    }
    return buff.toString();
  }

  public static boolean isBlank(String text) {
    if (text == null) {
      return true;
    }
    if ("".equals(text.trim())) {
      return true;
    }
    return false;
  }

}
