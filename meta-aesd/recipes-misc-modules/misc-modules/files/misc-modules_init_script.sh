#!/bin/sh

INITSCRIPTS_DIR=/etc/misc-modules_startup

case $1 in
    start)
        $INITSCRIPTS_DIR/module_load faulty
    ;;
    stop)
        $INITSCRIPTS_DIR/module_unload
    ;;
esac