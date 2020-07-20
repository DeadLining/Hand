### 一张动图彻底搞懂 Git 分支的 master、origin、origin/master 区别~

[link]: https://blog.csdn.net/u013645219/article/details/104866348?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase



### git fetch & pull详解

[link]: https://www.cnblogs.com/runnerjack/p/9342362.html



### Git常用命令&git merge心得

[link]: https://www.jianshu.com/p/2fa78bafc8f3



### Git merge最简洁用法

一、开发分支（dev）上的代码达到上线的标准后，要合并到 master 分支

```
git checkout dev

git pull

git checkout master

git merge dev

git push -u origin master
```

二、当master代码改动了，需要更新开发分支（dev）上的代码

```
git checkout master

git pull

git checkout dev

git merge master

git push -u origin dev
```



### git-merge完全解析

[link]: https://www.jianshu.com/p/58a166f24c81



### git分支合并之Fast-forword(快进方式)原理剖析

[link]: https://blog.csdn.net/xr597657148/article/details/84190242



### IntelliJ IDEA下的使用git

[link]: https://www.cnblogs.com/pejsidney/p/9199115.html