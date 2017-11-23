# MainDexWrapper
Easy way to put classes in main dex.

One of the ways is similar to official realization：

```groovy
multiDexKeepProguard file('./maindex-rules.pro')
```

### How to use

```groovy
apply plugin: 'me.wangyuwei.maindexwrapper'

mainDexWrapper {
    enable true
    keepFile file('./maindex-rules.pro')
}
```

### TODO

- support @KeepMainDex



