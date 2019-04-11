# ListView优化 #
----------

参考博客：[https://blog.csdn.net/u011692041/article/details/53099584](https://blog.csdn.net/u011692041/article/details/53099584 "convertView、viewHolder")

## 使用convertView复用Item ##
使用convertView可以使未划入的item复用之前窗口中的item

    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View item = null;

        if (view == null) {
            item = View.inflate(mContext, R.layout.listview_item, null);
        }else{
            item = convertView;
        }

        //找到文本框
        TextView tv = (TextView) item.findViewById(R.id.tv);
        //设置文本内容
        tv.setText(listViewData.get(position));

        //找到复选框
        CheckBox cb = (CheckBox) item.findViewById(R.id.cb);

        return item;
    }
## ViewHolder使用 ##
 上述优化方式有个缺点，就是每次在findviewById，重新找到控件，然后对控件进行赋值，这样会减慢加载的速度，其实我们可以创建一个内部类ViewHolder，里面的成员变量和view中所包含的组件个数、类型相同，在convertview为null的时候，把findviewbyId找到的控件赋给ViewHolder中对应的变量，就相当于先把它们装进一个容器，下次要用的时候，直接从容器中获取，这样比findviewbyId效率要高进50%

	public class ListViewAdapter extends BaseAdapter {
	
	    private List<String> listViewData;
	
	    private Context mContext;
	
	    public ListViewAdapter(List<String> listViewData, Context mContext) {
	        this.listViewData = listViewData;
	        this.mContext = mContext;
	    }
	
	    @Override
	    public int getCount() {
	        return listViewData.size();
	    }
	
	    @Override
	    public Object getItem(int i) {
	        return listViewData.get(i);
	    }
	
	    @Override
	    public long getItemId(int i) {
	        return i;
	    }
	
	    @Override
	    public View getView(int position, View view, ViewGroup viewGroup) {
	
	        //Item对应的试图
	        View item = null;
	
	        ViewHolder vh = null;
	
	        if (view == null) {
	            item = View.inflate(mContext, R.layout.listview_item, null);
	            vh = new ViewHolder();
	            //找到文本框
	            vh.tv = (TextView) item.findViewById(R.id.tv);
	            //找到复选框
	            vh.cb = (CheckBox) item.findViewById(R.id.cb);
	            //让item和ViewHolder绑定在一起
	            item.setTag(vh);
	        } else {
	            //复用ListView给的View
	            item = view;
	            //拿出ViewHolder
	            vh = (ViewHolder) item.getTag();
	        }
	
	        //设置文本内容
	        vh.tv.setText(listViewData.get(position));
	
	        //还原状态
	        vh.cb.setChecked(false);
	
	        if (position % 2 == 0) { //如果是奇数
	            vh.cb.setChecked(true);
	        }
	
	        return item;
	    }
	
	    /**
	     * 用于存放一个ItemView中的控件,由于这里只有两个控件,那么声明两个控件即可
	     */
	    class ViewHolder {
	        TextView tv;
	        CheckBox cb;
	    }
	
	}


----------
参考博客：[https://www.jianshu.com/p/f0408a0f0610](https://www.jianshu.com/p/f0408a0f0610 "ListView优化")
## 使用异步加载图片 ##
如果在adapter中的某些操作需要耗费大量的时间，这个时候就要用到异步线程来进行异步就在数据。比如：现在要加载图片，此时我们需要根据url访问网络得到数据，然后将数据解析为Bitmap设置给View。这些操作如果不进行异步处理而直接放入adapter，可想而知，我们的ListView会有多卡。

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //判断是否有缓存
        if (convertView == null) {
            //通过LayoutInflate实例化布局
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout, parent, false);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
           //通过tag找到缓存的布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsBean newsBean = newsBeanList.get(position);

        String urlString = newsBean.newsIconUrl;
        viewHolder.ivIcon.setTag(urlString); // 将ImageView与url绑定
        // 普通异步加载
        // mImageLoader.showImageByThread(viewHolder.ivIcon,urlString);
		// AsyncTask加载
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon,urlString);
        viewHolder.tvTitle.setText(newsBean.newsTitle);
        viewHolder.tvContent.setText(newsBean.newsContent);
        return convertView;
    }
## 使用分段加载 ##
有些情况下我们需要加载网络中的数据，显示到ListView，而往往此时都是数据量比较多的一种情况，如果数据有1000条，没有优化过的ListView都是会一次性把数据全部加载出来的，很显然需要一段时间才能加载出来，我们不可能让用户面对着空白的屏幕等好几分钟，那么这时我们可以使用分段加载，比如先设置每次加载数据10条，当用户滑动ListView到底部的时候，我们再加载20条数据出来，然后使用Adapter刷新ListView，这样用户只需要等待10条数据的加载时间，这样也可以缓解一次性加载大量数据而导致OOM崩溃的情况。
## 使用分页加载 ##
上面方式其实也不能完全解决OOM崩溃的情况，因为虽然我们在分段中一次只增加10条数据到List集合中，然后再刷新到ListView中去，假如有10万条数据，如果我们顺利读到最后这个List集合中还是会累积海量条数的数据，还是可能会造成OOM崩溃的情况，这时候我们就需要用到分页，比如说我们将这10万条数据分为1000页，每一页100条数据，每一页加载时都覆盖掉上一页中List集合中的内容，然后每一页内再使用分批加载，这样用户的体验就会相对好一些。
## listView错位加载问题 ##
	//将url设为imagView的标识位
	String urlString = newsBean.newsIconUrl; 
	viewHolder.ivIcon.setTag(urlString); // 将ImageView与url绑定
然后再加载的过程中通过url来判断对应imagview位置是否一致来决定是否加载。

	if (mImageView.getTag().equals(mUrl)) { 
	//当url标记和原先设置的一样时，才设置ImageView    
	mImageView.setImageBitmap((Bitmap) msg.obj);
	}