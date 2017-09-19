# 统一修改 Options

有时候我们需要对所有请求的 Options 进行检查并修改，例如列表滑动中暂停加载功能就需要检查所有的 DisplayOptions 并修改 requestLevel 属性

### 使用

Sketch 提供了 [OptionsFilter] 接口来实现此功能

1.首先你需要实现 [OptionsFilter] 接口，定义你的过滤器，如下：

```java
public class TestOptionsFilter implements OptionsFilter {

    @Override
    public void filter(DownloadOptions options) {
        // 在这里检查并修改 options
    }
}
```

2.然后通过 [OptionsFilterRegistry] 注册 TestOptionsFilter 即可，如下：

```java
OptinsFilterRegistry optinsFilterRegistry = Sketch.with(context).getConfiguration().getOptinsFilterRegistry(); 
optinsFilterRegistry.add(new OptionsFilterRegistry());
```

### 内置的 OptionsFilter

Sketch 内置了四种 [OptionsFilter]，如下：
* [LowQualityOptionsFilter]：用来控制全局开启低质量模式
* [InPreferQualityOverSpeedOptionsFilter]：用来控制全局开启质量优先模式
* [PauseDownloadOptionsFilter]：用来控制暂停下载，配合 [MobileNetworkPauseDownloadController] 可实现移动网络下暂停下载，另参考 [移动网络下暂停下载图片，节省流量][pause_download] 
* [PauseLoadOptionsFilter]：用来控制暂停加载，配合 [ScrollingPauseLoadManager] 可实现列表滑动中暂停加载，另参考 [列表滑动时暂停加载图片，提升列表滑动流畅度][pause_load]

上述四个 [OptionsFilter], [Configuration] 和 [OptionsFilterRegistry] 都提供了开关控制，详情参考源码即可

[OptionsFilter]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/OptionsFilter.java
[OptionsFilterRegistry]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/OptionsFilterRegistry.java
[LowQualityOptionsFilter]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/LowQualityOptionsFilter.java
[InPreferQualityOverSpeedOptionsFilter]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/InPreferQualityOverSpeedOptionsFilter.java
[PauseDownloadOptionsFilter]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/PauseDownloadOptionsFilter.java
[PauseLoadOptionsFilter]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/PauseLoadOptionsFilter.java
[MobileNetworkPauseDownloadController]: ../../sketch/src/main/java/me/xiaopan/sketch/optionsfilter/MobileNetworkPauseDownloadController.java
[ScrollingPauseLoadManager]: ../../sample/src/main/java/me/xiaopan/sketchsample/util/ScrollingPauseLoadManager.java
[pause_download]: pause_download.md
[pause_load]: pause_load.md
[Configuration]: ../../sketch/src/main/java/me/xiaopan/sketch/Configuration.java