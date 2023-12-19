#!/bin/sh

INITSCRIPTS_DIR=/etc/scull_startup

case $1 in
    start)
        $INITSCRIPTS_DIR/scull_load
        modprobe hello
    ;;
    stop)
        $INITSCRIPTS_DIR/scull_unload
        rmmod hello
    ;;
esac


