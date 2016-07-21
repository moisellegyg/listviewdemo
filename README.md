# listviewdemo
A demo project for Android ListView to load large size data.

When create a <a href="https://developer.android.com/reference/android/widget/ListView.html">ListView</a> in Android, one common Adapter to use is <a href="https://developer.android.com/reference/android/widget/ArrayAdapter.html">ArrayAdapter</a>. There are two types of data we can use for ArrayAdapter, array or List. Each has its pros and cons. However, both of them will fail and threw <b>out of memory error</b> when the full data set is too big. 

This demo project is targeting this issue by implementing a curstomized List called <code>CachedList</code>.

The CachedList maintains a list of cache with a certain size. When scrolling ListView, <code>get(int location)</code> method inside CachedList is called. Meanwhile CachedList will also decide if it needs to update the cache by checking the distance between <code>location</code> and the boundary of the cache. When scrolling smoothly, data requested at a position in ListView is already inside the cache. When fast scrolling, data inside the cache might not be up to date. In this case, ListView will firstly render dummy data while CachedList is requesting the server to update the cache. After the cache gets updated, <code>notifyDataSetChanged()</code> will be called to notify the Adapter and ListView will refresh the data.

In this demo, for a more obvious results, a delay of <b>1 second</b> is intentionally made when CachedList requests more data from server.
