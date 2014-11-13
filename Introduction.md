由于恒大项目需要将一个包打成33个不同地区的安装包，在Android的开发环境中没有这种方便的打包方式，而手动打包则非常繁琐耗时，介于此，必须出台一款打包工具。

当前这款打包工具﻿的思想是：通过Linux脚本的替换命令，将测试完成的工程中的响应字段替换，之后对替换后的工程进行打包操作。  
  
###一、具体步骤如下：

0. 首先建立一个文件夹，作为生产空间，所有操作在这个生产空间中完成。

1. 建立一个“数据配置文件”，每一行为一个城市，每行有多个不同项目中需要替换的参数（例如：包名，版本号，软件名，Keystore名称，密码等信息），用一个特殊符号分隔（目前用的是，|，竖线）。

2. 将一个测试完成的版本的工程（以下称为“源项目”）放入与配置文件同级的目录，通过文本编辑器将需要替换掉的参数修改为“特定字符+序号”的形式，特定字符是不可能在项目中任何地方出现的字符串，确保替换字符的唯一性；而序号则是对应与数据配置文件中每行的第几个（从1开始计算），并且采用两位来书写，不足两位补0，例如02，08，12。

3. 删除生产空间中存在的所有过去生成的项目，保证项目的整洁。

4. 手动将记录了Keystore信息的ant_properties文件复制到源项目中。将装满了keystore文件的文件夹放到生产空间。在linux_script.sh脚本的头部修改全局数据。  

至此，完成需要手动配置的参数。

5. 在生产空间的目录下执行linux_script.sh脚本

6. 执行完成后会出现33个新项目（都已经生成签名后的apk文件），此时使用copy_to_one_floder.sh脚本，就可以将这33个apk复制出来。
  


###二、各个文件：  

#####1. ant.properties:  
		用于放入源工程中，用于脚本替换config_file记录的Keystore信息，供ant打包时使用。
		# keystore path
		key.store=wwwuuudddaaayyyuuu12
		# keystore password
		key.store.password=wwwuuudddaaayyyuuu13
		# alias name
		key.alias=wwwuuudddaaayyyuuu14
		# alias password
		key.alias.password=wwwuuudddaaayyyuuu15
#####2. config_file:
		记录了各个城市的各个信息，目前使用|来分隔。例子：
		com.movitech.grande|1.2|R0bgsowGrq3ElavVQTGSta90|SERVER_URL_SHAN_XI|太原|SHARE_URL_SHAN_XI|山西恒房通|wxb4f0ea9289c1e76f|80c2d36632d354fcf5195f89e706bd0e|SinaWeiboTestKey|SinaWeiboTestSecret|taiyuankeystore|hft123456|hft|hft123456
		com.movitech.grande.beijing|0.1|jKndWp5BVXWcf3FBNBigfGKK|SERVER_URL_BEI_JING|北京|SHARE_URL_BEI_JING|北京恒房通|WechatTestId|WechatMomentTest|SinaWeiboTestKey|SinaWeiboTestSecret|beijingkeystore|hft123456|hft|hft123456
######每行15个参数，依次是：包名；版本号；百度Key；服务器地址标识；城市名称；分享地址标识；应用程序名；微信ID；微信Secret；新浪Key；新浪Secret；Keystore名；Keystore密码；alias名；alias密码  

#####3. copy_to_one_floder.sh:
		用于拷贝各个项目bin目录下的已经打包的apk文件到apks文件夹中。
		# 定义临时文件名
		TMP_DIR_NAME=tmp_dir_names

		# 列出所有以com.开头的文件夹
		pathes=`ls -d com.*`
		# 将文件夹数据写入临时文件
		echo $pathes > $TMP_DIR_NAME
		# 获取文件夹数目
		length=`awk 'NR==1{print NF}' $TMP_DIR_NAME`;
		echo length=$length
		# 删除并建立apks
		rm -rf apks >> /dev/null
		mkdir apks
		# 遍历某个目录复制生成的apk文件
		for ((i=1;i<=length;++i)) do
			# 获取目录名
			packageName=`cat $TMP_DIR_NAME|cut -d ' ' -f $i`
			echo packageName=$packageName
			cp $packageName/bin/*-release.apk apks/
		done;
		# 删除临时文件
		rm $TMP_DIR_NAME;
#####4. linux_script.sh:
最重要的文件，用于执行建立不同工程，打包功能。
具体查看<https://github.com/wudayu/AutoGenerateApk/blob/master/linux_script.sh>