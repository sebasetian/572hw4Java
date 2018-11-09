import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class ExtractLinks {

    public static void main(String[] args) throws Exception {


        File file = new File("");
        Document doc = Jsoup.parse(file,"UTF-8","");

        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        print("\nMedia: (%d)",media.size());
        for (Element src:media) {
            if (src.tagName().equals("img")) {
                print(" * %s: <%s> %sx%s (%s)", src.tagName(),src.attr("abs:src"),src.attr("width"),src.attr("height"),
                        trim(src.attr("alt"),20));
            } else
                print(" * %s: <%s>", src.tagName(),src.attr("abs:src"));
        }
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg,args));
    }
    private static String trim(String s, int width) {
        if (s.length() > width) {
            return s.substring(0,width-1) + ".";
        } else {
            return s;
        }
    }
}
