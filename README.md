# AutoCreateValues
Android中针对不同分辨率做适配时，理论上来说，我们只需要配置某一个values（例如values-w360dp）的资源文件就好了，但是我们还得考虑兼容240dp ，270dp ，320dp，400dp等设备。
作为一个高效(偷懒)的程序员，我们明显不会傻乎乎的在每个路径下都计算dimens后加入进去，我们想要一个双击就能生成多套配置文件的方案。

###特点

- 能够自动生成values-wXXXdp文件夹
- 支持dimen.xml中dp和sp

### 使用

- 在Config.java中进行配置
```java
    //需要适配的设备配置
    public final static int[] supportDevices = { 853 };

    //dimens.xml 计算基础 1280dp
    public final static int defaultValue = 1280;

    //values 文件夹路径 。默认在此路径下存放dimens.xml
    public final static String path = "E:\\workspaceAS\\app\\src\\main\\res\\values-w1280dp";
```
- 运行MyClass.java

### Thanks

> [binye33333/android](https://github.com/binye33333/android/)