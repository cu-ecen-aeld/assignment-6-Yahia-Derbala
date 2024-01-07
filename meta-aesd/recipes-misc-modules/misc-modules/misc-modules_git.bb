
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

FILESEXTRAPATHS:prepend = "${THISDIR}/files:"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Yahia-Derbala.git;protocol=ssh;branch=master"
SRC_URI += "file://misc-modules_init_script.sh"


# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "60ed10e64168cb0d4217e6614b51f9304bb8e307"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules_init_script.sh"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
        :
}

do_compile () {
        oe_runmake
}

KERNEL_VERSION = "5.15.124-yocto-standard"

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc-modules_init_script.sh ${D}${sysconfdir}/init.d/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    
    install -d ${D}/etc/misc-modules_startup
    install -m 0755 ${S}/module_unload ${D}/etc/misc-modules_startup/module_unload
    install -m 0755 ${S}/module_load ${D}/etc/misc-modules_startup/module_load
}
     