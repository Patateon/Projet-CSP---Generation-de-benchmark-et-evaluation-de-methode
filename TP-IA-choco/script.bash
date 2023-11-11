#!/bin/bash

rm -f csp/*

  for (( i = 750 ; i >= 50; i-=50 ))
do
./urbcsp 100 100 100 $i 10 > csp/csp$i.txt
done