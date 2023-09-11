
### [安卓使用RecyclerView+HorizontalScrollView 实现Item整体横向滑动(V1)](https://www.jianshu.com/p/75bba86dd61c) 效果的优化版本(V2),自定义RecyclerView+自定义ViewGroup实现,完美保留RecyclerView的缓存机制

![gif效果](https://gitee.com/Jeromeer/horizontal-scroll-demo/raw/master/screen/1667873134063.gif)

### 版本对比解决以下几个点
#### 1.V1版本实现方式需要写多个adapter,和布局的嵌套,费时费力;V2版本只有一个adapter
#### 2.V1版本在每一行数据过多时滑动,会出现上下几行数据错位的现象,这是由于使用HorizontalScrollView的fling效果导致的,暂时不能解决;V2版本完全自定义ViewGroup替代,总计不到300行代码,逻辑简单粗暴

#### 具体实现原理也很简单,两个类加起来不到500行代码
1.RecyclerView的addView会在每一个LayoutManager往其添加View的时候回调,也就是RecyclerView获取缓存失败时,创建新的View的时候调用,在这里将创建的View加到集合中保存下来,用于同步做左右滚动

2.RecyclerView的item也需要使用自定义的ViewGroup包裹,这样就可以监听横向滚动,以便同步滚动所有item

3.自定义ViewGroup时,需要处理手势,这里拦截并响应了所有点击事件,这样会有个小瑕疵,它包裹的子view不能有点击事件了,不过每个item的点击事件我已经处理好了,如果子view实在有需要点击事件,那就需要自己处理了


## 其他不多说,代码是最好的解释