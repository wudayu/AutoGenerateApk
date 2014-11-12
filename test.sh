i=1
cols=`awk 'NR==1{print NF}' config_file`;
index=`expr $cols - 3`;
KEYSTORE_FLODER=keystores/;
filename=`awk "{if(NR==$i)print $1;}" config_file|cut -d ' ' -f $index`;
cat $KEYSTORE_FLODER$filename