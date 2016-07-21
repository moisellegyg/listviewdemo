package com.yugegong.listviewdemo;

import android.widget.BaseAdapter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


/**
 * CachedList is an implementation of {@link List}. The special part of CacheList is that it doesn't
 * store the full data set. Instead, it only stores partial data in its cache. The override
 * {@code get(int location)} method will update the cache when {@code location} is close to the edge
 * of the cache. The CacheList is created to handle large data set and avoid exceeding memory limitation.
 *
 * @author Yuge Gong
 * @since 2016-07-09
 * @param <E> The element type of this list.
 */
public class CachedList<E> extends AbstractList<E> implements ListService.ListServiceListener<E> {
    private final static int CACHE_SIZE = 100;
    private final static int CACHE_BUFFER = 10;

    private List<E> mCache; // Circular cache list
    private int head = 0;   // Head index of the mCache
    private int eldest = 0, newest = -1; // Eldest and Newest index into the full data set inside mCache
    private int pendingHead = 0, pendingTail = 0;   // Eldest and newest index of requested data

    private Integer size;   // size of the full data set

    private BaseAdapter mAdapter;
    private ListService<E> mService;


    public CachedList(ListService<E> service) {
        mService = service;
        service.addListServiceListener(this);
        mCache = new ArrayList<>(CACHE_SIZE);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public E get(int location) {

        if (location < pendingHead + CACHE_BUFFER || location > pendingTail - CACHE_BUFFER) {
            pendingHead = location - CACHE_SIZE/2;
            pendingTail = pendingHead + CACHE_SIZE - 1;
            int type = ListService.FETCH_WHOLE;
            int reqHead = pendingHead, reqTail = pendingTail;
            if (pendingHead >= eldest && pendingHead <= newest) {
                reqHead = newest + 1;
                type = ListService.FETCH_NEWER;
            }
            if (pendingTail >= eldest && pendingTail <= newest) {
                reqTail = eldest - 1;
                type = ListService.FETCH_ELDER;
            }

            mService.updateData(type, pendingHead, pendingTail, reqHead, reqTail);
        }
        if (location < eldest || location >= eldest + mCache.size()) {
            return null;
        }

        int ind = location % CACHE_SIZE;
        return mCache.get(ind);
    }

    @Override
    public int size() {
        if (size == null) return 1;
        return size;
    }

    @Override
    public void refreshList(int total, int type, int st, int ed, int reqSt, int reqEd, List<E> data) {
        if (st != pendingHead) {
            return;
        }
        this.size = total;
        int n = data.size();
        if (n == 0) return;
        int m = mCache.size();
        if (reqSt < 0) reqSt = 0;
        if (reqEd >= total) reqEd = total - 1;

        if (type == ListService.FETCH_WHOLE) {
            eldest = reqSt;
            newest = reqEd;
            head = reqSt % CACHE_SIZE;
        } else if (type == ListService.FETCH_NEWER) {
            head = (reqEd + 1) % CACHE_SIZE;
            eldest = st < 0 ? 0 : st;
            newest = reqEd < total ? reqEd : total;
        } else if (type == ListService.FETCH_ELDER) {
            eldest = reqSt;
            newest = ed < total ? ed : total;
            head = reqSt % CACHE_SIZE;
        } else {
            return;
        }

        int i = 0;
        while (reqSt <= reqEd) {
            int k = reqSt % CACHE_SIZE;
            if (k < mCache.size()) mCache.set(k, data.get(i));
            else mCache.add(data.get(i));
            reqSt++;
            i++;
        }

        mAdapter.notifyDataSetChanged();
    }
}