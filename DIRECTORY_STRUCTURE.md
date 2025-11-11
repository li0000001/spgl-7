# 项目完整目录结构

## 项目根目录

```
Product Expiration Date Manager/
│
├── .git/                                 # Git 版本控制
├── .idea/                               # Android Studio IDE 配置
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties    # Gradle Wrapper 配置
├── app/                                 # 应用模块（主要代码）
│   ├── build.gradle.kts                # 应用构建配置
│   ├── proguard-rules.pro              # ProGuard 混淆规则
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml     # 应用清单，权限声明
│       │   ├── java/
│       │   │   └── com/example/myapplication/
│       │   │       ├── MainActivity.kt                      # 应用主入口
│       │   │       ├── data/                               # 数据层
│       │   │       │   ├── Product.kt                      # 商品数据模型和枚举
│       │   │       │   ├── ProductDao.kt                   # Room 数据访问对象
│       │   │       │   ├── AppDatabase.kt                  # Room 数据库实例
│       │   │       │   └── Converters.kt                   # LocalDate 类型转换器
│       │   │       ├── viewmodel/
│       │   │       │   └── ProductViewModel.kt             # MVVM ViewModel 层
│       │   │       ├── ui/                                 # 用户界面
│       │   │       │   ├── screens/
│       │   │       │   │   ├── ProductListScreen.kt        # 商品列表屏幕
│       │   │       │   │   ├── AddProductScreen.kt         # 添加/编辑屏幕
│       │   │       │   │   └── ProductDetailScreen.kt      # 商品详情屏幕
│       │   │       │   ├── components/
│       │   │       │   │   ├── ProductList.kt              # 商品列表 Composable
│       │   │       │   │   └── ProductForm.kt              # 商品表单 Composable
│       │   │       │   └── theme/
│       │   │       │       ├── Theme.kt                    # Material 3 主题
│       │   │       │       ├── Color.kt                    # 颜色定义
│       │   │       │       └── Type.kt                     # 字体定义
│       │   │       ├── reminder/                           # 提醒系统
│       │   │       │   ├── AlarmScheduler.kt               # 闹钟调度
│       │   │       │   ├── CalendarManager.kt              # 系统日历管理
│       │   │       │   └── ReminderReceiver.kt             # 提醒广播接收器
│       │   │       └── navigation/
│       │   │           └── AppNavigation.kt                # 导航配置
│       │   └── res/                                        # Android 资源
│       │       ├── values/
│       │       │   ├── strings.xml                         # 字符串资源（中文）
│       │       │   └── themes.xml                          # 应用主题
│       │       ├── drawable/
│       │       │   ├── ic_launcher_background.xml          # 启动图标背景
│       │       │   └── ic_launcher_foreground.xml          # 启动图标前景
│       │       ├── mipmap-anydpi-v26/
│       │       │   └── ic_launcher.xml                     # Android 8 适配图标
│       │       └── mipmap-anydpi-v33/
│       │           ├── ic_launcher.xml                     # Android 13 适配图标
│       │           └── ic_launcher_round.xml               # 圆形图标
│       ├── test/                        # 单元测试（待实现）
│       └── androidTest/                 # Android 测试（待实现）
│
├── build.gradle.kts                    # 顶级 Gradle 构建脚本
├── settings.gradle.kts                 # Gradle 项目设置
├── gradle.properties                   # Gradle 全局属性
├── gradlew                             # Gradle Wrapper (Unix/Linux/Mac)
├── gradlew.bat                         # Gradle Wrapper (Windows)
├── .gitignore                          # Git 忽略规则
├── README.md                           # 项目概览文档
├── ANDROID_STUDIO_SETUP.md            # Android Studio 快速启动指南
├── PROJECT_STATUS.md                  # 项目状态总结
├── DIRECTORY_STRUCTURE.md             # 本文件
└── local.properties.example            # 本地配置示例（用户自填）
```

## 详细说明

### 核心包结构

#### `com.example.myapplication` (根包)
主应用程序包，包含以下子包：

```
com.example.myapplication/
├── data/              # 数据层 (Repository Pattern)
├── viewmodel/         # MVVM ViewModel
├── ui/                # UI 层 (Jetpack Compose)
│   ├── screens/       # 完整屏幕
│   ├── components/    # 可复用 Composable
│   └── theme/         # 主题和样式
├── reminder/          # 提醒和通知系统
├── navigation/        # 导航逻辑
└── MainActivity.kt    # 应用入口
```

### 文件数量统计

| 类别 | 数量 |
|------|------|
| Kotlin 源文件 | 18 |
| XML 资源文件 | 10+ |
| Gradle 配置文件 | 4 |
| 其他配置文件 | 3 |
| **总计** | **35+** |

### 编译输出目录 (不提交)

```
.gradle/                    # Gradle 缓存
build/                      # 构建输出目录
  ├── intermediates/        # 中间文件
  ├── outputs/              # APK/AAB 输出
  └── ...
.idea/                      # Android Studio 缓存
local.properties            # 本地配置（自动生成）
```

## 关键特性文件位置

| 特性 | 主要文件 |
|------|---------|
| 数据库 | `data/AppDatabase.kt`, `data/ProductDao.kt` |
| 商品模型 | `data/Product.kt` |
| 业务逻辑 | `viewmodel/ProductViewModel.kt` |
| 列表显示 | `ui/screens/ProductListScreen.kt`, `ui/components/ProductList.kt` |
| 添加/编辑 | `ui/screens/AddProductScreen.kt`, `ui/components/ProductForm.kt` |
| 详情展示 | `ui/screens/ProductDetailScreen.kt` |
| 导航 | `navigation/AppNavigation.kt` |
| 闹钟提醒 | `reminder/AlarmScheduler.kt` |
| 日历集成 | `reminder/CalendarManager.kt` |
| 权限请求 | `MainActivity.kt` |
| 本地化文本 | `res/values/strings.xml` |

## 开发流程

### 添加新功能的典型路径

1. **数据层**: `data/Product.kt` → `data/ProductDao.kt` → `data/AppDatabase.kt`
2. **业务逻辑**: `viewmodel/ProductViewModel.kt`
3. **UI**: `ui/screens/XxxScreen.kt` 或 `ui/components/XxxComponent.kt`
4. **导航**: 更新 `navigation/AppNavigation.kt`
5. **资源**: 添加到 `res/values/strings.xml`

### 代码查找快速指南

| 需要修改 | 位置 |
|---------|------|
| 应用名称 | `res/values/strings.xml` (app_name) |
| 按钮文本 | `res/values/strings.xml` |
| 应用图标 | `res/mipmap-*/ic_launcher.*` |
| 颜色主题 | `ui/theme/Color.kt` |
| 字体样式 | `ui/theme/Type.kt` |
| 数据库字段 | `data/Product.kt` |
| 数据库查询 | `data/ProductDao.kt` |
| 权限设置 | `AndroidManifest.xml` |
| 应用图标 | `res/drawable/ic_launcher_*.xml` |
| 闹钟逻辑 | `reminder/AlarmScheduler.kt` |

## 构建系统

### Gradle 模块

```
root (Project)
└── app (Module)
    ├── compileSdk: 34
    ├── targetSdk: 34
    ├── minSdk: 24
    └── dependencies: 10+ 库
```

### 依赖管理

所有依赖定义在 `app/build.gradle.kts`:

- **Compose**: BOM 2023.10.01
- **Room**: 2.6.1 (KSP 编译器)
- **Navigation**: 2.7.5
- **Lifecycle**: 2.6.2
- **Permissions**: Accompanist 0.33.2-alpha

## 测试目录 (待实现)

```
app/src/
├── test/                    # 单元测试
└── androidTest/             # Android 集成测试
```

## 资源命名规范

### 字符串资源 (strings.xml)
- 格式: `<string name="descriptive_name">值</string>`
- 例: `app_name`, `add_product`, `days_remaining`

### Drawable 资源
- 格式: `ic_<feature>.xml` (矢量图标)
- 例: `ic_launcher_foreground.xml`

### 颜色资源 (Color.kt)
- 格式: `val 颜色名 = Color(...)`
- 例: `Purple40`, `PurpleGrey80`

## 版本控制

### .gitignore 排除

- 构建输出: `build/`, `.gradle/`
- IDE 配置: `.idea/`
- 本地配置: `local.properties`
- 编译缓存: `.kotlin/`

### 包含在版本控制中

- ✅ 所有 Kotlin 源代码
- ✅ 所有 XML 资源
- ✅ Gradle 配置
- ✅ Gradle Wrapper
- ✅ 文档文件

---

**备注**: 这个结构完全符合 Android 和 Gradle 标准，可以被所有主流 IDE 识别。
