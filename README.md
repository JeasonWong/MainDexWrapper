# MainDexWrapper
Easy way to put classes in main dex.

One of the ways is similar to official realization：

```groovy
multiDexKeepProguard file('./maindex-rules.pro')
```

Another way is similar to `@Keep `annotations in proguard.

### How to use

#### build.gradle

```groovy
apply plugin: 'me.wangyuwei.maindexwrapper'

mainDexWrapper {
    enable true
    keepFile file('./maindex-rules.pro')
}

compile project(':annotations')
```
#### demo

```java
package me.wangyuwei.maindexwrapper.demo;

import me.wangyuwei.maindexwrapper.annotations.KeepMainDex;

@KeepMainDex
public class Demo {

    public class InnerClass {

    }

}

```


