LOCAL_PATH := $(call my-dir)

#Build .so  
include $(CLEAR_VARS)

LOCAL_MODULE := libIRCore
LOCAL_SRC_FILES_32 := libs/armeabi/libIRCore.so
LOCAL_MULTILIB := 32
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_SUFFIX := .so
include $(BUILD_PREBUILT)

include $(CLEAR_VARS)

LOCAL_MODULE := libmisc
LOCAL_SRC_FILES_32 := libs/armeabi/libmisc.so
LOCAL_MULTILIB := 32
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_SUFFIX := .so
include $(BUILD_PREBUILT)

#-----------------------------------------------------

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_PACKAGE_NAME := FactoryTest
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res
LOCAL_ASSET_DIR := $(LOCAL_PATH)/assets

LOCAL_STATIC_JAVA_LIBRARIES := \
	android-support-v4 \
	libFactory_gson-2.3.1 \
	libFactory_MotorService

LOCAL_JNI_SHARED_LIBRARIES := libIRCore libmisc

LOCAL_MULTILIB := 32

LOCAL_PROGUARD_ENABLED := disabled

include $(BUILD_PACKAGE)	

include $(CLEAR_VARS)

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \
	libFactory_gson-2.3.1:libs/gson-2.3.1.jar \
	libFactory_MotorService:libs/MotorService.jar
include $(BUILD_MULTI_PREBUILT)
