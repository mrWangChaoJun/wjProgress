# WJRecyclerview


控件名称
=======
进度条，包含圆形进度条和条形进度条。 

版本
===

![wjprogress 1.0.0](https://img.shields.io/badge/wjprogress-1.0.0-yellow)
<br/>
![Gradle 4.6](https://img.shields.io/badge/Gradle-4.6-yellow)
<br/>
![SdkVersion 28](https://img.shields.io/badge/SdkVersion-28-yellow)
<br/>
![MinSdkVersion 16](https://img.shields.io/badge/MinSdkVersion-16-yellow)

安装教程
=======
点击 <https://jitpack.io/> 搜索 *mrWangChaoJun/wjprogress*  
或者在build.gradle中加入以下代码  
```
dependencies {  
    implementation 'com.github.mrWangChaoJun:wjprogress:1.0.0'  
}
```
使用说明
=======
***Version 1.0.0***
<br/>
<br/>
1、在xml中添加控件

```
<cn.mr.wangChaoJun.wjprogress.WJProgress
        android:id="@+id/progress"
        android:layout_width="170dp"
        android:layout_height="170dp"
        />
```

2、在java文件中使用

```
WJProgress.setProgress(progressNumber);
```
也可以添加动画

```
WJProgress.startAnimation();
```
<br/>
设置进度

```
WJProgress.setProgress(float p);
```
开启动画

```
WJProgress.startAnimation();
```
3、控件属性
<br/>
<br/>

 | 属性 | 作用 | 默认值 | 类型 |
 | ----- | ----- | ----- | ----- |
 | wj_width | 进度条宽度 | 10 | float |
 | wj_backgroundColor | 进度条背景颜色 | 灰色 | color|
 | wj_color | 进度条颜色 | 白色 | color|
 | wj_showPoint | 是否显示圆点 | 不显示 | boolean|
 | wj_showProgressText | 是否在显示进度 | 不显示 | boolean|
 | wj_progressTextColor | 进度提示字体的颜色 | 黑色 | color|
 | wj_progressTextSize | 进度提示字体大小 | 14 | integer|
 | wj_beforeTipText | 前置提示文字 | 无 | string|
 | wj_afterTipText | 后置提示文字 | 无 | string|
 | wj_showPercent | 是否显示% | 显示 | boolean|
 | wj_progressType | 进度条类型 | 圆形 | enum |
 | wj_textGravity | 提示文字的位置 | 左边 | enum |
 
 ***wj_progressType***
 <br/>
 <br/>
 
 | 圆形 | 条形 |
 | ----- | ----- |
 | circular | straightLine |
 
 ***wj_textGravity，该属性只有在条形进度条的时候起作用***
 <br/>
 <br/>
 
 | 左边 | 中间 | 右边 |
 | --- | --- | --- |
 | left | center | right |
