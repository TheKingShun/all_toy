if [ $# == 0 ]
then 
	python Main.py -fp ./source.py
elif [ $# == 1 ]
then
	python Main.py -fp $1
fi


