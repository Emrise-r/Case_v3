package Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainNews extends Thread{
    private Server_v2 server;
    private String urlb;
    private News news;

    public MainNews(String urlb, Server_v2 server, News news) {
        this.urlb = urlb;
        this.server = server;
        this.news = news;
    }

    @Override
    public void run() {

        try{
            URL url = new URL(urlb);
            Scanner scan = new Scanner(new InputStreamReader(url.openStream()));
            scan.useDelimiter("\\Z");
            String body = scan.next();
            scan.close();

            body = body.replaceAll("\\n", "");
            Pattern pattern = Pattern.compile("<p (.*?)>(.*?)</p>");
            Matcher matcher = pattern.matcher(body);

            String b;
            while (matcher.find()) {
                b = matcher.group(2);
                b = b.replaceAll("<(.*?)>", "");
//                b = b.replaceAll("\\n", "");
                System.out.println(b);
                server.serverSendAll(b);
//                RunServer.textAreaDisplay.appendText(b);
            }
        }catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
        }
    }
}
