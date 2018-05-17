# Des(三重Des)加解密以及Jni、Jna调用c语言动态链接库

## Des加解密

### 1.关于Des加密
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DES 使用一个 56 位的密钥以及附加的 8 位奇偶校验位，产生最大 64 位的分组大小。这是一个迭代的分组密码，使用称为 Feistel 的技术，其中将加密的文本块分成两半。使用子密钥对其中一半应用循环功能，然后将输出与另一半进行"异或"运算。接着交换这两半，这一过程会继续下去，但最后一个循环不交换。DES 使用 16 个循环，使用异或，置换，代换，移位操作四种基本运算。DES 的常见变体是三重 DES，使用 168 位的密钥对资料进行三次加密的一种机制;它通常(但非始终)提供极其强大的安全性。如果三个 56 位的子元素都相同，则三重 DES 向后兼容 DES。

### 2.代码示例
- [Des加解密方法一](https://github.com/Areogel666/DesAndJna/blob/master/src/cn/lxr/instance/DesEcb.java)
- [Des加解密方法二](https://github.com/Areogel666/DesAndJna/blob/master/src/cn/lxr/instance/DesFile.java)
- [3Des加解密方法](https://github.com/Areogel666/DesAndJna/blob/master/src/cn/lxr/instance/TripleDES.java)

## Jni、Jna调用c语言动态链接库

### 1.关于Jni
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JNI是Java Native Interface的缩写，它提供了若干的API实现了Java和其他语言的通信(主要是C&C++)。从Java1.1开始，JNI标准成为java平台的一部分，它允许Java代码和其他语言写的代码进行交互。JNI一开始是为了本地已编译语言，尤其是C和C++而设计的，但是它并不妨碍你使用其他编程语言，只要调用约定受支持就可以了。使用java与本地已编译的代码交互，通常会丧失平台可移植性。但是，有些情况下这样做是可以接受的，甚至是必须的。例如，使用一些旧的库，与硬件、操作系统进行交互，或者为了提高程序的性能。JNI标准至少要保证本地代码能工作在任何Java 虚拟机环境。

### 2.关于Jna
>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JNA(Java Native Access )提供一组Java工具类用于在运行期动态访问系统本地库(native library:如Window的dll)而不需要编写任何Native/JNI代码。开发人员只要在一个java接口中描述目标native library的函数与结构，JNA将自动实现Java接口到native function的映射。

### 3.生成c/c++动态链接库（Linux-so库;Windows-dll库）
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;具体可以参考网上方法，此处只根据本人遇到的情况，介绍一种Linux环境下生成so库的方法。
- 生成.o文件
`gcc -fPIC -c trides.c -std=c99 -o trides.o`
- 生成.so文件
`gcc -shared -o libtrides.so trides.o`

### 4.示例代码
- [Jna调用so库](https://github.com/Areogel666/DesAndJna/blob/master/src/cn/lxr/instance/TestJNA.java)
- [Jni调用so库](https://github.com/Areogel666/DesAndJna/blob/master/src/cn/lxr/instance/TestJNI.java)
