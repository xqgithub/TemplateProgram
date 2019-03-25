package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by beijixiong on 2019/3/20.
 * Dagger2 测试
 * 是一个依赖注入框架，butterknife也是一个依赖注入框架。不过butterknife，最多叫奶油刀，Dagger2被叫做利器啊，他的主要作用，就是对象的管理，其目的是为了降低程序耦合
 */

public class TestDagger2Activity extends BaseActivity {
    /**
     * Inject用于标记需要注入的依赖，或者标记用于提供依赖的方法
     * 一，构造器注入
     * 1.告诉Dagger2可以使用这个构造器构建对象。如Rose类
     * 2.注入构造器所需要的参数的依赖。 如Pot类，构造上的Rose会被注入
     * 二，属性注入
     * 1.被标注的属性不能使用private修饰，否则无法注入
     * 2.属性注入也是Dagger2中使用最多的一个注入方式
     * 三，方法注入
     * 1.标注在public方法上，Dagger2会在构造器执行之后立即调用这个方法
     * 2.该依赖需要this对象的时候，使用方法注入可以提供安全的this对象，因为方法注入是在构造器之后执行的
     */


    /**
     * Component则可以理解为注入器，在注入依赖的目标类TestDragger2Activity使用Component完成注入 Inject用于标记需要注入的依赖，或者标记用于提供依赖的方法
     * 一，两种方式定义方法
     * 编译后Dagger2就会为我们生成DaggerXXXComponent这个类，它是我们定义的xxxComponent的实现，在目标类中使用它就可以实现依赖注入了
     * 1.void inject(目标类 obj);Dagger2会从目标类开始查找@Inject注解，自动生成依赖注入的代码，调用inject可完成依赖的注入。
     * 2.Object getObj(); 如：Pot getPot();Dagger2会到Pot类中找被@Inject注解标注的构造器，自动生成提供Pot依赖的代码，这种方式一般为其他Component提供依赖
     */

    /**
     * Module需要和 Provide是需要一起使用的时候才具有作用的，并且Component也需要指定了该Module的时候
     * 1.Module是告诉Component，可以从这里获取依赖对象。Component就会去找被@Provide标注的方法
     * 2.Provide标注的方法，相当于构造器的@Inject，可以提供依赖
     * 3.Component可以指定多个@Module的，如果需要提供多个依赖的话
     */

    /**
     * Qualifier和Named,使用作用一样
     * 1.Qualifier是限定符
     * 2.Named则是基于String的限定符
     * 3.两个相同的依赖（都继承某一个父类或者都是先某一个接口）可以提供给高层时，那么程序就不知道我们到底要提供哪一个依赖，因为它找到了两个。这时候我们就可以通过限定符为两个依赖分别打上标记，指定提供某个依赖
     */

    /**
     * Component的dependence和SubComponent
     * 1.Component可以依赖于其他Component，可以使用@Component的dependence，也可以使用@SubComponent，这样就可以获取其他Component的依赖了
     * 2.比较适合使用Subcomponent的几个场景
     * (1).很多工具类都需要使用到Application的Context对象，此时就可以用一个Component负责提供，我们可以命名为AppComponent。需要用到的context对象的SharePreferenceComponent，ToastComponent就可以它作为Subcomponent存在了
     * 3.Component dependencies和Subcomponent区别
     * (1).Component dependencies 能单独使用，而Subcomponent必须由Component调用方法获取
     * (2).Component dependencies 可以很清楚的得知他依赖哪个Component， 而Subcomponent不知道它自己的谁的孩子……真可怜
     * (3).使用上的区别，Subcomponent就像这样DaggerAppComponent.plus(new SharePreferenceModule());使用Dependence可能是这样DaggerAppComponent.sharePreferenceComponent(SharePreferenceComponent.create())
     */

    /**
     * Scope和Singleton
     * 1.Scope是用来管理依赖的生命周期的,@Scope的作用了，它的作用只是保证依赖在@Component中是唯一的，可以理解为“局部单例”
     * 2.Singleton则是@Scope的默认实现
     * 3.命名是需要慎重考虑
     */

    /**
     * MapKey和Lazy
     * 1.这个注解用在定义一些依赖集合（目前为止，Maps和Sets）,@MapKey注解目前只提供两种类型 - String和Enum
     * 2.Dagger2还支持Lazy模式，通过Lazy模拟提供的实例，在@Inject的时候并不初始化，而是等到你要使用的时候，主动调用其.get方法来获取实例
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_dragger2;
    }
}
