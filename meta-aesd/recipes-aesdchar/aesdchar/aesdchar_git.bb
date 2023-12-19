# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend = "${THISDIR}/files:"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-Yahia-Derbala.git;protocol=ssh;branch=master"
SRC_URI += "file://aesdchar_init_script.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "d2cf881bb2a844e1389b2267b02b6f97da8eafa9"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar_init_script.sh"

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
    install -m 0755 ${WORKDIR}/aesdchar_init_script.sh ${D}${sysconfdir}/init.d/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    
    install -d ${D}/etc/aesdchar_startup
    install -m 0755 ${S}/aesdchar_unload ${D}/etc/aesdchar_startup/aesdchar_unload
    install -m 0755 ${S}/aesdchar_load ${D}/etc/aesdchar_startup/aesdchar_load
}
     