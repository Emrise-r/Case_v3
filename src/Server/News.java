package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class News extends Thread {
    private Server_v2 server;
    public List<ObjNews> listNews;

    public News(Server_v2 server) {
        this.server = server;
    }

    @Override
    public void run() {

        try {
            URL url = new URL("https://www.nytimes.com/");
            Scanner scan = new Scanner(new InputStreamReader(url.openStream()));
            scan.useDelimiter("\\Z");
            String title = scan.next();
            scan.close();

            title = title.replaceAll("\\n", "");
            Pattern pattern = Pattern.compile("<div class=\"(.*?)\"><a href=\"(.*?)\"><div(.*?)><h2(.*?)>(.*?)</h2></div><");
            Matcher matcher = pattern.matcher(title);

            int n = 1, N = 0;
            String t;
            String urlb;
            listNews = new ArrayList<ObjNews>();
            while (matcher.find() && N < 10) {
                t = matcher.group(5);
                if (t.contains("h2")) {
                    continue;
                }
                if(n > 3) {
                    if (matcher.group(2).contains("https://www.nytimes.com/")) {
                        urlb = matcher.group(2);
                    } else urlb = "https://www.nytimes.com/" + matcher.group(2);
                    urlb = urlb.replaceAll("\"(.*?)", "");
                    N = n - 3;
                    System.out.println(urlb);
                    ObjNews objNews = new ObjNews(N, urlb, t);
                    listNews.add(objNews);
                    server.serverSendAll(N + ". " + t.replaceAll("<span>|</span>","") + "\n");
//                    RunServer.textAreaDisplay.appendText(N + ". " + t.replaceAll("<span>|</span>","") + "\n");
//                    System.out.println(urlb);
                }
                n++;
            }
            server.serverSendAll("Nhap so thu tu bao ban can doc\n");
//            if (this.key != 0) {

//            }

        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {

        }
    }
    public void startMainNews(int key){
        MainNews mainNews = new MainNews(listNews.get(key - 1).getUrlb(), this.server, this);
        mainNews.start();
    }
}
