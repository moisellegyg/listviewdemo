package com.yugegong.listviewdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * ServerDataStream is the type of data returned from server
 * @author Yuge Gong
 * @since 2016-07-16
 */
public class ServerDataStream {
    // The size of full server data for this demo
    private final static int DATA_SIZE = 1000;
    private List<Integer> stream;

    public ServerDataStream(int st, int ed) {
        if (st < 0) st = 0;
        if (ed > DATA_SIZE - 1) ed = DATA_SIZE - 1;

        stream = new ArrayList<>();
        while (st <= ed) {
            stream.add(st);
            st++;
        }
    }

    public List<Integer> getDataStream() {
        return stream;
    }

    public int getDataStreamSize() {
        return  DATA_SIZE;
    }

    public static ServerDataStream generateServerDataStream(int st, int ed) {
        return new ServerDataStream(st, ed);
    }
}
