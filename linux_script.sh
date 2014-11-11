# 定义替换标志字符串
SYMBOL=wwwuuudddaaayyyuuu;
# 定义原工程的名称
ORIGIN_PROJECT=xcfcAndroid;



echo ''
# 获取配置文件行数也就是城市数目
lines=`awk 'END{print NR}' config_file`;
# 获取配置文件列数也就是参数数目
cols=`awk 'NR==1{print NF}' config_file`;

# 按照各个城市开始循环
for ((i=1;i<=lines;++i)) do
	echo i = $i
	# 新工程的文件夹名称
	newFloder=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f 1`;
	echo newFloder = $newFloder;
	`cp -rf $ORIGIN_PROJECT $newFloder`
	# 替换各个属性的值属性值
	for ((j=1;j<=cols;++j)) do
		# val代表第i行第j列的字符数据
		val=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f $j`
		# tmpIndex表示两位的数字，不足两位的数字需要在前面补0，比如02，09，12
		tmpIndex=`printf "%02d\n" $j`;
		echo j = $tmpIndex , val = $val
		# 获取所有包含了需要修改的字段的文件
		fileNeedReplace=`grep "$SYMBOL$tmpIndex" -rl $newFloder`
		# 替换获取到的文件中的响应字段
		`sed -i '' "s/$SYMBOL$tmpIndex/$val/g" $fileNeedReplace 2>/dev/null`
	done;
	echo ''
done;