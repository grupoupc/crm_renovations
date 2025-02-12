#!/bin/sh 
SERVICE_NAME=Servicio_crm_renovations 
PATH_TO_JAR=/apli/crm_renovations/crm_renovations.jar 
PID_PATH_NAME=/tmp/crm-server-pid
fecha=$(date +'%y%m%d')
echo $fecha 
case $1 in 
start)
       echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then 
      # nohup /usr/bin/java -jar -Dspring.profiles.active=dev $PATH_TO_JAR >> /logapli/crm_renovations/crm_renovations.log 2>&1&      
        nohup /usr/bin/java -jar $PATH_TO_JAR >> /logapli/crm_renovations/crm_renovations_$fecha.log 2>&1&       
           echo $! > $PID_PATH_NAME  
       echo "$SERVICE_NAME started ..."         
  else 
       echo "$SERVICE_NAME is already running ..."
  fi
;;
stop)
  if [ -f $PID_PATH_NAME ]; then
         PID=$(cat $PID_PATH_NAME);
         echo "$SERVICE_NAME stoping ..." 
         kill $PID;         
         echo "$SERVICE_NAME stopped ..." 
         rm $PID_PATH_NAME       
  else          
         echo "$SERVICE_NAME is not running ..."   
  fi    
;;    
restart)  
  if [ -f $PID_PATH_NAME ]; then 
      PID=$(cat $PID_PATH_NAME);    
      echo "$SERVICE_NAME stopping ..."; 
      kill $PID;           
      echo "$SERVICE_NAME stopped ...";  
      rm $PID_PATH_NAME     
      echo "$SERVICE_NAME starting ..."  
    #  nohup /usr/bin/java -jar -Dspring.profiles.active=pre $PATH_TO_JAR >> /logapli/crm_renovations/crm_renovations.log 2>&1&            
      nohup /usr/bin/java -jar $PATH_TO_JAR >> /logapli/crm_renovations/crm_renovations_$fecha.log 2>&1&
      echo $! > $PID_PATH_NAME  
      echo "$SERVICE_NAME started ..."    
  else           
      echo "$SERVICE_NAME is not running ..."    
     fi     ;;
 esac
