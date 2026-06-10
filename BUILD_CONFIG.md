# UaoanDKVideoPlayer 自动编译配置

## 🎬 项目概述
UaoanDKVideoPlayer 是一个基于 DK 播放器的二次开发视频播放库，支持多种播放功能。

## 🚀 快速开始

### 自动编译触发
该项目已配置 GitHub Actions 自动编译工作流。

**触发条件：**
- ✅ 推送到 `master`、`main`、`develop` 分支
- ✅ 创建 Pull Request 到这些分支
- ✅ 手动在 Actions 页面触发

### 📦 编译输出
- **Debug APK**：`app-debug.apk` - 用于开发测试
- **Release APK**：`app-release.apk` - 用于发布

### 🔗 查看编译状态
访问项目的 [Actions 页面](https://github.com/uaoan/UaoanDKVideoPlayer/actions)

---

**最后更新**：2026-06-10 02:10 UTC

## 环境要求
- JDK 11
- Android SDK 36+
- Gradle 缓存优化

## 配置文件
- 工作流配置：`.github/workflows/build-apk.yml`
- 项目配置：`settings.gradle`、`build.gradle`
