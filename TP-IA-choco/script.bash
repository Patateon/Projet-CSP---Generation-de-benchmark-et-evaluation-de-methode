#!/bin/bash

for (( i = 10 ; i <= 110; i+=10 )) 
do
./urbcsp 100 300 10 $i 100 > csp$i.txt
done