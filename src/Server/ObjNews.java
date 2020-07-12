package Server;

public class ObjNews {
    private int N;
    private String urlb;
    private String t;

    public ObjNews(int stt, String urlb, String t) {
        this.N = stt;
        this.urlb = urlb;
        this.t = t;
    }

    public String getUrlb() {
        return urlb;
    }

}
