# 🚀 快速开始 (5 分钟)

## 只需 4 步就能运行应用

### 1️⃣ 打开项目 (30 秒)

```bash
# 在 Android Studio 中:
File → Open → 选择这个项目文件夹 → Open
```

### 2️⃣ 等待同步 (3-5 分钟)

- Android Studio 会自动下载 Gradle
- 自动解析依赖
- 自动配置 Android SDK

**不需要做任何操作，耐心等待即可** ⏳

### 3️⃣ 创建/选择设备 (1 分钟)

#### 选项 A: 虚拟设备
```
Tools → Device Manager → Create Device
选择 Pixel 6 或 Pixel 7 → API 34 或更高 → Finish
```

#### 选项 B: 真实设备
```
连接 Android 设备到电脑
启用 USB 调试
Android Studio 会自动识别
```

### 4️⃣ 点击 Run (1-2 分钟)

```
点击绿色的 Run 按钮 (或按 Shift + F10)
选择设备
等待应用启动
```

---

## ✅ 完成！

应用现在应该在你的设备上运行了！

### 你会看到:
- 📱 "保质期管家" 应用界面
- 📝 添加商品按钮
- 📋 商品列表（初始为空）
- 📊 三个标签页: 全部、有效、已过期

---

## 🛠️ 常见问题

### Q: Gradle 同步失败?
```bash
# 在终端中运行:
./gradlew clean
```
然后在 Android Studio 中点击 **File → Sync Now**

### Q: 找不到虚拟设备?
```
Tools → Device Manager → Create Device
```

### Q: 应用运行缓慢?
- 虚拟设备配置 4GB+ RAM
- 启用硬件加速
- 检查你的电脑性能

### Q: 权限问题?
应用会自动请求权限，只需要允许即可。

---

## 📚 详细指南

- **完整设置**: 见 `ANDROID_STUDIO_SETUP.md`
- **项目结构**: 见 `DIRECTORY_STRUCTURE.md`
- **项目文档**: 见 `README.md`
- **项目状态**: 见 `PROJECT_STATUS.md`

---

## 🎯 接下来干什么?

### 测试功能
1. 点击 **"添加商品"** 按钮
2. 输入商品信息
3. 选择生产日期和保质期
4. 保存
5. 查看商品列表

### 修改应用
- 编辑应用名称: `app/src/main/res/values/strings.xml`
- 修改颜色: `app/src/main/java/.../ui/theme/Color.kt`
- 修改业务逻辑: `app/src/main/java/.../viewmodel/ProductViewModel.kt`

### 构建发布
```bash
# 在 Android Studio 中:
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

---

**准备好了吗？打开 Android Studio，开始吧！** 🎉
