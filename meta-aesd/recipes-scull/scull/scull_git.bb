LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

FILESEXTRAPATHS:prepend = "${THISDIR}/files:"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Yahia-Derbala.git;protocol=ssh;branch=master"
SRC_URI += "file://scull_init_script.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "60ed10e64168cb0d4217e6614b51f9304bb8e307"

S = "${WORKDIR}/git/scull"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull_init_script.sh"

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
    install -m 0755 ${WORKDIR}/scull_init_script.sh ${D}${sysconfdir}/init.d/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    
    install -d ${D}/etc/scull_startup
    install -m 0755 ${S}/scull_unload ${D}/etc/scull_startup/scull_unload
    install -m 0755 ${S}/scull_load ${D}/etc/scull_startup/scull_load
}
     