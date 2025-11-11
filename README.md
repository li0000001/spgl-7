# 商品保质期管家 (Product Expiration Date Manager)

一个功能丰富的Android应用，帮助用户管理商品保质期，及时接收过期提醒。

## 技术栈

- **平台**: Android
- **语言**: Kotlin
- **UI框架**: Jetpack Compose
- **架构模式**: MVVM
- **数据库**: Room
- **依赖注入**: 手动依赖注入
- **导航**: Navigation Compose
- **权限管理**: Accompanist Permissions

## 项目结构

```
app/src/main/
├── java/com/example/myapplication/
│   ├── MainActivity.kt              # 主活动，处理权限请求
│   ├── data/
│   │   ├── Product.kt              # 商品数据模型
│   │   ├── ProductDao.kt           # 数据访问对象
│   │   ├── AppDatabase.kt          # Room数据库
│   │   └── Converters.kt           # 类型转换器
│   ├── reminder/
│   │   ├── AlarmScheduler.kt       # 闹钟调度器
│   │   ├── CalendarManager.kt      # 日历管理器
│   │   └── ReminderReceiver.kt     # 提醒广播接收器
│   ├── ui/
│   │   ├── components/
│   │   │   ├── ProductForm.kt      # 商品表单组件
│   │   │   └── ProductList.kt      # 商品列表组件
│   │   ├── screens/
│   │   │   ├── ProductListScreen.kt
│   │   │   ├── AddProductScreen.kt
│   │   │   └── ProductDetailScreen.kt
│   │   └── theme/
│   │       ├── Color.kt
│   │       ├── Theme.kt
│   │       └── Type.kt
│   ├── viewmodel/
│   │   └── ProductViewModel.kt     # 商品视图模型
│   └── navigation/
│       └── AppNavigation.kt        # 应用导航设置
└── res/
    ├── values/
    │   ├── strings.xml
    │   └── themes.xml
    └── drawable/
        └── ic_launcher_*.xml
```

## 核心功能

### 商品管理
- ✅ 添加商品信息（名称、生产日期、保质期、过期日期）
- ✅ 灵活的输入方式（生产日期+保质期 或 直接输入过期日期）
- ✅ 编辑和删除商品
- ✅ 商品列表按过期日期排序
- ✅ 分类展示（全部、有效、已过期）

### 提醒系统
- ✅ 四种提醒类型：通知提醒、闹钟提醒、双重提醒、应用内提醒
- ✅ 可自定义提前提醒天数和提醒时间
- ✅ 与系统日历集成，自动创建日历事件
- ✅ 使用AlarmManager设置精确提醒
- ✅ 支持多种提醒方式（通知、闹钟、应用内）

### 用户界面
- ✅ 使用Jetpack Compose构建现代化UI
- ✅ 中文界面，符合用户使用习惯
- ✅ 直观显示剩余天数和过期状态
- ✅ 响应式设计，适配不同屏幕尺寸
- ✅ Tab标签页快速切换商品分类

## 数据模型

### Product
```kotlin
data class Product(
    val id: Int,
    val name: String,
    val productionDate: LocalDate,
    val shelfLifeDays: Int,
    val expiryDate: LocalDate,
    val reminderDaysInAdvance: Int,
    val reminderTime: String,
    val reminderType: ReminderType,
    val calendarEventId: Long?,
    val isExpired: Boolean,
    val daysRemaining: Int,
    val notes: String
)
```

### ReminderType
- `NOTIFICATION`: 通知提醒
- `ALARM`: 闹钟提醒
- `BOTH`: 双重提醒
- `NONE`: 无提醒

## 权限要求

应用需要以下权限：
- `READ_CALENDAR`: 读取日历
- `WRITE_CALENDAR`: 写入日历
- `POST_NOTIFICATIONS`: 发送通知 (Android 13+)
- `SCHEDULE_EXACT_ALARM`: 设置精确闹钟

## 构建和运行

### 前置条件
- Android Studio 最新版本
- Android SDK 34+
- Kotlin 1.9.10+

### 构建
```bash
./gradlew build
```

### 运行
```bash
./gradlew installDebug
```

## 数据库

使用Room数据库进行本地数据存储，数据库自动创建和迁移。

## 导航

应用使用Jetpack Navigation Compose进行屏幕导航：
- `product_list`: 商品列表屏幕
- `add_product`: 添加商品屏幕
- `product_detail/{productId}`: 商品详情屏幕
- `edit_product/{productId}`: 编辑商品屏幕

## 主要依赖

- androidx.core:core-ktx:1.12.0
- androidx.lifecycle:lifecycle-runtime-ktx:2.6.2
- androidx.activity:activity-compose:1.8.0
- androidx.compose.ui:ui
- androidx.compose.material3:material3
- androidx.navigation:navigation-compose:2.7.5
- androidx.room:room-runtime:2.6.1
- androidx.room:room-ktx:2.6.1
- com.google.accompanist:accompanist-permissions:0.33.2-alpha

## 开发挑战与解决方案

### 日期选择器兼容性
- 实现了自定义日期选择对话框
- 处理Calendar和LocalDate之间的转换

### 权限管理
- 使用Accompanist Permissions简化权限请求
- 针对不同Android版本的权限处理

### 提醒系统
- 结合日历API和AlarmManager
- 处理不同提醒类型的实现

### 数据同步
- 确保数据库和日历事件同步
- 处理数据更新和删除的一致性

## 扩展可能性

### 功能扩展
- [ ] 添加商品分类
- [ ] 添加条码扫描功能
- [ ] 添加数据导入/导出功能
- [ ] 添加商品图片
- [ ] 添加使用统计功能

### 技术优化
- [ ] 使用依赖注入框架(Hilt)
- [ ] 添加单元测试和UI测试
- [ ] 使用DataStore替代SharedPreferences
- [ ] 实现数据备份和恢复

## 许可证

MIT License

## 作者

Product Expiration Date Manager Team
