# 定义替换标志字符串
SYMBOL=wwwuuudddaaayyyuuu
# 定义原工程的名称
ORIGIN_PROJECT=xcfcAndroid
# 定义原工程的包名(Manifest文件中的)
ORIGIN_PACKAGE_NAME=com\\.movitech\\.grande\\.haerbin
# 定义Keystore文件夹名称
KEYSTORE_FLODER=keystores
# 定义配置文件名称
CONFIG_FILE=config_file
# 定义配置文件列数
COLS_COUNT=15;
# 定义签名文件文件Index位置
SIGN_FILE_INDEX=12



echo ''
# 删除没用的版本控制文件
`find $ORIGIN_PROJECT -name *.svn | xargs rm -rf`

# 获取配置文件行数也就是城市数目
lines=`awk 'END{print NR}' $CONFIG_FILE`;
# 获取配置文件列数也就是参数数目
cols=$COLS_COUNT;


# 按照各个城市开始循环
for ((i=1;i<=lines;++i)) do
	echo i = $i
	# 新工程的文件夹名称
	newProject=`awk "{if(NR==$i)print $1;}" $CONFIG_FILE|cut -d '|' -f 1`;
	echo newProject = $newProject;
	# 使用原版工程源码生成新的工程
	cp -rf $ORIGIN_PROJECT $newProject
	# 替换应用包名
	fileContainsPacakgeName=`grep $ORIGIN_PACKAGE_NAME -rl $newProject`
	`sed -i '' "s/$ORIGIN_PACKAGE_NAME/$newProject/g" $fileContainsPacakgeName 2>/dev/null`
	# 替换新工程中各个属性的值
	for ((j=1;j<=cols;++j)) do
		# val代表第i行第j列的字符数据
		val=`awk "{if(NR==$i)print $1;}" $CONFIG_FILE|cut -d '|' -f $j`
		# tmpIndex表示两位的数字，不足两位的数字需要在前面补0，比如02，09，12
		tmpIndex=`printf "%02d\n" $j`;
		echo j = $tmpIndex , val = $val
		# 获取所有包含了需要修改的字段的文件
		fileNeedReplace=`grep "$SYMBOL$tmpIndex" -rl $newProject`
		if [ $j = $SIGN_FILE_INDEX ] ;then
			# keystore路径(对于项目内部来说的路径) # newProject
			ksPath=..\\/$KEYSTORE_FLODER\\/$val
			`sed -i '' "s/$SYMBOL$tmpIndex/$ksPath/g" $fileNeedReplace 2>/dev/null`
		else
			# 替换获取到的文件中的响应字段
			`sed -i '' "s/$SYMBOL$tmpIndex/$val/g" $fileNeedReplace 2>/dev/null`
		fi
	done;
	# 进入新工程目录
	cd $newProject
	rm -rf bin
	# 替换.project文件中的项目名
	`sed -i '' "s/wwwuuudddaaayyyuuu00/$newProject/g" .project 2>/dev/null`
	#`sed -i '' "s/wwwuuudddaaayyyuuu16/$newProject\/compile-libs\/androidannotations-3.0.1.jar/g" .project 2>/dev/null`
	# 退出新工程目录，回到生产目录
	cd ..
	echo ''
done;
