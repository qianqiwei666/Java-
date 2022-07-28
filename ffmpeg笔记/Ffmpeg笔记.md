# 一、安装

## 一、下载

> [Releases · BtbN/FFmpeg-Builds · GitHub](https://github.com/BtbN/FFmpeg-Builds/releases)

## 二、安装

> 解压下载文件,最好配置环境变量

# 二、使用

## 一、将mp4文件切割为ts小文件

> ffmpeg -i  [视频文件名称]  -hls_time [每片ts视频长度]  -hls_list_size [保存分片的数量(0保存所有)] -hls_segment_filename [每一片ts文件的名称]  ./[m3u8索引文件]

## 二、用例

> ffmpeg -i test.mp4 -hls_time 3 -hls_list_size 0 -hls_segment_filename ./qianqiwei_%05d.ts ./qianqiwei.m3u8

# 三、实战

## 一、利用m3u8播放视频文件

> 导入以下文件

> 1. [m3u8视频解析链接][https://unpkg.com/videojs-contrib-hls@5.15.0/dist/videojs-contrib-hls.js]
> 2. [video.js][https://unpkg.com/video.js@7.19.2/dist/video.min.js]

