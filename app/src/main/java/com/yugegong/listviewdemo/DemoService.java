package com.yugegong.listviewdemo;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * DemoService is an implementation of {@link ListService}. The element type for this implementation
 * is {@link DemoData}. DemoService will use a different thread to request new data to avoid blocking
 * the main thread.
 * @author Yuge Gong
 * @since 2016-07-10
 */
public class DemoService implements ListService<DemoData> {
    private List<ListServiceListener<DemoData>> mListenerList;
    private int mPendingHead;

    public DemoService() {
        mListenerList = new ArrayList<>();
    }

    @Override
    public void addListServiceListener(ListServiceListener<DemoData> listServiceListener) {
        mListenerList.add(listServiceListener);
    }

    @Override
    public void updateData(int type, int st, int ed, int reqSt, int reqEd) {
        mPendingHead = st;
        DemoTask task = new DemoTask();
        task.execute(st, ed, reqSt, reqEd, type);
    }

    /**
     * DemoTask is an implementation of {@link AsyncTask}. A DemoTask instance will be created when
     * there is a request to a new data patch.
     */
    private class DemoTask extends AsyncTask<Integer, Void, List<DemoData>> {
        private int type;
        private int total;
        private int st, ed;
        private int reqSt, reqEd;

        @Override
        protected List<DemoData> doInBackground(Integer... params) {
            st = params[0];

            if (this.st != mPendingHead) {
                this.cancel(true);
            }
            if (this.isCancelled()) {
                return null;
            }
            ed = params[1];
            reqSt = params[2];
            reqEd = params[3];
            type = params[4];
            ServerDataStream serverDataStream = getServerDataStream(reqSt, reqEd);
            total = serverDataStream.getDataStreamSize();

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return DemoData.parseServerData(serverDataStream);
        }

        @Override
        protected void onPostExecute(List<DemoData> demoDatas) {
            for (ListServiceListener listener : mListenerList) {
                listener.refreshList(total, type, st, ed, reqSt, reqEd, demoDatas);
            }
        }

        private ServerDataStream getServerDataStream(int st, int cnt) {
            return ServerDataStream.generateServerDataStream(st, cnt);
        }

    }
}