package xyz.rtxux.u17Crawler.Processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;
import xyz.rtxux.u17Crawler.model.ImageInfo;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ChapterProcessor implements SubPageProcessor{

    public MatchOther processPage(Page page) {
        int ChapterID = (int) page.getRequest().getExtra("ChapterID");
        String json = page.getHtml().regex("image_list: (\\{.*\\}).*image_pages",1).all().get(0);
        JSONObject image_list = JSON.parseObject(json);
        List<ImageInfo> images = new LinkedList<ImageInfo>();
        for (Map.Entry<String,Object> imageEntry : image_list.entrySet())
        {
            ImageInfo image = image_list.getObject(imageEntry.getKey(),ImageInfo.class);
            image.setSrc(new String(Base64.getDecoder().decode(image.getSrc())));
            image.setChapterID(ChapterID);
            images.add(image);
        }
        page.putField("Type",3);
        page.putField("Data",images);
        return MatchOther.NO;
    }

    public boolean match(Request request) {
        if (request.getExtra("Type").equals(3))
        {
            return true;
        }
        return false;
    }
}
