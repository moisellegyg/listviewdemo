package com.yugegong.listviewdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * DemoData is the type of data that {@link DemoService} and {@link DemoAdapter} process
 * @author Yuge Gong
 * @since 2016-07-10
 */
public class DemoData{

    private String data;
    public DemoData(int val) {
        data = "Demo Data: " + val;
    }
    public String getDataString() {
        return data;
    }

    /**
     * Call this method to parse {@link ServerDataStream} and converted to a list of DemoData.
     * @param serverDataStream Data from server
     * @return A list of DemoData that from parsed {@link ServerDataStream}
     */
    public static List<DemoData> parseServerData(ServerDataStream serverDataStream) {
        List<DemoData> list = new ArrayList<>();
        for (Integer val : serverDataStream.getDataStream()) {
            list.add(new DemoData(val));
        }
        return list;
    }

    @Override
    public String toString() {
        return data;
    }
}
