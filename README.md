# RxJava-demo
rxJava练习

**主要测试了RxJava的使用**

**android hook技术的使用demo，通过hook View的点击事件演示**

## android 模块化开发

###**路由采用ARouter**

**1、ARouter的使用方法**

```
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
            arguments = [ moduleName : project.getName() ]
            }
        }
    }
}

dependencies {
    // 替换成最新版本, 需要注意的是api
    // 要与compiler匹配使用，均使用最新版可以保证兼容
    compile 'com.alibaba:arouter-api:x.x.x'
    annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
    ...
}
```

**2、然后在Application中初始化**

```
if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
    ARouter.openLog();     // 打印日志
    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
}
ARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
```

**3、接着使用注解标记要跳转的Activity**

```
@Route(path = "/module_b/activity_b")
public class ActivityB extend Activity {
    ...
}
```

**4、然后在ActivityA中这样跳转到ActivityB**

```
ARouter.getInstance().build("/module_b/activity_b").navigation();
```
这样就完成了不同业务之间的跳转，是不是超级简单？

**5、跳转传参数**

```
//  跳转并携带参数
ARouter.getInstance().build("/module_b/activity_b")
            .withLong("key1", 666L)
            .withString("key3", "888")
            .withObject("key4", new Test("Jack", "Rose"))
            .navigation();
```

**6、在ActivityB中获取参数有两种方式，一种是普通Activity那样getIntent().getXXX
    加一种是使用@Autowired注解的方式**
    
```
@Route(path = "/test/activity")
public class Test1Activity extends Activity {

    @Autowired
    public String name;
    
    @Autowired
    int age;
    
    @Autowired(name = "girl") // 通过name来映射URL中的不同参数
    boolean boy;
    
    @Autowired
    TestObj obj;    // 支持解析自定义对象，URL中使用json传递
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ARouter.getInstance().inject(this);
    

        // ARouter会自动对字段进行赋值，无需主动获取
        Log.d("param", name + age + boy);
    }
}
```    

**还有一种情况，业务A是要使用业务C中的某个服务或者说某种功能，并不是想跳转Activity**

**7、这种情况下就要使用到IProvider**

```
// 声明接口,其他组件通过接口来调用服务
public interface HelloService extends IProvider {
    String sayHello(String name);
}

// 实现接口
@Route(path = "/service/hello", name = "测试服务")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
    return "hello, " + name;
    }

    @Override
    public void init(Context context) {

    }
}
```

**申明的接口放在base module中，接口的实现放在具体功能实现的module中。
  然后在要使用这一服务的module中这样使用：**

```
HelloService helloService =   (HelloService) ARouter.getInstance().build("/service/hello").navigation();

helloService.sayHello("Vergil");
```

这样就实现了调用其他业务服务

**8、还有还可能通过ARouter获取其他业务模块的Fragment:**

```
// 获取Fragment
Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
```

**9、下面是一些特殊用法：**

```
// 构建标准的路由请求，startActivityForResult
// navigation的第一个参数必须是Activity，第二个参数则是RequestCode
ARouter.getInstance().build("/home/main", "ap").navigation(this, 5);
```
上面是从Activity调startActivityForResult 但是Arouter没有API支持从fragment调startActivityForResult
这种情况要怎么办呢？


**可以这样做**

```
 Postcard postcard = ARouter.getInstance().build("/module2/activity");
 LogisticsCenter.completion(postcard);
 Class<?> destination = postcard.getDestination();
```

这里得到的destination类就是我们要跳转的类，这样fragment的startActivityForResult就好办了

```
Intent intent = new Intent(getContext(),destination);
startActivityForResult(intent,requestCode);
```

## MVP 开发

