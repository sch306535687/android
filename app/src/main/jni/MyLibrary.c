#include "com_example_sun_myapplication_MyLibrary.h"

JNIEXPORT jstring JNICALL Java_com_example_sun_myapplication_MyLibrary_getString
  (JNIEnv * env, jobject obj){
   //定义一个C语言字符串
      char* cstr = "hello jni";
      jstring jstr = (*env) -> NewStringUTF(env, cstr);
      return jstr;
  }