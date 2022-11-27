package web_crawler_demo.src;

import org.jsoup.Connection;  // import cac thu vien jsoup
import org.jsoup.Jsoup;           // cấp những hàm để lấy Document từ một file HTML hoặc một URL
import org.jsoup.nodes.Document;  // cung cấp những hàm để lấy dữ liệu trong trang HTML theo nhiều cách
import org.jsoup.nodes.Element;   // class element ( 1 element có thể có nhiều element con

import java.util.ArrayList; // import lop Array List

public class Crawler {
    public static void main(String[] args) {
        String url = "https://nguoikesu.com/";  // khai bao xau url de luu duong link cua trang web
        crawl(1, url, new ArrayList<String>()); // ham crawl de crawl du lieu tu url va luu trong mang
    }

    private static void crawl(int level, String url, ArrayList<String> visited) {
        if (level <= 5) {
            Document doc = request(url,visited);  // lấy document từ url và lưu vào object doc
            if(doc!= null){ // nếu không có gì được lấy ( tức là trang web rỗng)
                for(Element link: doc.select("a[href]")){ // vòng for đối với mỗi element link là thẻ <a> được select từ doc
                    String next_link = link.absUrl("href"); // khai báo xâu để lưu nội dung của thẻ <a>
                    if(visited.contains(next_link) ==false){  // nếu mảng visited không chứa next_link
                        crawl(level++, next_link, visited);   // tiếp tục crawl từ lịnk mới
                    }
                }
            }
        }
    }
    private static Document request(String url, ArrayList<String> v){  // khởi tạo hàm để request để lấy document
        try{
            Connection con = Jsoup.connect(url);    // kết nối với web từ link
            Document doc = con.get(); // response document lấy được lưu vào doc

            if(con.response().statusCode()==200){  //nếu response trả về có status là 200 => cớ dữ liệu
                System.out.println("Link: " + url); // in ra link
                System.out.println(doc.title());   // in ra title
                v.add(url);                        // them url vào array list
                return doc; // trả về doc
            }
            return null;    // trả ve null

        }catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

}
