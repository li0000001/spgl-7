# Android Studio 快速开始指南

## 项目概览

这是一个完整的、可以直接在 Android Studio 中运行的 Kotlin/Jetpack Compose 项目。

## 系统要求

- **Android Studio**: Flamingo 版本或更高版本（推荐最新版本）
- **Java Development Kit (JDK)**: 11 或更高版本
- **Android SDK**: API Level 34（自动下载）
- **Gradle**: 8.1.0（通过 Gradle Wrapper 提供）

## 快速开始步骤

### 1. 在 Android Studio 中打开项目

1. 打开 Android Studio
2. 点击 **File** → **Open**（或 **Open File** 在欢迎页面）
3. 选择项目根目录（包含 `gradlew` 和 `settings.gradle.kts` 的目录）
4. 点击 **Open** 按钮

### 2. Android Studio 会自动：

- 识别这是一个 Gradle 项目
- 下载 Gradle Wrapper（gradle-8.1）
- 下载并配置 Android SDK（如需要）
- 下载所有必要的依赖库
- 构建项目

**等待过程**: 首次打开可能需要 5-15 分钟，具体取决于你的网络速度和机器性能。

### 3. 配置本地 SDK 路径（如果需要）

Android Studio 会自动检测或提示你下载 Android SDK。如果需要手动配置：

1. 点击 **File** → **Project Structure**
2. 在左侧选择 **Project**
3. 确保 **SDK Location** 指向正确的 Android SDK 路径
4. 如果你有 `local.properties` 文件，它会自动使用该文件中的配置

### 4. 创建或使用虚拟设备

#### 方式 A：使用 Android 虚拟设备 (AVD)

1. 打开 **Device Manager**（Tools → Device Manager）
2. 点击 **Create Device**
3. 选择一个设备（例如 Pixel 6 或 Pixel 7）
4. 选择 API 级别 **34** 或更高
5. 完成创建

#### 方式 B：使用真实 Android 设备

1. 在你的设备上启用开发者模式（连续点击关于手机中的编译号 7 次）
2. 启用 USB 调试
3. 用 USB 线连接到电脑

### 5. 运行应用

#### 方式 1：直接点击运行

1. 在 Android Studio 顶部工具栏，确保选中了一个设备或虚拟机
2. 点击绿色的 **Run** 按钮（或按 **Shift + F10**）
3. 选择 **app**
4. 应用会编译并在选中的设备上运行

#### 方式 2：使用菜单

1. 点击 **Run** → **Run 'app'**
2. 选择你要运行的设备

#### 方式 3：使用快捷键

- Mac: **Control + R** 或 **Shift + F10**
- Windows/Linux: **Shift + F10**

### 6. 等待应用启动

第一次运行可能需要 1-3 分钟来编译和部署应用。后续运行会更快。

## 项目结构

```
project/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/myapplication/
│   │       │   ├── MainActivity.kt              # 应用入口
│   │       │   ├── data/                        # 数据层
│   │       │   │   ├── Product.kt
│   │       │   │   ├── ProductDao.kt
│   │       │   │   ├── AppDatabase.kt
│   │       │   │   └── Converters.kt
│   │       │   ├── reminder/                    # 提醒系统
│   │       │   │   ├── AlarmScheduler.kt
│   │       │   │   ├── CalendarManager.kt
│   │       │   │   └── ReminderReceiver.kt
│   │       │   ├── ui/                          # UI 层
│   │       │   │   ├── screens/
│   │       │   │   ├── components/
│   │       │   │   └── theme/
│   │       │   ├── viewmodel/
│   │       │   │   └── ProductViewModel.kt      # 数据和 UI 逻辑
│   │       │   └── navigation/
│   │       │       └── AppNavigation.kt         # 导航设置
│   │       └── res/                             # Android 资源
│   │           ├── values/
│   │           │   ├── strings.xml
│   │           │   └── themes.xml
│   │           ├── drawable/
│   │           └── mipmap-*/
│   └── build.gradle.kts
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew                                      # Gradle 运行脚本

```

## 常见问题 (FAQ)

### Q: 应用无法找到 Android SDK
**A**: 
1. 点击 **File** → **Project Structure**
2. 确保 **SDK Location** 正确指向你的 Android SDK 目录
3. 如果没有 SDK，点击 **Download SDK** 按钮

### Q: 编译错误："Cannot resolve symbol 'LocalDate'"
**A**: 
1. 检查项目配置中的 `targetSdk` 和 `compileSdk` 是否为 34 或更高
2. 执行 **File** → **Invalidate Caches** → **Invalidate and Restart**
3. 重新构建项目

### Q: Gradle 同步失败
**A**:
1. 点击 **File** → **Sync Now**
2. 如果仍然失败，执行 `./gradlew clean` 然后重试
3. 检查网络连接（Gradle 需要下载依赖）

### Q: 应用运行缓慢或崩溃
**A**:
1. 确保虚拟设备配置了足够的 RAM（推荐 4GB 或更高）
2. 在设备或虚拟机上启用硬件加速
3. 检查 Logcat 窗口查看具体错误

### Q: 权限错误
**A**: 
这个项目需要以下权限。Android Studio 会在首次运行时自动请求：
- READ_CALENDAR
- WRITE_CALENDAR
- POST_NOTIFICATIONS (Android 13+)
- SCHEDULE_EXACT_ALARM

在运行时授予这些权限即可。

### Q: 如何在真实设备上运行？
**A**:
1. 在设备上启用开发者模式和 USB 调试
2. 用 USB 线连接到电脑
3. 在 Android Studio 中，应该自动检测到你的设备
4. 点击 Run 选择你的设备

## 功能说明

### 应用功能

✅ **商品管理**
- 添加、编辑和删除商品
- 输入生产日期和保质期或直接输入过期日期
- 查看商品详情

✅ **提醒系统**
- 四种提醒类型：通知、闹钟、双重、无
- 自定义提前提醒天数和时间
- 与系统日历集成

✅ **UI 特性**
- 分类标签：全部、有效、已过期
- 按过期日期排序
- 显示剩余天数
- 完整的中文界面

## 开发相关

### 构建变体

- **Debug**: 开发构建，包含调试信息
- **Release**: 发布构建，已优化

### 构建命令 (如果使用终端)

```bash
# 清理构建
./gradlew clean

# 构建 Debug APK
./gradlew assembleDebug

# 运行调试
./gradlew installDebug

# 构建 Release APK
./gradlew assembleRelease

# 运行所有测试
./gradlew test

# 检查依赖
./gradlew dependencies
```

### 技术栈

- **Kotlin** 1.9.10
- **Jetpack Compose** 最新版
- **Material Design 3**
- **Room 2.6.1** (数据库)
- **Navigation Compose 2.7.5**
- **Accompanist Permissions 0.33.2-alpha**

## 调试技巧

### Logcat 输出

- 打开 **Logcat** 窗口：**View** → **Tool Windows** → **Logcat**
- 按 **package name** 筛选日志
- 按 **Log Level** 筛选（Info, Warning, Error 等）

### 调试应用

1. 在代码中设置断点（点击行号左侧）
2. 点击 Debug 按钮（虫子图标）而不是 Run
3. 使用调试工具栏单步执行代码

### 检查数据库

- 在 Logcat 中搜索 SQL 语句
- 使用 **Database Inspector**（View → Tool Windows → App Inspection）

## 下一步

- 查看 `README.md` 了解更多项目详情
- 查看源代码中的注释和文档
- 根据需要修改 `strings.xml` 自定义应用名称和文字
- 在 `MainActivity.kt` 中修改应用的启动行为

## 获取帮助

如有问题，请检查：
1. Android Studio 的官方文档
2. 项目中的源代码注释
3. Android 开发者网站

---

**祝你开发愉快！** 🚀
