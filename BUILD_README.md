# 自动编译指南

## GitHub Actions 自动编译配置

本项目已配置 GitHub Actions 自动编译工作流。

### 功能特性

✅ **自动编译**
- 每次推送到 `master`、`main`、`develop` 分支时自动触发编译
- 支持手动触发编译

✅ **输出产物**
- Debug APK：`app-debug.apk`
- Release APK：`app-release.apk`

✅ **自动发布**
- 创建 Tag 时自动生成 Release
- APK 自动上传到 Release 页面

### 如何使用

#### 1. 查看构建状态
访问：https://github.com/uaoan/UaoanDKVideoPlayer/actions

#### 2. 手动触发编译
1. 进入 **Actions** 标签
2. 选择 **Build APK** 工作流
3. 点击 **Run workflow** → **Run workflow** 按钮

#### 3. 下载编译产物
- 在 Actions 运行详情中找到 **Artifacts** 部分
- 下载 `debug-apk` 或 `release-apk`

#### 4. 创建版本发布并自动上传 APK
```bash
# 创建 tag 并推送
git tag v1.0.1
git push origin v1.0.1
```

之后 APK 会自动上传到 Release 页面。

### 工作流配置说明

| 配置项 | 说明 |
|--------|------|
| JDK 版本 | 11（与项目要求一致） |
| 编译类型 | Debug 和 Release |
| 缓存 | Gradle 缓存加速编译 |
| 触发条件 | push、pull_request、手动触发 |
| 运行环境 | ubuntu-latest |

### 编译时间

- **首次编译**：3-5 分钟（需要下载依赖）
- **后续编译**：1-2 分钟（有 Gradle 缓存）

### 常见问题

**Q: 编译失败怎么办？**
A: 
1. 检查 Actions 日志中的错误信息
2. 通常是网络问题，点击 **Re-run jobs** 重新运行
3. 如果持续失败，检查依赖配置

**Q: 编译后的 APK 在哪里？**
A: 
- 在 Actions 工作流运行页面中，向下滚动找 **Artifacts** 部分
- 可以下载 `debug-apk` 或 `release-apk`

**Q: 如何使用编译好的 APK？**
A:
```bash
# Debug APK 可以直接安装到测试设备
adb install app-debug.apk

# Release APK 需要签名才能在真实设备上安装
# 可用于上传到应用商店
```

**Q: 支持哪些分支？**
A: 目前配置支持以下分支的自动编译：
- `master`
- `main`
- `develop`

### 截图示例

1. **查看 Actions**
   - 访问 https://github.com/uaoan/UaoanDKVideoPlayer/actions

2. **查看运行日志**
   - 点击某次运行 → 查看详细日志

3. **下载 APK**
   - 运行页面 → Artifacts → 下载所需 APK

### 相关链接

- [GitHub Actions 官方文档](https://docs.github.com/en/actions)
- [工作流配置文件](.github/workflows/build-apk.yml)
- [项目 Actions 页面](https://github.com/uaoan/UaoanDKVideoPlayer/actions)

---

**最后更新**：2026-06-10
