LOCAL_PATH:= $(call my-dir)

ifeq ($(MTK_PROJECT), y20a_dev)
	include $(LOCAL_PATH)/y20a_dev/Android.mk
else ifeq ($(MTK_PROJECT), y20b_dev)
	include $(LOCAL_PATH)/y20b_dev/Android.mk
else
	include $(LOCAL_PATH)/y20a_dev/Android.mk
endif