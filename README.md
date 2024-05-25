# 背单词

## 注意

1.在你每次更新项目时,请在README中"更新"一栏写下具体更新内容和时间,
在"请求"一栏写下你请求co-author需要实现的内容
!记得及时拉取项目的最新状态和更新项目的README,谢谢!

## TODO List

1. 解析json文件(zsj,解决) （留言：您解析完json后也许可以把ymc.init.WordBookInitializer实现一下，Word类应该需要加一些字段 zsj:请求全部实现）
2. 解析md文件(zsj)
3. 背单词模式(ymc)
4. 读文章模式(ymc)
5. audio(zsj,已实现)
6. selectedWords(zsj,正在做)
7. FX界面(zsj,正在做)

(留言：目前的GUI只是我为了测试读文章模式能不能运行简单写的，您还是按计划写一个FX的美观版GUI吧
zsj:好,我们这周先把基本逻辑理顺,预计下周或更早开始写界面)
(zsj:我们的demo在运行时是单线程的,我今天加入了声音播放,声音播放和内容显示不能同时进行,所以我准备在以后的fx逻辑中加入多线程,实现音效播放和内容显示同时进行)
(zsj:WordBook中新增了searchWordInEng和searchWordInCh,可以在本地词书中搜索)
(zsj:修改了UserProgress中的private Map<String, Map<Word, ReviewData>> reviewCounts;根据ReviewData确定是否需要复习)

## 模式

## 配置(正在做，已有demo，json解析好后进一步完善)

学习量
添加词书
随机选取算法
熟练度

学单词部分有了一个demo，但是需要进一步完善：

- 各种算法还没实现

## 算法

曲线

### 背单词模式（正在做）

1.给出一个单词 认识/不认识 (差不多实现了一个demo)

例句 
题目

### 单词的信息

### 复习题型

看英语选汉语
反过来
听音选词
完型

### 读文章功能（正在做）
- 已完成从某个网站每天更新20篇文章的功能


做了一个读文章功能的demo

### 词典功能（正在做）
- 上网查词功能（做了一个demo，在translator下面）已经集成进UI了，但比较简陋

- 这个功能在GUI时可以与读文章功能联动（还没做）



