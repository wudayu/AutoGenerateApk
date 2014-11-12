# 定义替换标志字符串
SYMBOL=wwwuuudddaaayyyuuu;
# 定义原工程的名称
ORIGIN_PROJECT=xcfcAndroid;
# 定义Keystore文件夹
KEYSTORE_FLODER=keystores/;
# 定义配置文件名称
CONFIG_FILE=config_file



echo ''
# 获取配置文件行数也就是城市数目
lines=`awk 'END{print NR}' $CONFIG_FILE`;
# 获取配置文件列数也就是参数数目
cols=`awk 'NR==1{print NF}' $CONFIG_FILE`;

# 按照各个城市开始循环
for ((i=1;i<=lines;++i)) do
	echo i = $i
	# 新工程的文件夹名称
	newProject=`awk "{if(NR==$i)print $1;}" $CONFIG_FILE|cut -d ' ' -f 1`;
	echo newProject = $newProject;
	# 使用原版工程源码生成新的工程
	`cp -rf $ORIGIN_PROJECT $newProject`
	# 替换新工程中各个属性的值
	for ((j=1;j<=cols;++j)) do
		# val代表第i行第j列的字符数据
		val=`awk "{if(NR==$i)print $1;}" $CONFIG_FILE|cut -d ' ' -f $j`
		# tmpIndex表示两位的数字，不足两位的数字需要在前面补0，比如02，09，12
		tmpIndex=`printf "%02d\n" $j`;
		echo j = $tmpIndex , val = $val
		# 获取所有包含了需要修改的字段的文件
		fileNeedReplace=`grep "$SYMBOL$tmpIndex" -rl $newProject`
		# 替换获取到的文件中的响应字段
		`sed -i '' "s/$SYMBOL$tmpIndex/$val/g" $fileNeedReplace 2>/dev/null`
	done;

	# keystore文件名在某一行中的序号
	ksFileNameIndex=`expr $cols - 3`;
	# keystore文件名
	ksFileName=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f $ksFileNameIndex`;
	# keystore文件名在某一行中的序号
	ksFilePassIndex=`expr $cols - 2`;
	# keystore文件名
	ksFilePass=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f $ksFilePassIndex`;
	# keystore文件名在某一行中的序号
	ksAliasNameIndex=`expr $cols - 1`;
	# keystore文件名
	ksAliasName=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f $ksAliasNameIndex`;
	# keystore文件名在某一行中的序号
	ksAliasPassIndex=`expr $cols - 0`;
	# keystore文件名
	ksAliasPass=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f $ksAliasPassIndex`;

	# keystore路径 # newProject
	ksPath=$KEYSTORE_FLODER$filename
	echo ''
done;