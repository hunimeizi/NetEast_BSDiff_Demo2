cmake_minimum_required(VERSION 3.4.1)

file(GLOB bzip_source ${CMAKE_SOURCE_DIR}/bzip/*.c)

add_library( # 设置本地动态库 编译生成动态库
             native-lib #模块名


             # 将库设置为共享库。
             SHARED


             # 为源文件提供一个相对路径。
             src/main/cpp/native-lib.cpp
             src/main/cpp/bspatch.c
             ${bzip_source})

# 搜索指定预先构建的库和存储路径变量。因为CMake包括系统库搜索路径中默认情况下,只需要指定想添加公共NDK库的名称，在CMake验证库之前存在完成构建
find_library( # 设置path变量的名称
              log-lib
              # 在CMake定位前指定的NDK库名称
              log )
# 指定库CMake应该链接到目标库中，可以链接多个库，比如定义库，构建脚本，预先构建的第三方库或者系统库
target_link_libraries( # 指定目标库
                       native-lib
                       # 目标库到日志库的链接 包含在NDK
                       ${log-lib} )