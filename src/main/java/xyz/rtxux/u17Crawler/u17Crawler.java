package xyz.rtxux.u17Crawler;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.handler.CompositePipeline;
import xyz.rtxux.u17Crawler.Processor.ChapterProcessor;
import xyz.rtxux.u17Crawler.Processor.ComicListProcessor;
import xyz.rtxux.u17Crawler.Processor.ComicProcessor;
import xyz.rtxux.u17Crawler.pipeline.ChapterPipeline;
import xyz.rtxux.u17Crawler.pipeline.ComicPipeline;
import xyz.rtxux.u17Crawler.utils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class u17Crawler {


    public static void main(String[] args) {
        String host, port, username, password, filepath;
        int startPage;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("MySQL Host: ");
            host = reader.readLine();
            System.out.println("MySQL Port: ");
            port = reader.readLine();
            System.out.println("MySQL Username: ");
            username = reader.readLine();
            System.out.println("MySQL Password: ");
            password = reader.readLine();
            Properties sqlConnectionProperties = new Properties();
            sqlConnectionProperties.setProperty("url", "jdbc:mysql://" + host + ":" + port + "/u17?useSSL=false&serverTimezone=UTC");
            sqlConnectionProperties.setProperty("username", username);
            sqlConnectionProperties.setProperty("password", password);
            utils.setSqlConnectionProperties(sqlConnectionProperties);
            System.out.println("RecordFile: ");
            filepath = reader.readLine();
            utils.setRecordPath(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompositePageProcessor cpp = new CompositePageProcessor(Site.me().setSleepTime(100));
        CompositePipeline compositePipeline = new CompositePipeline();
        compositePipeline.addSubPipeline(new ChapterPipeline());
        compositePipeline.addSubPipeline(new ComicPipeline());
        cpp.addSubPageProcessor(new ComicListProcessor());
        cpp.addSubPageProcessor(new ComicProcessor());
        cpp.addSubPageProcessor(new ChapterProcessor());
        Spider spider = Spider.create(cpp).addPipeline(compositePipeline).thread(4);
        spider.addRequest(utils.createComicListRequest(1));
        Runtime.getRuntime().addShutdownHook(new Thread(utils::saveRecord));
        spider.runAsync();
        System.out.println("Started!\nType \"stop\" to Stop");
        try {
            while (true) {
                if (reader.readLine().equals("stop")) {
                    spider.stop();
                    reader.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
