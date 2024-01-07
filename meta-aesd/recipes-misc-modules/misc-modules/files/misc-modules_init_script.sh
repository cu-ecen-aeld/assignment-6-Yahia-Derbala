#!/bin/sh

INITSCRIPTS_DIR=/etc/misc-modules_startup

case $1 in
    start)
        $INITSCRIPTS_DIR/module_load faulty
        modprobe hello
    ;;
    stop)
        $INITSCRIPTS_DIR/module_unload
        rmmod hello || exit 1
        rm -f /dev/hello        
    ;;
esac