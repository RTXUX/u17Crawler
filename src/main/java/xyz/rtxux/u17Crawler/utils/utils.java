package xyz.rtxux.u17Crawler.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class utils {
    private static SqlSessionFactory sqlSessionFactory = null;
    private static ThreadLocal<SqlSession> session = new ThreadLocal<>();
    public static Map<String,Object> createComicListRequestForm(int page){
        Map<String,Object> form = new HashMap<String,Object>();
        form.put("data[group_id]","no");
        form.put("data[theme_id]","no");
        form.put("data[is_vip]","30");
        form.put("data[accredit]","no");
        form.put("data[color]","no");
        form.put("data[comic_type]","no");
        form.put("data[series_status]","no");
        form.put("data[order]","2");
        form.put("data[page_num]",Integer.toString(page));
        form.put("data[read_mode]","no");
        return form;
    }

    public static Request createComicListRequest(int page) {
        Request request = new Request("http://www.u17.com/comic/ajax.php?mod=comic_list&act=comic_list_new_fun&a=get_comic_list");
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(createComicListRequestForm(page),"UTF-8"));
        request.putExtra("Type",1);
        request.putExtra("Page",page);
        return request;
    }

    public static int getChapterIdByAttribute(String attribute)
    {
        return Integer.parseInt(attribute.substring(4));
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession getSession() {
        if (session.get() == null) {
            session.set(getSqlSessionFactory().openSession());
        }
        return session.get();
    }

}
