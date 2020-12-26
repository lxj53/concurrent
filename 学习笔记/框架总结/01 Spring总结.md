# 1 Spring启动流程

![image-20201226221934717](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201226221934.png)

## 1.1 BeanDefinitionReader作用

spring支持可扩展性，可支持不同的配置文件类型，用户可自定义实现配置文件的解析



## 1.2 BeanFactoryPostProcessor

支持BeanFactory增强，如数据库连接池配置文件中的占位符，如何将配置文件中的占位符替换为具体的值，可通过BeanFactoryPostProcessor扩展。



## 1.3 实例化

实例化操作就是在堆内存中申请空间



## 1.4 初始化

1. 对属性赋值
2. 执行XXXAware接口相应的实现逻辑，作用就是bean中可以拿到XXXAware中的信息（如bean的名称、environment）
3. BeanPostProcessor的before方法，作用就是对生成的bean扩展，如Spring中常见的AOP，在这里生成动态代理对象
4. init-method方法，执行对象自定义的初始化方法
5. BeanPostProcessor的after方法，作用就是对bean扩展