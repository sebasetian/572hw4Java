import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class ExtractLinks {

    public static void main(String[] args) throws Exception {

        String mapPath = "URLtoHTML_mercury.csv";
        File mapFile = new File(mapPath);
        Map<String,String> fileUrlMap = new HashMap<>();
        Map<String,String> urlFileMap = new HashMap<>();
        PrintWriter writer = new PrintWriter("edgeList.txt");
        try {
            Scanner sc = new Scanner(mapFile);
            while(sc.hasNext()) {
                String[] pairs = sc.next().split(",");
                fileUrlMap.put(pairs[0],pairs[1]);
                urlFileMap.put(pairs[1],pairs[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String filePath = "mercurynews";
        File files = new File(filePath);


        Set<String> edges = new HashSet<>();
        System.out.println("start calculating");
        for(File file: Objects.requireNonNull(files.listFiles())) {
            Document doc = Jsoup.parse(file,"UTF-8",fileUrlMap.get(file.getName()));
            Elements links = doc.select("a[href]");

            for (Element link:links) {
                String url = link.attr("href").trim();
                if(urlFileMap.containsKey(url)) {
                    edges.add(file.getName() + " " + urlFileMap.get(url));
                }
            }
        }
        System.out.println("start printing");
        for (String s:edges) {
            writer.println(s);
        }
        writer.flush();
        writer.close();
        System.out.println("finished");

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
