# RecycleView与ListView区别 #
----------
## RecycleView ##
优点：


1. 封装了viewholder的回收复用
1. 可以使用布局管理器LayoutManager来管理RecyclerView的显示方式：水平、垂直、网络、网格交错布局； 
1. 自定义item的分割条，实现自定义；
1. 可以控制item的添加和删除的动画，非常自由，可以自定义动画，配合具体场景，效果非常棒；
1. 可以动态的在指定位置添加和删除某一项，而列表不会回到顶部，动态的update列表数据（非常需要）；
 
缺点：

1. 不能简单的加头和尾
2. 没有OnItemClickListenter(),需要自己在RecycleView内部自定义列表项的点击事件或则长按事件（按需求自己添加）；
1. 在Material Design中和CardView（和RecycleView同时出现的新控件）配合使用，显示效果非常突出（现在很多新的主流App都使用了这种结构，后面会有demo展示）。
