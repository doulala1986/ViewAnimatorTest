# 动画框架—ViewAnimator

[ViewAnimator](https://github.com/florent37/ViewAnimator) 是一个基于策略模式的动画框架，能够帮助快速的创建动画，进行组合动画，代码简介，便于维护、修改。

##Gradle
```
    compile 'com.github.florent37:viewanimator:1.0.4'
```

##Api
**ViewAnimator** 是所有动画的入口工具类，通过静态方法创建builder，来定义动画细节。


---
###Effects

```
 ViewAnimator.animate(img).translationX(0,100).alpha(0, 1).duration(2000).start();
```

**animate(View... view)：** 创建一组动画，并指定了作用的控件，允许指定多个控件。

**translationX(float... x):** X方向的位移变化，内部使用的是ObjectAnimator实现。

**alpha(float... alpha):** 透明度变化

**backgroundColor(int... colors):** 背景色渐变

**textColor(int... colors)** 文本色渐变

**height(float... height)** 高度变化

**width(float... width)** 高度变化

** rotation(float... rotation)** 旋转，单位度

**pivotX(float pivotX) / pivotY(float pivotY)**  配合rotation()使用,指定旋转中轴x/y坐标

**bounce()** 上下抖动，可以用来做提示动画

**bounceIn()** 放大渐入

**bounceOut()** 渐出

**fadeIn()** alpha(0,1)

**fadeOut()** alpha(1,0)

**fall()** 旋转

**flash()** 闪烁

**swing():**左右抖动

**flipVertical()** 垂直折叠

**flipHorizontal()** 水平折叠

**newsPaper()** 放大渐入的另一种形式

**pulse()** 先放大再缩小

**duration(long duration):** 指定动画的时长，默认是3000ms

---

###Callbacks

```
ViewAnimator.animate(img).translationX(0, 100).andAnimate(btn1).alpha(0, 1).onStart(startListener).thenAnimate(btn3).alpha(0, 1).onStop(stopListener).start();


//callbacks
private AnimationListener.Start startListener = new AnimationListener.Start() {
        @Override
        public void onStart() {
            Toast.makeText(MainActivity.this, "动画1部分启动", Toast.LENGTH_SHORT).show();

        }
    };

private AnimationListener.Stop stopListener = new AnimationListener.Stop() {
        @Override
        public void onStop() {
            Toast.makeText(MainActivity.this, "动画2部分结束", Toast.LENGTH_SHORT).show();

        }
    };

```

**andAnimate(View... view):** 在当前动画组中，添加新的动画元素，允许指定多个控件。

**thenAnimate(View... views):** 创建另一组动画，该动画组将在上一组动画执行结束后启动。

**onStart(AnimationListener.Start startListener)：**
  动画开始事件回调，在每一组动画启动时触发。
  
**onStop(AnimationListener.Stop stopListener)：**
  动画结束事件回调，在每一组动画结束时触发。
  
 ---
 
  ###Path
 
   Path是android提供的一个路径工具类，支持贝塞尔曲线，也支持简单的形状。注意在ViewAnimator中不支持FillType，也不支持多个图形的绘制，如add多个圆或者矩形。
  
  我们可以画一条线：
  ```
        Path path0 = new Path();
        path0.moveTo(400,400);//先移动到400，400
        path0.rLineTo(500,500);//相对400，400向右、下各移动500像素
        //path0.lineTo(500,500);//移动到500，500
        
  ```
  
  我们可以画一个圆：
 
 ```
    Path path1 = new Path();
    path1.addCircle(700,700,300, Path.Direction.CW);
 ```
 我们可以画一个矩形：
 
 ```
   Path path2 = new Path();
   path2.addRect(new RectF(700, 700, 250, 200), Path.Direction.CW);
 ```
** 贝塞尔曲线:**
 
 [贝塞尔曲线的讲解](http://blog.csdn.net/tianjian4592/article/details/46955833)
 
 Path目前只针对低阶，即2阶、3阶贝塞尔曲线进行了实现。
 
 ###二阶贝塞尔曲线
 
 ![](https://doulala1986.gitbooks.io/doulala-android-tech/content/root/3.UI%E5%8F%8AView/20150719190450255.gif)
 
 
**quadTo(float x1, float y1, float x2, float y2)**

x1、y1 代表控制点的 x、y，即一个控制点动态图中的P1，x2、y2 代表目标点的 x、y；
 
 
 ###三阶贝塞尔曲线
 ![](https://doulala1986.gitbooks.io/doulala-android-tech/content/root/3.UI%E5%8F%8AView/20150719190508446.gif)

**cubicTo(float x1, float y1, float x2, float y2, float x3, float y3)**

x1、y1 代表控制点1的 x、y；
x2、y2 代表控制点2的 x、y；
x3、y3 代表目标点的 x、y；
 
 我们使用贝塞尔二阶曲线绘制一个心形路径，设计图如下。
 
 ![](https://doulala1986.gitbooks.io/doulala-android-tech/content/root/3.UI%E5%8F%8AView/7D9C0411-1FBB-44EE-A1C6-FF0788C19DA2.png)
 
 
 ```
  Path path4=new Path();
  path4.moveTo(700,700);
  path4.quadTo(100,400,700,1500);
  path4.quadTo(1300,400,700,700);
  ViewAnimator.animate(tiny).path(path4).start();
 
 ```
 
** 如何确定贝塞尔曲线的控制点？**
 
 
 
 
 
  ---
  
 ##others
 
 
 ```
//需要注意dp作用域是在一个animate组
private void dp() {
   //x轴方向位移200dp,y轴方向上位移200px
   ViewAnimator.animate(img).dp().translationX(0, 200).andAnimate(img).translationY(0,200).start();
}

 ```
** dp()：**在同一个animation内，把长度单位由px改变成dp。
 
 
---
 ```
  private void interpolator(){
     ViewAnimator.animate(img).dp().translationX(0, 200).interpolator(new AccelerateInterpolator()).start();
 }
 ```
 



**interpolator(Interpolator interpolator):** 设置动画加速度，详细的加速度见animation文档。
 
~~accelerate()~~
 
~~descelerate()~~
 
 
 ---
 ```
private void custom() {
  ViewAnimator.animate(img)
    .custom(new AnimationListener.Update<ImageView>() {
          @Override
          public void update(ImageView view, float value) {
             view.setAlpha(value);
              }
        }, 0, 1).duration(5000).start();
}
 ```
 
** custom(final AnimationListener.Update update, float... values)：** 允许我们进行动画的自定义，需要注意的是，这里的duration将失效。
 
 
