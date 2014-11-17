# 定义临时文件名
TMP_DIR_NAME=tmp_dir_names

# 列出所有以com.开头的文件夹
pathes=`ls -d com.*`
# 将文件夹数据写入临时文件
echo $pathes > $TMP_DIR_NAME
# 获取文件夹数目
length=`awk 'NR==1{print NF}' $TMP_DIR_NAME`;
echo length=$length
# 建立apks
mkdir apks >> /dev/null
# 遍历某个目录复制生成的apk文件
for ((i=1;i<=length;++i)) do
	# 获取目录名
	packageName=`cat $TMP_DIR_NAME|cut -d ' ' -f $i`
	echo packageName=$packageName
	cd $packageName
	# 生成build.xml等文件
	android update project -p . -n $packageName -t android-20
	# 打包并签名
	ant release
	cd ..
	cp $packageName/bin/*-release.apk apks/
done;
# 删除临时文件
rm $TMP_DIR_NAME;