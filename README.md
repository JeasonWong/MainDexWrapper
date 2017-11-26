# MainDexWrapper

### Introduction
Easy way to put classes in main dex.


### Usage
- One of the ways is similar to official realization：

```groovy
multiDexKeepProguard file('./maindex-rules.pro')
```

- Another way is similar to `@Keep `annotations in proguard.

### How to use

#### build.gradle

##### in root-build.gradle

```groovy

buildscript {
    dependencies {
        classpath 'me.wangyuwei:maindexwrapper-plugin:1.0.0'
    }
}

allprojects {
    repositories {
        maven {
            url 'https://dl.bintray.com/wangyuwei/maven'
        }
    }
}

```

##### in lib-build.gradle

```grrovy

apply plugin: 'me.wangyuwei.maindexwrapper'

mainDexWrapper {
    enable true
    keepFile file('./maindex-rules.pro')
}

compile 'me.wangyuwei:maindexwrapper-annotations:1.0.0'

```

#### demo

```java
// 1、add @KeepMainDex
package me.wangyuwei.maindexwrapper.demo;

import me.wangyuwei.maindexwrapper.annotations.KeepMainDex;

@KeepMainDex
public class Demo {

    public class InnerClass {

    }

}

```

```java
// 2、add keep rules in a file.

#-keep class me.wangyuwei.maindexwrapper.demo.Demo {*;}
```


