package com.example.demo;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class BinlogDemo {

    public static void main(String[] args) throws IOException {

        // 连接到本地 MySQL
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "root"
        );

        // 设置监听器
        client.registerEventListener(event -> {

            EventData data = event.getData();

            if (data instanceof TableMapEventData) {
                TableMapEventData tableMap = (TableMapEventData) data;
                System.out.println("==== TABLE MAP ====");
                System.out.println("Database: " + tableMap.getDatabase());
                System.out.println("Table: " + tableMap.getTable());
            }

            // ---- 写入事件（INSERT） ----
            if (data instanceof WriteRowsEventData) {
                System.out.println("==== INSERT ====");
                WriteRowsEventData d = (WriteRowsEventData) data;
                d.getRows().forEach(row -> {
                    System.out.println("NEW ROW: " + row);
                });
            }

            // ---- 删除事件（DELETE） ----
            if (data instanceof DeleteRowsEventData) {
                System.out.println("==== DELETE ====");
                DeleteRowsEventData d = (DeleteRowsEventData) data;
                d.getRows().forEach(row -> {
                    System.out.println("OLD ROW: " + row);
                });
            }

            // ---- 更新事件（UPDATE） ----
            if (data instanceof UpdateRowsEventData) {
                System.out.println("==== UPDATE ====");
                UpdateRowsEventData d = (UpdateRowsEventData) data;

                d.getRows().forEach(row -> {
                    Map.Entry<Serializable[], Serializable[]> entry = row;
                    Serializable[] before = entry.getKey();
                    Serializable[] after  = entry.getValue();

                    System.out.println("BEFORE: " + java.util.Arrays.toString(before));
                    System.out.println("AFTER : " + java.util.Arrays.toString(after));
                });
            }
        });

        System.out.println("Listening for binlog events...");
        client.connect(); // 阻塞运行
    }
}
