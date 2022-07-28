# 一、入门

## 一、关键字

> object:指的是文件,比如图片,音乐等。
>
> bucket:给数据分组,每个组的数据不受影响。说白了就是当前磁盘中的顶层文件夹。
>
> drive:指的是哪个磁盘(linux:/mnt/)。
>
> set:指的是drive集合。也就是磁盘集合。

# 二、简单部署(单节点)

## 一、下载并运行MinIO

``` bash
wget https://dl.min.io/server/minio/release/linux-amd64/minio
chmod +x minio
MINIO_ROOT_USER=admin MINIO_ROOT_PASSWORD=qianqiwei ./minio server /mnt/data --console-address ":9001"
```

# 三、Java使用

## 一、导入maven

``` xml
       <!-- https://mvnrepository.com/artifact/io.minio/minio -->
        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>8.3.9</version>
        </dependency>
      <!--Spring部分版本不兼容请引入这些依赖-->
        <dependency>
            <groupId>me.tongfei</groupId>
            <artifactId>progressbar</artifactId>
            <version>0.5.3</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>4.8.1</version>
        </dependency>
```

## 二、一些常用命令的使用

> 请看我的自定义工具类,写的很详细！

## 三、MinIO配合Spring下载文件

```java
@RestController
public class WebController {

    @GetMapping("/index")
    public void index(HttpServletResponse response, @RequestParam("filename")String filename){
        try {
            //获取响应流
            ServletOutputStream outputStream = response.getOutputStream();
            //设置自定义工具类信息
            MinIOCommand minIOCommand=new EasyMinIOCommand("http://www.qianqiwei.com:9000","admin","qianqiwei");
            //获取文件信息
            StatObjectResponse message = minIOCommand.statObject("qianqiwei", filename);
            //设置内容type
            response.setContentType(message.contentType());
            //设置响应头
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
            //将获取的文件交给ServletOutputStream
            minIOCommand.downloadFileByResponse("qianqiwei",filename,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        }

    }
}
```

