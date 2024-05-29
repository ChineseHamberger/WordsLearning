# 背单词

## 注意

在你每次更新项目时,**请在README中"update"一栏写下具体更新内容和时间**,
!记得**及时拉取项目的最新状态**和更新项目的README,谢谢!

## TODO List

1. 解析json文件(zsj,解决) （留言：您解析完json后也许可以把ymc.init.WordBookInitializer实现一下，Word类应该需要加一些字段 zsj:请求全部实现）
2. 解析md文件(zsj)
3. 背单词模式(ymc，已实现)
4. 读文章模式(ymc，已实现)
5. audio(zsj,已实现)
6. selectedWords(zsj,已实现)
7. FX界面(zsj,正在做)

## update

5.27 ymc:基本完成demo,学习单词功能基本稳定 zsj:fx逻辑正在完善,新增全局设置GlobalSetting
5.28 zsj:删除了WordBookLoader类,这个功能与localStorage中的getWordBook重复,将这个方法改名为loadWordBook
- （您这个版本更新后，我有时会遇到GUI启动不起来，命令行输出load WordBook error或者Load UserProgress error的错误信息，有时不会遇到这个bug；遇到这个bug只要删除WordBooks文件夹和Users文件夹就可以运行起来；有待检测这个bug的成因）


5.29 ymc：实现了四种复习题功能，目前的机制是每天应该复习的单词会首先出现一遍问用户认不认识；之后再出现一遍随机出一道题，如果答对了就pass，答错了之后会再随机出一道题。
-(为了测试复习功能，目前开放了每日多次开始学习的功能，正式版将关闭这一功能，学完之后再学会显示"今日任务已完成，积少成多别贪心！")


## log

1.Done-(留言：目前的GUI只是我为了测试读文章模式能不能运行简单写的，您还是按计划写一个FX的美观版GUI吧
zsj:好,我们这周先把基本逻辑理顺,预计下周或更早开始写界面)
2.Done-(zsj:我们的demo在运行时是单线程的,我今天加入了声音播放,声音播放和内容显示不能同时进行,所以我准备在以后的fx逻辑中加入多线程,实现音效播放和内容显示同时进行)
3.Done-(zsj:WordBook中新增了searchWordInEng和searchWordInCh,可以在本地词书中搜索)
4.Done-(zsj:修改了UserProgress中的private Map<String, Map<Word, ReviewData>> reviewCounts;根据ReviewData确定是否需要复习)
5.Done-(已实现)(zsj:实现了选择算法,排除了一些空指针错误,优化了一部分逻辑,demo应该能完整比较流畅的运行,现在demo体验感不好的地方是不能**中途退出下次进来继续保留进度**,希望您能给我一些接口)
6.Done-(ymc:实现了阅读文章时的点击查词功能)
7.Done-(ymc:实现了中途退出功能)
8.Done-(ymc:修复了一个bug：wordSelectionAlgo里面getWordsForLearning方法中，计数器cnt没有递增，导致无法正确判断是否达到每日学习配额。)
9.Done-(ymc:对Storage储存逻辑进行了大修，现在不同单词书的进度独立保存了）
10.Done-(ymc:修复了学习单词时的许多bug，目前学习单词功能基本稳定了)

## 模式

## 配置(已完成)

学习量
添加词书
随机选取算法
熟练度

## 算法（已完成）

曲线

### 背单词模式（正在做）

1.给出一个单词 认识/不认识 (已完成)

例句 
题目

### 单词的信息

### 复习题型(已实现)

看英语选汉语
看汉语选英语
听音选词
完型

### 读文章功能（已完成）
- 已完成从某个网站每天更新20篇文章的功能
做了一个读文章功能的demo

### 词典功能（已完成）
- 上网查词功能（已完成）
- 这个功能在GUI时可以与读文章功能联动（已完成）



