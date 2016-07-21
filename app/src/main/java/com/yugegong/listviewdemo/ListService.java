package com.yugegong.listviewdemo;

import java.util.List;

/**
 * This interface is used to create a service class for {@link CachedList}, which will help {@link CachedList}
 * to maintain and update its cache.
 * @author  Yuge Gong
 * @since   2016-07-16
 *
 * @param <E> The element type of this service. It should be identical as the element type of {@link CachedList}
 */
public interface ListService<E> {
    //default type: public final static
    int FETCH_ELDER = 0;
    int FETCH_NEWER = 1;
    int FETCH_WHOLE = 2;

    /**
     * Call this method to requesting new data from server
     * @param type There are three values for type, {@code FETCH_ELDER}, {@code FETCH_NEWER} and
     *             {@code FETCH_WHOLE}, which indicates the returned {@code data} is an older patch
     *             of the original data, a newer patch of the original data, or a complete new patch
     *             to replace the original data.
     * @param st The lowest index of the requested data
     * @param ed The highest index of the requested data
     * @param reqSt The lowest index of data patch really needs to be requested, {@code reqSt >= st}
     * @param reqEd The highest index of data patch really needs to be requested, {@code reqEd <= ed}
     */
    void updateData(int type, int st, int ed, int reqSt, int reqEd);

    /**
     * Add a {@link ListServiceListener} for the current {@link ListService}
     * @param listServiceListener The listener got added for the current {@link ListService}
     */
    void addListServiceListener(ListServiceListener<E> listServiceListener);

    /**
     *
     * @param <E> The element type of this listener. It should be identical as the
     *           element type of {@link ListService} and {@link CachedList}.
     */
    interface ListServiceListener<E> {
        /**
         * This is a callback function will be called to refresh the cache of {@link CachedList}
         * when requested data is returned from server.
         * @param total Size of full data from server
         * @param type There are three values for type, {@code FETCH_ELDER}, {@code FETCH_NEWER} and
         *             {@code FETCH_WHOLE}, which indicates the returned {@code data} is an older patch
         *             of the original data, a newer patch of the original data, or a complete new patch
         *             to replace the original data.
         * @param st The lowest index of the requested data
         * @param ed The highest index of the requested data
         * @param reqSt The lowest index of {@code data}, {@code reqSt >= st}
         * @param reqEd The highest index of {@code data}, {@code reqEd <= ed}
         * @param data Data patch returned from server. Index range of data is [{@code reqSt}, {@code reqEd}]
         */
        void refreshList(int total, int type, int st, int ed, int reqSt, int reqEd, List<E> data);
    }
}
