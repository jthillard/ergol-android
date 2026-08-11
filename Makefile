.PHONY: debug install lint

debug:
	./gradlew assembleDebug

install: debug
	adb install externalkeyboard/build/outputs/apk/debug/externalkeyboard-debug.apk
