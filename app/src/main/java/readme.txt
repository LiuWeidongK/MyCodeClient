1.实现注册与登入的逻辑处理                              √
2.实现teacher与student的选择 不可选的设置               √
3.实现"我的"页面的逻辑设定
4.注册之后在"我的"页面中显示 提示登入等等...
5.蓝牙未打开 显示提示等等...

SharedPreferences:
    USE_COUNTS ->
        COUNT 登入次数 用于判断是否为初次登入
    USER_TYPE ->
        用户类型(TYPE):[STUDENT/TEACHER]
        是否注册(IS_REGIST)->此处未实现->Mine页面
    TEACHER_INFO ->
        除了密码
    STUDENT_INFO ->
        除了密码
    REMEMBER ->
        用户名:USERNAME
        密码:PASSWORD
        记住密码选定状态:ISCHECKED

    ※未改：
    1.账号密码 SQLite 存在本地不安全            √（No necessary）
    2.登入中的记住密码                          √（SharedPreferences）
    3.登入中的无法登入
    4.密码加密                                  √（MD5）