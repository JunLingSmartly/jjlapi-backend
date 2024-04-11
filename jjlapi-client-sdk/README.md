### 目录结构 📝

```text
jjlapisdk
    ├─client
    ├─exception
    ├─model
    │  ├─enums
    │  ├─params
    │  ├─request
    │  └─response
    ├─service
    │  └─impi
    └─utils
```
###  快速开始 🚀

**要开始使用 Jin-API-SDK，您需要按照以下简单进行操作:**

#### 1. 引入依赖坐标

```xml
<dependency>
  <groupId>com.jjlapi</groupId>
  <artifactId>jjlapi-common</artifactId>
  <version>0.0.2</version>
</dependency>
```

#### 2. 前往[Jin-API 接口开放平台](http://62.234.67.99:8000/) 获取开发者密钥对

#### 3. 初始化客户端JjlApiClient对象

- 方法 1 ：主动实例化客户端

  ```java
  String accessKey = "你的 accessKey";
  String secretKey = "你的 secretKey";
  JjlApiClient client = new JjlApiClient(accessKey, secretKey);
  ```

- 方法 2 ：通过配置文件注入对象

  - yml

    ```yml
    # Jin-API 配置
    jjlapi:
        client:
          access-key: 你的 accessKey
          secret-key: 你的 secretKey
    ```
    
  - properties
  
    ```properties
    jjlapi.client.access-key=你的 accessKey
    jjlapi.client.secret-key=你的 secretKey
    ```

#### 4. 使用API服务

   ```java
    @Resource
    private ApiService apiService;
   ```
