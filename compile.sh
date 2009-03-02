#! /bin/bash

case "$1" in 
	--compile | --build )
		echo "Compiling sources..."
		cd src
		for src in `ls *.java` ;  do
			case "$2" in 
				--verbose)
					echo "=> Compiling $src...."
				;;
			esac
			javac $src
		done
		mv *.class ../build
	;;

	--clean )
		echo "cleaning..."
		cd build
		rm *.class
	;;
esac
