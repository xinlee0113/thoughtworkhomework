产品需求：

Production requirements
• The view consists of profile image, avatar and tweets list
• For each tweet, there will be a sender, optional content, optional images and comments, ignore the tweet which does not contain a content and images
• A tweet contains from 0 to 9 images, make sure the layout is aligning
• Load all tweets in memory at first time, and get 5 of them each time from memory asynchronously
• Show 5 more while user pulling up the view at the bottom of view
• Pulling down the view to refresh, only first 5 items are shown after refreshing
• The layout in different Android devices(except tablet) should be considered

•视图包括个人资料图片、头像和推文列表
•对于每条tweet，将有一个发送者、可选内容、可选图像和评论，忽略不包含内容和图像的tweet
•一条tweet包含0到9张图片，确保布局对齐
•第一次将所有tweet加载到内存中，每次从内存异步获取5条tweet
•当用户在视图底部向上拉视图时，再显示5个
•下拉视图刷新，刷新后只显示前5项
•应考虑不同Android设备（平板电脑除外）的布局

技术要求：
Technical requirements
􏰀 • The data Json will be hosted in http://thoughtworks-ios.herokuapp.com/ 􏰀 Deployment Target should be API 14+
􏰀 • Utilise Git for source control 使用git做代码管理
􏰀 • 3rd libs is allowed  允许使用第三方框架
􏰀 • Unit tests is appreciated  单元测试
􏰀 • Keep your code clean as much as possible  代码简洁


Json data specification
􏰀 Request user info from url: http://thoughtworks-ios.herokuapp.com/user/jsmith
􏰀 Request tweets from url: http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets



1。 使用mvvm的设计模式
2。 recycleview 、 retrofit 、jetpack
3。 开发框架：https://developer.android.google.cn/jetpack/docs/guide
4. PagedList:https://www.jianshu.com/p/ff5c711bb7a1


20200816
1. 调整布局
2. 本地缓存
3. lint
4. 屏幕适配

