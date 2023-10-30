# Author	Maksym Dybarskyi
# Created	17.04.2023

BIN_NAME = gmd
APP_HOME = ~/.adbwrp

help:
	@echo "\
	adbwrp - adb wrapper\n\
	--------------------\n\
	Tasks: \n\
	  init			- setup shell completion, app home dir and config files with placeholders\n\
	  buildLinux    - build linuxX64 executable binaries\n\
	  buildOsx   	- build macosArmX64 executable binaries\n\
	  installLinux  - install linuxX64 executable binaries to ~/.local/bin\n\
	  installOsx    - install macosArmX64 executable binaries to ~/.local/bin\n\
	"

buildLinux:
	./gradlew linuxX64Binaries

buildOsx:
	./gradlew macosArm64Binaries

installLinux:
	cp build/bin/linuxX64/releaseExecutable/adbwrp.kexe $(HOME)/.local/bin/$(BIN_NAME)

installOsx:
	cp build/bin/macosArm64/releaseExecutable/adbwrp.kexe /opt/bin/$(BIN_NAME)

init:
	# config files
	mkdir -p $(APP_HOME)
	touch $(APP_HOME)/config
	touch $(APP_HOME)/credentials
	printf "app_package=\nandroid_home=\nlauncher_activity=\n" > $(APP_HOME)/config
	# completion
	cp completion.sh $(APP_HOME)/completion
	echo "source $(APP_HOME)/completion" >> "$(HOME)/.$$(echo $(SHELL) | cut -d '/' -f3)rc"

osx: init buildOsx installOsx
linux: init buildLinux installLinux

.PHONY: init buildLinux buildOsx installLinux installOsx
.SILENT: init installLinux installOsx
