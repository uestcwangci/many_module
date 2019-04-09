# View绘制流程 #
----------
参考博客：[https://lrh1993.gitbooks.io/android_interview_guide/content/android/basis/custom_view.html](https://lrh1993.gitbooks.io/android_interview_guide/content/android/basis/custom_view.html)

参考博客：[https://www.jianshu.com/p/1dab927b2f36](https://www.jianshu.com/p/1dab927b2f36)


从View的测量、布局和绘制原理来看，要实现自定义View，根据自定义View的种类不同，可能分别要自定义实现不同的方法。但是这些方法不外乎：
onMeasure()方法，onLayout()方法，onDraw()方法。

**onMeasure()方法**：单一View，一般重写此方法，针对wrap_content情况，规定View默认的大小值，避免于match_parent情况一致。ViewGroup，若不重写，就会执行和单子View中相同逻辑，不会测量子View。一般会重写onMeasure()方法，循环测量子View。

**onLayout()方法**:单一View，不需要实现该方法。ViewGroup必须实现，该方法是个抽象方法，实现该方法，来对子View进行布局。

**onDraw()方法**：无论单一View，或者ViewGroup都需要实现该方法，因其是个空方法